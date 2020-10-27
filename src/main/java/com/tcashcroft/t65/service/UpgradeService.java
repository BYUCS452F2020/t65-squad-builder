package com.tcashcroft.t65.service;

import com.tcashcroft.t65.db.mysql.UpgradeDao;
import com.tcashcroft.t65.model.Upgrade;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Setter
public class UpgradeService {

    @Autowired
    private UpgradeDao upgradeDao;

    public void createUpgrade(Upgrade upgrade) {
        upgradeDao.createUpgrade(upgrade);
    }

    public void createFlipSideUpgrades(List<Upgrade> upgrades) {
        upgradeDao.createFlipSideUpgrades(upgrades);
    }

    public Upgrade getUpgrade(String upgradeId) {
        return upgradeDao.readUpgrade(upgradeId).orElseThrow(() -> new RuntimeException());
    }

}
