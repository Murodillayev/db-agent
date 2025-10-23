package uz.pdp.dbagent.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgentResponse {
    private String id;
    private String name;
    private String databaseUsername;
    private String databaseUrl;
    private String callbackUrl;
}
