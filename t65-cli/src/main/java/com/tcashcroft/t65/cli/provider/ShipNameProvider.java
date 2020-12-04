package com.tcashcroft.t65.cli.provider;

import com.tcashcroft.t65.cli.client.ShipClient;
import com.tcashcroft.t65.cli.model.Ship;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ShipNameProvider implements ValueProvider {

    @Autowired
    private ShipClient shipClient;

    private List<String> shipNames;

    @Override
    public boolean supports(MethodParameter methodParameter, CompletionContext completionContext) {
        return methodParameter.getParameterName().equals("shipName");
    }

    @Override
    public List<CompletionProposal> complete(MethodParameter methodParameter, CompletionContext completionContext, String[] strings) {
        List<CompletionProposal> result = new ArrayList();
        if (shipNames == null || shipNames.isEmpty()) {
            shipNames = shipClient.getShips().stream().map(Ship::getNameId).collect(Collectors.toList());
        }
        String userInput = completionContext.currentWordUpToCursor();
        shipNames.stream()
                .filter(t -> t.startsWith(userInput))
                .forEach(t -> result.add(new CompletionProposal(t)));

        return result;
    }
}
