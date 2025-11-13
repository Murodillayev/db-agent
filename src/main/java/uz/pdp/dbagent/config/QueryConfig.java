package uz.pdp.dbagent.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "query")
public class QueryConfig {
    private String exists;
    private String create;
    private String alterPassword;
    private String dropUser;
    private String enable;
    private String currentRoles;
    private String grantRole;
    private String revokeRole;
}
