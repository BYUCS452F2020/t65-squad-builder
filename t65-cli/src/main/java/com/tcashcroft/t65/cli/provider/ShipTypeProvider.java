package com.tcashcroft.t65.cli.provider;

import com.tcashcroft.t65.cli.client.ShipClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Data
public class ShipTypeProvider implements ValueProvider {

    @Autowired
    private ShipClient shipClient;

    private List<String> shipTypes;

    @Override
    public boolean supports(MethodParameter parameter, CompletionContext completionContext) {
        return parameter.getParameterName().equals("shipType");
    }

    @Override
    public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
        if (Objects.isNull(shipTypes) || shipTypes.isEmpty()) {
            shipTypes = shipClient.getShipTypes();
        }
        String userInput = completionContext.currentWordUpToCursor();
        List<CompletionProposal> result = new ArrayList<>();
        shipTypes.stream().filter(t -> t.startsWith(userInput)).forEach(t -> result.add(new CompletionProposal(t)));
        return result;
    }
}
