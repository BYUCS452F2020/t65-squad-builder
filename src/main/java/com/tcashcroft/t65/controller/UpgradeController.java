package com.tcashcroft.t65.controller;

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

    @GetMapping("/{id}")
    public Upgrade getUpgrade(@PathVariable final String upgradeId) {
        return upgradeService.getUpgrade(upgradeId);
    }

    @GetMapping("/all")
    public List<Upgrade> getAllUpgrades() {
        return upgradeService.getAllUpgrades();
    }
}
