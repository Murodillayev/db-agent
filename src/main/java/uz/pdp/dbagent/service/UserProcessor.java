package uz.pdp.dbagent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.dbagent.config.QueryConfig;
import uz.pdp.dbagent.config.VersionProps;
import uz.pdp.dbagent.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class UserProcessor {

    private final QueryConfig queryConfig;
    private final JdbcTemplate jdbcTemplate;

    public void update(List<User> users) {
        Long maxVersion = users.stream()
                .map(User::getVersion)
                .max(Long::compareTo)
                .orElse(0L);

        for (User user : users) {
            if (user.isDeleted()) {
                dropUser(user.getUsername());
            } else {
                syncUser(user);
            }
        }
        syncVersion(maxVersion);
    }

    private void syncUser(User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        boolean exists = userExists(username);

        if (!exists) {
            createUser(username, password);
        } else {
            updatePassword(username, password);
        }

        syncRoles(username, user.getRoles());

        enableLogin(username);
    }

    private boolean userExists(String username) {

        String sql = queryConfig.getExists().formatted(username);

        try {
            jdbcTemplate.queryForObject(
                    sql,
                    Integer.class
            );
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    private void createUser(String username, String password) {
        String sql = queryConfig.getCreate().formatted(username, password);
        jdbcTemplate.update(sql);
    }

    private void updatePassword(String username, String password) {
        String sql = queryConfig.getAlterPassword().formatted(username, password);
        jdbcTemplate.update(sql);
    }

    private void dropUser(String username) {
        String sql = queryConfig.getDropUser().formatted(username);
        jdbcTemplate.update(sql);
    }

    private void enableLogin(String username) {
        jdbcTemplate.update(
                queryConfig.getEnable().formatted(username)
        );
    }

    // [r1,r2,r3,r4](hammasi)    user[r4,r3]       user(new)[r2,r3]

    // newRoles = [r2,r3],   revoke = [r1,r2,r3,r4]     ->  [r1,r4]
    // newRoles = [r2,r3],   grant = [r1,r2,r3,r4]     ->  [r1,r4]

    private void syncRoles(String username, List<String> targetRoles) {
        targetRoles.removeIf(r -> r == null || r.isEmpty());
        List<String> currentRoles = getCurrentRoles(username);

        Set<String> toRevoke = new HashSet<>(currentRoles);

        toRevoke.removeIf(targetRoles::contains);

        Set<String> toGrant = new HashSet<>(targetRoles);
        toGrant.removeIf(currentRoles::contains);

        for (String role : toRevoke) {
            jdbcTemplate.update(
                    queryConfig.getRevokeRole().formatted(role, username)
            );
        }

        for (String role : toGrant) {
            String sql = queryConfig.getGrantRole().formatted(role, username);
            jdbcTemplate.update(sql);
        }
    }

    private List<String> getCurrentRoles(String username) {
        return jdbcTemplate.query(
                queryConfig.getCurrentRoles().formatted(username),
                (rs, rowNum) -> rs.getString(1)
        );
    }

    private void syncVersion(Long version) {
        VersionProps.setVersion(version.toString());
    }

}
