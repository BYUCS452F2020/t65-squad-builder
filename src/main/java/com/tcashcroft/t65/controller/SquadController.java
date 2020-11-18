package com.tcashcroft.t65.controller;

import com.tcashcroft.t65.model.*;
import com.tcashcroft.t65.service.ShipService;
import com.tcashcroft.t65.service.SquadService;
import com.tcashcroft.t65.service.UpgradeService;
import org.hibernate.jpa.spi.MutableJpaCompliance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/{username}/squad")
public class SquadController {

    @Autowired
    private SquadService squadService;

    @Autowired
    private ShipService shipService;

    @Autowired
    private UpgradeService upgradeService;

    @GetMapping("/{id}")
    public Squad getSquadById(@PathVariable("username") final String username, @PathVariable("id") final String id) {
        Squad squad = squadService.getSquadById(id);
        if (squad.getUsername().equals(username)) {
            return squad;
        } else throw new RuntimeException("Forbidden");
    }

//    @Deprecated
//    @GetMapping("/name/{name}")
//    public Squad getSquadBySquadName(@PathVariable("username") final String username, @PathVariable("name") final String squadName) {
//        Squad squad = squadService.getSquadBySquadName(squadName, username);
//        if (squad.getUsername().equals(username)) {
//            return squad;
//        } else throw new RuntimeException("forbidden");
//    }

    @PostMapping("/{faction}/{name}")
    public Squad createSquad(@PathVariable("username") final String username, @PathVariable("faction") final Utils.Faction faction, @PathVariable("name") final String squadName) {
        Squad squad = squadService.createSquad(squadName, username, faction);
        return squad;
    }

    @DeleteMapping("/id/{id}")
    public void deleteSquadById(@PathVariable("username") final String username, @PathVariable("id") final String id) {
        Squad squad = squadService.getSquadById(id);
        if (squad.getUsername().equals(username)) {
            squadService.deleteSquadById(id);
        } else throw new RuntimeException("forbidden");
    }

//    @Deprecated
//    @DeleteMapping("/name/{name}")
//    public void deleteSquadByName(@PathVariable("username") final String username, @PathVariable("name") final String squadName) {
//        squadService.deleteSquadBySquadName(squadName, username);
//    }

//    @Deprecated
//    @PostMapping("/{name}/ship/{shipId}")
//    public void addShipToSquadBySquadName(@PathVariable("username") final String username, @PathVariable("name") final String squadName, @PathVariable("shipId") final String shipId) {
//        Squad squad = squadService.getSquadBySquadName(squadName, username);
//        if (squad.getUsername().equals(username)) {
//            Ship ship = shipService.getShip(shipId);
//            squadService.createSquadShip(squad.getId(), ship);
//        } else throw new RuntimeException("forbidden");
//    }

    @PostMapping("/{id}/ship/{shipId}")
    public void addShipToSquad(@PathVariable("username") final String username, @PathVariable("id") final String id, @PathVariable("shipId") final String shipId) {
        Squad squad = squadService.getSquadById(id);
        if (squad.getUsername().equals(username)) {
            Ship ship = shipService.getShip(shipId);
            squadService.createSquadShip(id, ship);
        } else throw new RuntimeException("forbidden");
    }

    @GetMapping("/{id}/ships")
    public List<SquadShip> getSquadShips(@PathVariable("username") final String username, @PathVariable("id") final String id) {
        Squad squad = squadService.getSquadById(id);
        if (squad.getUsername().equals(username)) {
            return squad.getShips();
        } else throw new RuntimeException("forbidden");
    }

    @DeleteMapping("/{id}/squad_ship/{squadShipId}")
    public void deleteSquadShipByShipId(@PathVariable("username") final String username, @PathVariable("id") final String id, @PathVariable("squadShipId") final String squadShipId) {
        Squad squad = squadService.getSquadById(id);
        if (squad.getUsername().equals(username)) {
            squadService.deleteSquadShip(squadShipId);
        } else throw new RuntimeException("forbidden");
    }

    @PostMapping("/{id}/squad_ship/{squadShipId}/upgrade/{upgradeId}")
    public void addUpgradeToSquadShip(@PathVariable("username") final String username, @PathVariable("id") final String id, @PathVariable("squadShipId") final String squadShipId, @PathVariable("upgradeId") final String upgradeId) {
       Squad squad = squadService.getSquadById(id);
       if (squad.getUsername().equals(username)) {
           Upgrade upgrade = upgradeService.getUpgrade(upgradeId);
           squadService.createSquadUpgrade(squadShipId, upgrade);
       } else throw new RuntimeException("forbidden");
    }

    @GetMapping("/{id}/squad_upgrade/{squadUpgradeId}")
    public SquadUpgrade getSquadUpgrade(@PathVariable("username") final String username, @PathVariable("id") final String id, @PathVariable("squadUpgradeId") final String squadUpgradeId) {
        Squad squad = squadService.getSquadById(id);
        if (squad.getUsername().equals(username)) {
            return squadService.getSquadUpgrade(squadUpgradeId);
        } else throw new RuntimeException("forbidden");
    }

    @GetMapping("/{id}/squad_ship/{squadShipId}/squad_upgrades")
    public List<SquadUpgrade> getSquadUpgrades(@PathVariable("username") final String username, @PathVariable("id") final String id, @PathVariable("squadShipId") final String squadShipId) {
        Squad squad = squadService.getSquadById(id);
        if (squad.getUsername().equals(username)) {
            return squadService.getSquadUpgrades(squadShipId);
        } else throw new RuntimeException("forbidden");
    }

    @DeleteMapping("/{id}/squad_upgrade/{squadUpgradeId}")
    public void deleteSquadUpgrade(@PathVariable("username") final String username, @PathVariable("id") final String id, @PathVariable("squadUpgradeId") final String squadUpgradeId) {
        Squad squad = squadService.getSquadById(id);
        if (squad.getUsername().equals(username)) {
            squadService.deleteSquadUpgrade(squadUpgradeId);
        } else throw new RuntimeException("forbidden");
    }

    @DeleteMapping("/{id}/squad_ship/{squadShipId}/upgrades")
    public void deleteSquadUpgradesFromSquadShip(@PathVariable("username") final String username, @PathVariable("id") final String id, @PathVariable("squadShipId") final String squadShipId) {
        Squad squad = squadService.getSquadById(id);
        if (squad.getUsername().equals(username)) {
            squadService.deleteSquadUpgrades(squadShipId);
        } else throw new RuntimeException("forbidden");
    }
}
