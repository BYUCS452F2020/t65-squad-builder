package com.tcashcroft.t65.controller;

import com.tcashcroft.t65.exception.ExistsException;
import com.tcashcroft.t65.exception.ForbiddenException;
import com.tcashcroft.t65.exception.NotFoundException;
import com.tcashcroft.t65.exception.UpgradeNotAllowedException;
import com.tcashcroft.t65.model.*;
import com.tcashcroft.t65.service.ShipService;
import com.tcashcroft.t65.service.SquadService;
import com.tcashcroft.t65.service.UpgradeService;
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

    @Deprecated
    @GetMapping("/id/{id}")
    public Squad getSquadById(@PathVariable("username") final String username, @PathVariable("id") final String id) throws NotFoundException, ForbiddenException {
        Squad squad = squadService.getSquadById(id);
        if (squad.getUsername().equals(username)) {
            return squad;
        } else throw new ForbiddenException();
    }

    @GetMapping("/all")
    public List<Squad> getSquads(@PathVariable("username") final String username) {
        return squadService.getSquads(username);
    }

    @GetMapping("/{name}")
    public Squad getSquadBySquadName(@PathVariable("username") final String username, @PathVariable("name") final String squadName) throws NotFoundException, ForbiddenException {
        Squad squad = squadService.getSquadBySquadName(squadName, username);
        if (squad.getUsername().equals(username)) {
            return squad;
        } else throw new ForbiddenException();
    }

    @PostMapping()
    public Squad createSquad(@PathVariable("username") final String username, @RequestBody final Squad squad) throws ForbiddenException, ExistsException {
        if (squad.getUsername().equals(username)) {
            try {
                squadService.getSquadBySquadName(squad.getName(), squad.getUsername());
                throw new ExistsException();
            } catch (NotFoundException e) {
                Squad newSquad = new Squad();
                newSquad.setUsername(squad.getUsername());
                newSquad.setFaction(squad.getFaction());
                newSquad.setName(squad.getName());
                newSquad.setShips(squad.getShips());
                newSquad.setTotalPoints(squad.getTotalPoints());
                return squadService.createSquad(newSquad);
            }
        } else throw new ForbiddenException();
    }

    @DeleteMapping("/{name}")
    public void deleteSquadById(@PathVariable("username") final String username, @PathVariable("name") final String squadName) throws NotFoundException, ForbiddenException{
        Squad squad = squadService.getSquadBySquadName(squadName, username);
        if (squad.getUsername().equals(username)) {
            squadService.deleteSquadBySquadName(squadName, username);
        } else throw new ForbiddenException();
    }

    @PostMapping("/{name}/ship/{shipName}")
    public Squad.ShipEntry addShipToSquad(@PathVariable("username") final String username, @PathVariable("name") final String squadName, @PathVariable("shipName") final String shipName) throws NotFoundException, ForbiddenException{
        Squad squad = squadService.getSquadBySquadName(squadName, username);
        if (squad.getUsername().equals(username)) {
            Ship ship = shipService.getShipByNameId(shipName);
            Squad.ShipEntry entry = squadService.addShipToSquadBySquadName(squadName, username, ship);
            return entry;
        } else throw new ForbiddenException();
    }

    @GetMapping("/{name}/ship_entry/{shipEntryId}")
    public Squad.ShipEntry getShipEntryById(@PathVariable("username") final String username, @PathVariable("name") final String squadName, @PathVariable("shipEntryId") final String shipEntryId) throws NotFoundException, ForbiddenException {
        Squad squad = squadService.getSquadBySquadName(squadName, username);
        if (squad.getUsername().equals(username)) {
            return squad.getShips().stream().filter(it -> it.getId().equals(shipEntryId)).findFirst().orElseThrow(() -> new NotFoundException());
        } else throw new ForbiddenException();
    }

    @DeleteMapping("/{name}/ship_entry/{shipEntryId}")
    public Squad deleteSquadShipByShipId(@PathVariable("username") final String username, @PathVariable("name") final String squadName, @PathVariable("shipEntryId") final String shipEntryId) throws NotFoundException, ForbiddenException{
        Squad squad = squadService.getSquadBySquadName(squadName, username);
        if (squad.getUsername().equals(username)) {
            return squadService.removeShipEntryFromSquadBySquadName(squadName, username, shipEntryId);
        } else throw new ForbiddenException();
    }

    @GetMapping("/{name}/ships")
    public List<Squad.ShipEntry> getSquadShips(@PathVariable("username") final String username, @PathVariable("name") final String squadName) throws NotFoundException, ForbiddenException {
        Squad squad = squadService.getSquadBySquadName(squadName, username);
        if (squad.getUsername().equals(username)) {
            return squad.getShips();
        } else throw new ForbiddenException();
    }

    @PostMapping("/{name}/ship_entry/{shipEntryId}/upgrade/{upgradeName}")
    public Squad.ShipEntry addUpgradeToSquadShip(@PathVariable("username") final String username, @PathVariable("name") final String squadName, @PathVariable("shipEntryId") final String shipEntryId, @PathVariable("upgradeName") final String upgradeName) throws NotFoundException, ForbiddenException, UpgradeNotAllowedException {
       Squad squad = squadService.getSquadBySquadName(squadName, username);
       if (squad.getUsername().equals(username)) {
           Upgrade upgrade = upgradeService.getUpgradeByNameId(upgradeName);
           return squadService.addUpgradeToShipBySquadName(squadName, username, shipEntryId, upgrade);
       } else throw new ForbiddenException();
    }

    @DeleteMapping("/{name}/ship_entry/{shipEntryId}/upgrade/{upgradeName}")
    public Squad.ShipEntry deleteSquadUpgrade(@PathVariable("username") final String username, @PathVariable("name") final String squadName, @PathVariable("shipEntryId") final String shipEntryId, @PathVariable("upgradeName") final String upgradeName) throws NotFoundException, ForbiddenException {
        Squad squad = squadService.getSquadBySquadName(squadName, username);
        if (squad.getUsername().equals(username)) {
            Upgrade upgrade = upgradeService.getUpgradeByNameId(upgradeName);
            return squadService.removeUpgradeFromShipBySquadName(squadName, username, shipEntryId, upgrade);
        } else throw new ForbiddenException();
    }
}
