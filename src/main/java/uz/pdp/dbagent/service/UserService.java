package uz.pdp.dbagent.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.pdp.dbagent.config.ApplicationProps;
import uz.pdp.dbagent.transfer.AgentRequest;
import uz.pdp.dbagent.transfer.AgentResponse;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.Properties;

@Service
@Slf4j
public class UserService {

    private final ApplicationProps applicationProps;
    private final RestTemplate restTemplate;

    public UserService(ApplicationProps applicationProps, RestTemplate restTemplate) {
        this.applicationProps = applicationProps;
        this.restTemplate = restTemplate;
    }

    // call register agent to hub
    @PostConstruct
    public void init() {

        if (!applicationProps.getEnableRegister()) {
            return;
        }

        AgentRequest reqBody = AgentRequest
                .builder()
                .username(applicationProps.getDbUsername())
                .password(applicationProps.getDbPassword())
                .name(applicationProps.getProjectName())
                .dbUrl(applicationProps.getDbUrl())
                .build();

        HttpEntity<AgentRequest> request = new HttpEntity<>(reqBody);

        // create default roles

        //call hub's create agent api
        ResponseEntity<AgentResponse> exchange = restTemplate.postForEntity(applicationProps.getRegisterUrl(), request, AgentResponse.class);

        if (!exchange.getStatusCode().is2xxSuccessful()) {
            log.error("Error in initializing agent : {}", exchange.getBody());
            System.exit(1);
            return;
        }

        log.info("Successfully initialized agent : {}", exchange.getBody());


    }


    @Scheduled(cron = "0 */5 * * * *")
    public void updateUsers() {


        //call  User[]

        //

    }
}
