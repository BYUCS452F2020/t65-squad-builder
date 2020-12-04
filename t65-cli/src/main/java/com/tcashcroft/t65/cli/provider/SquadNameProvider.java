package com.tcashcroft.t65.cli.provider;

import com.tcashcroft.t65.cli.client.SquadClient;
import com.tcashcroft.t65.cli.model.Squad;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
public class SquadNameProvider implements ValueProvider {

    @Autowired
    private SquadClient squadClient;

    @Override
    public boolean supports(MethodParameter parameter, CompletionContext completionContext) {
        return parameter.getParameterName().equals("squadName");
    }

    @Override
    public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
        List<String> squadNames = squadClient.getSquads().stream().map(Squad::getName).collect(Collectors.toList());
        String userInput = completionContext.currentWordUpToCursor();
        List<CompletionProposal> result = new ArrayList<>();
        squadNames.stream().filter(t -> t.startsWith(userInput)).forEach(t -> result.add(new CompletionProposal(t)));
        return result;
    }
}
