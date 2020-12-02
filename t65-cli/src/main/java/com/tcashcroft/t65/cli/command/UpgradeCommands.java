package com.tcashcroft.t65.cli.command;

import com.tcashcroft.t65.cli.client.UpgradeClient;
import com.tcashcroft.t65.cli.model.Upgrade;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@Data
public class UpgradeCommands {

    @Autowired
    private UpgradeClient upgradeClient;

    @ShellMethod("Get upgrades")
    public List<String> getUpgrades() {
        return upgradeClient.getUpgrades().stream().map(Upgrade::getNameId).collect(Collectors.toList());
    }

    @ShellMethod("Get upgrade")
    public Upgrade getUpgrade(String upgradeName) {
        return upgradeClient.getUpgrade(upgradeName);
    }
}
