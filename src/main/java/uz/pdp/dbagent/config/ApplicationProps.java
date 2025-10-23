package uz.pdp.dbagent.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationProps {

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${application.name}")
    private String projectName;

    @Value("${application.register-url}")
    private String registerUrl;

    @Value("${application.get-user-update-url}")
    private String getUserUpdateUrl;

    @Value("${application.enable-register:false}")
    private Boolean enableRegister;

}
