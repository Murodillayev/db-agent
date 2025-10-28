package uz.pdp.dbagent.transfer;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgentResponse {


    private @NonNull String id;
    private String name;
    private String databaseUsername;
    private String databaseUrl;
    private String callbackUrl;
}
