package uz.pdp.dbagent.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.pdp.dbagent.config.ApplicationProps;
import uz.pdp.dbagent.config.VersionProps;
import uz.pdp.dbagent.model.User;
import uz.pdp.dbagent.transfer.AgentRequest;
import uz.pdp.dbagent.transfer.AgentResponse;

import java.util.Arrays;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final ApplicationProps applicationProps;
    private final RestTemplate restTemplate;
    private final UserProcessor processor;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


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
        AgentResponse response = exchange.getBody();
        VersionProps.setAgentId(response.getId());
        log.info("Successfully initialized agent : {}", exchange.getBody());


    }


    @Scheduled(cron = "*/10 * * * * *")
    public void updateUsers() {
        Map<String, Object> params = Map.of(
                "agentId", VersionProps.getAgentId(),
                "version", VersionProps.getVersion()
        );
        User[] users = restTemplate.getForObject(applicationProps.getGetUserUpdateUrl(), User[].class, params);
        log.info("Successfully retrieved users : {}", Arrays.toString(users));

        if (users == null || users.length == 0) return;
        processor.update(Arrays.stream(users).toList());
        log.info("Successfully updates users");

    }
}
