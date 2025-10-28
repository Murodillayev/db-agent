package uz.pdp.dbagent;

import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoginProvider implements ValueProvider {
    @Override
    public List<CompletionProposal> complete(CompletionContext completionContext) {

        // get students


        return List.of(new CompletionProposal("aziz:"), new CompletionProposal("baxtiyor"), new CompletionProposal("shezod"));
    }
}
