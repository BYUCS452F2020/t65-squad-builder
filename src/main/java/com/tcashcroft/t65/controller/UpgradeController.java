package com.tcashcroft.t65.controller;

import com.tcashcroft.t65.exception.NotFoundException;
import com.tcashcroft.t65.model.Upgrade;
import com.tcashcroft.t65.service.UpgradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("upgrade")
public class UpgradeController {

    @Autowired
    private UpgradeService upgradeService;

    @GetMapping("{id}")
    public Upgrade getUpgrade(@PathVariable final String upgradeId) throws NotFoundException {
        return upgradeService.getUpgrade(upgradeId);
    }

    @GetMapping("all")
    public List<Upgrade> getAllUpgrades() {
        return upgradeService.getAllUpgrades();
    }

    @GetMapping("faction/{faction}")
    public List<Upgrade> getAllUpgradesByFaction(@PathVariable final String faction) {
        return upgradeService.getAllFactionUpgrades(faction);
    }

    @GetMapping("ship/{shipType}")
    public List<Upgrade> getAllUpgradesByShipType(@PathVariable final String shipType) {
        return upgradeService.getAllShipSpecificUpgrades(shipType);
    }

    @GetMapping("hyperspace/{legal}")
    public List<Upgrade> getAllUpgradesByHyperSpaceLegality(@PathVariable final boolean legal) {
        return upgradeService.getAllHyperspaceLegalUpgrades(legal);
    }

    @GetMapping("name/{name}")
    public Upgrade getUpgradeByName(@PathVariable final String name) throws NotFoundException {
        return upgradeService.getUpgradeByNameId(name);
    }
}
