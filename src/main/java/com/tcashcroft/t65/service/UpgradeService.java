package com.tcashcroft.t65.service;

import com.tcashcroft.t65.db.mongo.UpgradeRepository;
import com.tcashcroft.t65.exception.NotFoundException;
import com.tcashcroft.t65.model.Upgrade;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
public class UpgradeService {

    @Autowired
    private UpgradeRepository upgradeDao;

    public Upgrade createUpgrade(Upgrade upgrade) {
        return upgradeDao.save(upgrade);
    }

    public void createFlipSideUpgrades(List<Upgrade> upgrades) {
        upgrades.forEach(u -> upgradeDao.save(u));
    }

    public Upgrade getUpgrade(String upgradeId) throws NotFoundException {
        Optional<Upgrade> upgradeOptional = upgradeDao.findById(upgradeId);
        return upgradeOptional.orElseThrow(NotFoundException::new);
    }

    public Upgrade getUpgradeByNameId(String upgradeNameId) throws NotFoundException {
        Optional<Upgrade> upgradeOptional = upgradeDao.findUpgradeByNameId(upgradeNameId);
        return upgradeOptional.orElseThrow(NotFoundException::new);
    }

    public List<Upgrade> getAllUpgrades() {
        return upgradeDao.findAll();
    }

    public List<Upgrade> getAllShipSpecificUpgrades(String shipType) {
        return upgradeDao.findUpgradesByShipType(shipType);
    }

    public List<Upgrade> getAllFactionUpgrades(String faction) {
        return upgradeDao.findUpgradesByFaction(faction);
    }

    public List<Upgrade> getAllHyperspaceLegalUpgrades(boolean hyperspaceLegal) {
        return upgradeDao.findUpgradesByHyperspaceLegal(hyperspaceLegal);
    }

}
