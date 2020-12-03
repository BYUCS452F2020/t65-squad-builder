package com.tcashcroft.t65.cli.provider;

import com.tcashcroft.t65.cli.command.SquadCommands;
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
public class SquadShipEntryIdProvider implements ValueProvider {

    @Autowired
    private SquadCommands squadCommands;

    @Override
    public boolean supports(MethodParameter parameter, CompletionContext completionContext) {
        return true;
    }

    @Override
    public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
        List<String> ids = new ArrayList<>();
        if (squadCommands.getSquad() != null) {
            ids = squadCommands.getSquad().getShips().stream().map(Squad.ShipEntry::getId).collect(Collectors.toList());
        }
        String userInput = completionContext.currentWordUpToCursor();
        List<CompletionProposal> result = new ArrayList<>();
        ids.stream().filter(t -> t.startsWith(userInput)).forEach(t -> result.add(new CompletionProposal(t)));
        return result;
    }
}
