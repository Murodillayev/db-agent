package uz.pdp.dbagent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.dbagent.config.QueryConfig;
import uz.pdp.dbagent.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class UserProcessor {
    private final QueryConfig queryConfig;

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void update(List<User> users) {
        for (User user : users) {
            List<String> mems = jdbcTemplate.query(queryConfig.getGetUser(), new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("usename");
                }
            },user.getUsername());

            if (mems.isEmpty()) {
                namedParameterJdbcTemplate.update(queryConfig.getCreateUser(), Map.of(
                        "username", user.getUsername(),
                        "password", user.getPassword()));
            }


        }
    }

}
