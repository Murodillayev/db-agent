package uz.pdp.dbagent.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "query")
public class QueryConfig {

    private String createUser;
    private String getUser;
}
