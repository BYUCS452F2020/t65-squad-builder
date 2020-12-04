package com.tcashcroft.t65.cli.provider;

import com.tcashcroft.t65.cli.client.UpgradeClient;
import com.tcashcroft.t65.cli.model.Upgrade;
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
import java.util.stream.Collectors;

@Component
@Data
public class UpgradeTypeProvider implements ValueProvider {

    @Autowired
    private UpgradeClient upgradeClient;

    List<String> upgradeTypes;

    @Override
    public boolean supports(MethodParameter parameter, CompletionContext completionContext) {
        return parameter.getParameterName().equals("upgradeType");
    }

    @Override
    public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext, String[] hints) {
        if (Objects.isNull(upgradeTypes) || upgradeTypes.isEmpty()) {
            upgradeTypes = upgradeClient.getUpgrades().stream().map(Upgrade::getUpgradeType).collect(Collectors.toList());
        }
        String userInput = completionContext.currentWordUpToCursor();
        List<CompletionProposal> result = new ArrayList<>();
        upgradeTypes.stream().filter(t -> t.startsWith(userInput)).forEach(t -> result.add(new CompletionProposal(t)));
        return result;
    }
}
