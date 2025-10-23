package uz.pdp.dbagent.transfer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AgentRequest {
    private String username;
    private String password;

    @JsonProperty("databaseUrl")
    private String dbUrl;

    private String name;
}
