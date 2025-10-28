package uz.pdp.dbagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import uz.pdp.dbagent.config.QueryConfig;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({QueryConfig.class})
public class DbAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbAgentApplication.class, args);
    }

}
