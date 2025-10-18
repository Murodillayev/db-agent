package uz.pdp.dbagent.model;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AgentRequest {
    private String username;
    private String password;
    private String dbUrl;
    private String projectName;
}
