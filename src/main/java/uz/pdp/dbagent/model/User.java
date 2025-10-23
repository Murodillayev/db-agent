package uz.pdp.dbagent.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

@Getter
@Setter
public class User {
    private String username;
    private String password;
    private List<String> roles; // create_table,
    private Long version;
    private boolean deleted;

    //
    //
    // create user {username} login password {password}

    // alter user {username} passowrd {password}



}


// hub                         //agent
// user1 v=1                   // call to hub for get updates  1. (agentId,v=0) [user1,user2]
// user2 v=1                                                   2. (agentId,v=1) []
//                                                              3. (agentId,v=1) []
// user1 v=1
// user2 v=2                                 100. (agentId,v=1) [user2]


// user3 v=8
// user1 v=7
// user2 v=4                                  120. (agentId,v=3) [user1,user2,user3] -> 8