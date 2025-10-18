package uz.pdp.dbagent.model;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

        AgentRequest body = AgentRequest.builder()
                .username(applicationProps.getDbUsername())
                .password(applicationProps.getDbPassword())
                .projectName(applicationProps.getProjectName())
                .dbUrl(applicationProps.getDbUrl())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("API-KEY", "my-api-key");
        HttpEntity<AgentRequest> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8080/api/v1/project-agent", HttpMethod.POST, request, String.class);

        if (!exchange.getStatusCode().is2xxSuccessful()) {
            log.error("Error in initializing agent : {}", exchange.getBody());
            return;
        }

        log.info("Successfully initialized agent : {}", exchange.getBody());


    }
}
