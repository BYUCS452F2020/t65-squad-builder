package com.tcashcroft.t65.service;

import com.tcashcroft.t65.db.mysql.SquadDao;
import com.tcashcroft.t65.db.mysql.SquadShipDao;
import com.tcashcroft.t65.db.mysql.SquadUpgradeDao;
import com.tcashcroft.t65.model.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
public class SquadService {

    @Autowired
    private SquadDao squadDao;

    @Autowired
    private SquadShipDao squadShipDao;

    @Autowired
    private SquadUpgradeDao squadUpgradeDao;

    public Squad getSquadById(String squadId) {
        Optional<Squad> squadOptional = squadDao.readSquad(squadId);
        if (squadOptional.isEmpty()) {
            return null;
        } else {
            return squadOptional.get();
        }
    }

    public Squad getSquadBySquadName(String squadName, String username) {
        Optional<Squad> squadOptional = squadDao.readSquad(username, squadName);
        if (squadOptional.isEmpty()) {
            return null;
        } else {
            return squadOptional.get();
        }
    }

    public Squad createSquad(String squadName, String username, Utils.Faction faction) {
        squadDao.createSquad(username, squadName, faction);
        return getSquadBySquadName(squadName, username);
    }

    public void deleteSquadBySquadName(String squadName, String username) {
        Squad squad = getSquadBySquadName(squadName, username);
        squadDao.deleteSquad(squad.getId());
    }

    public void deleteSquadById(String squadId) {
        squadDao.deleteSquad(squadId);
    }

    /* TODO change controller classes to accept full classes, not primitives. I.E. this method should accept
        a Squad and a Ship rather than a String and a Ship. Since the fully created objects are currently
        guaranteed to come out of the controllers, having a full object should guarantee the presence of the
        appropriate identifiers. */
    public void createSquadShip(String squadId, Ship ship) {
        squadShipDao.createSquadShip(squadId, ship);
    }

    public SquadShip getSquadShip(String squadShipId) {
        // TODO the custom exceptions should be thrown by the controllers
        return squadShipDao.readSquadShip(squadShipId).orElseThrow(() -> new RuntimeException());
    }

    public List<SquadShip> getSquadShips(String squadId) {
        return squadShipDao.readSquadShips(squadId);
    }

    public void deleteSquadShips(String squadId) {
        squadShipDao.deleteSquadShips(squadId);
    }

    public void deleteSquadShip(String squadShipId) {
        squadShipDao.deleteSquadShip(squadShipId);
    }

    public void createSquadUpgrade(String squadShipId, Upgrade upgrade) {
        squadUpgradeDao.createSquadUpgrade(squadShipId, upgrade);
    }

    public void deleteSquadUpgrade(String squadUpgradeId) {
        squadUpgradeDao.deleteSquadUpgrade(squadUpgradeId);
    }

    public void deleteSquadUpgrades(String squadShipId) {
        squadUpgradeDao.deleteSquadUpgrades(squadShipId);
    }

    public SquadUpgrade getSquadUpgrade(String squadUpgradeId) {
        return squadUpgradeDao.readSquadUpgrade(squadUpgradeId).orElseThrow(() -> new RuntimeException());
    }

    public List<SquadUpgrade> getSquadUpgrades(String squadShipId) {
        return squadUpgradeDao.readSquadUpgrades(squadShipId);
    }
}
