package com.tcashcroft.t65.service;

import com.tcashcroft.t65.db.mongo.SquadRepository;
import com.tcashcroft.t65.exception.NotFoundException;
import com.tcashcroft.t65.exception.UpgradeNotAllowedException;
import com.tcashcroft.t65.model.*;
import edu.byu.hbll.misc.Strings;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
public class SquadService {

    @Autowired
    private SquadRepository squadDao;

    public List<Squad> getSquads(String username) {
        return squadDao.findSquadsByUsername(username);
    }

    public Squad getSquadById(String squadId) throws NotFoundException {
        return squadDao.findById(squadId).orElseThrow(NotFoundException::new);
    }

    public Squad getSquadBySquadName(String squadName, String username) throws NotFoundException {
        return squadDao.findSquadByUsernameAndName(username, squadName).orElseThrow(NotFoundException::new);
    }

    public Squad createSquad(Squad squad) {
        return squadDao.save(squad);
    }

    public void deleteSquadBySquadName(String squadName, String username) {
        squadDao.removeSquadByUsernameAndName(username, squadName);
    }

    public Squad updateSquad(Squad squad) {
        return squadDao.save(squad);
    }

    public Squad.ShipEntry addShipToSquadBySquadName(String squadName, String username, Ship ship) throws NotFoundException {
        Squad squad = getSquadBySquadName(squadName, username);
        return addShipToSquad(squad, ship);
    }

    private Squad.ShipEntry addShipToSquad(Squad squad, Ship ship) {
        Squad.ShipEntry shipEntry = squad.addShip(ship);
        squadDao.save(squad);
        return shipEntry;
    }

    public Squad removeShipEntryFromSquadBySquadName(String squadName, String username, String shipEntryId) throws NotFoundException {
        Squad squad = getSquadBySquadName(squadName, username);
        squad.removeShip(shipEntryId);
        squadDao.save(squad);
        return squad;
    }

    public Squad.ShipEntry addUpgradeToShipBySquadName(String squadName, String username, String shipEntryId, Upgrade upgrade) throws NotFoundException, UpgradeNotAllowedException {
        Squad squad = getSquadBySquadName(squadName, username);
        Squad.ShipEntry entry = squad.getShips().stream().filter(it -> it.getId().equals(shipEntryId)).findAny().orElseThrow(() -> new NotFoundException());
        return addUpgradeToShip(squad, entry, upgrade);
    }

    private Squad.ShipEntry addUpgradeToShip(Squad squad, Squad.ShipEntry shipEntry, Upgrade upgrade) throws NotFoundException, UpgradeNotAllowedException {
        Optional<Squad.ShipEntry> shipEntryOptional = squad.getShips().stream().filter(it -> shipEntry.getId().equals(it.getId())).findFirst();
        Squad.ShipEntry entryToModify = shipEntryOptional.orElseThrow(() -> new NotFoundException());

        if (upgradeMeetsShipCriteria(entryToModify, upgrade)) {
            entryToModify.getUpgrades().add(upgrade);
            squadDao.save(squad);
            return entryToModify;
        } else throw new UpgradeNotAllowedException();
    }

    private boolean upgradeMeetsShipCriteria(Squad.ShipEntry shipEntry, Upgrade upgrade) {
        Ship ship = shipEntry.getShip();
        if (ship.getSlots() == null) {
            return false;
        }
        if (ship.getSlots().containsKey(upgrade.getUpgradeType())) {
            int maxSlots = ship.getSlots().get(upgrade.getUpgradeType());
            int usedSlots = shipEntry.getUpgrades().stream().filter(it -> it.getUpgradeType().equals(upgrade.getUpgradeType())).mapToInt(it -> 1).sum();
            int availableSlots = maxSlots - usedSlots;
            if (availableSlots > 0 && upgrade.getNameLimit() < usedSlots) {
                if (Strings.isBlank(upgrade.getShipType())) {
                    // TODO handle hyperspace legality and other ship restrictions
                    return true;
                } else {
                    if (!upgrade.getShipType().equals(ship.getShipType())) {
                        return false;
                    }
                }
            } else return false;
        } else return false;
        return true;
    }

    public Squad.ShipEntry removeUpgradeFromShipBySquadName(String squadName, String username, String shipEntryId, Upgrade upgrade) throws NotFoundException {
        Squad squad = getSquadBySquadName(squadName, username);
        Squad.ShipEntry entry = squad.getShips().stream().filter(it -> it.getId().equals(shipEntryId)).findAny().orElseThrow(() -> new NotFoundException());
        return removeUpgradeFromShip(squad, entry, upgrade);
    }

    private Squad.ShipEntry removeUpgradeFromShip(Squad squad, Squad.ShipEntry shipEntry, Upgrade upgrade) throws NotFoundException {
        Optional<Squad.ShipEntry> shipEntryOptional = squad.getShips().stream().filter(it -> shipEntry.getId().equals(it.getId())).findFirst();
        Squad.ShipEntry entryToModify = shipEntryOptional.orElseThrow(() -> new NotFoundException());
        entryToModify.getUpgrades().remove(upgrade);
        squadDao.save(squad);
        return entryToModify;
    }

//    private Squad.ShipEntry copyShipEntry(Squad squad, Squad.ShipEntry shipEntry) {
//        Squad.ShipEntry copiedEntry = squad.addShip(shipEntry.getShip());
//        copiedEntry.getUpgrades().addAll(shipEntry.getUpgrades());
//        squadDao.save(squad);
//        return copiedEntry;
//    }

//    public void deleteSquadById(String squadId) {
//        squadDao.removeById(squadId);
//    }

//    public Squad.ShipEntry addShipToSquadBySquadId(String squadId, Ship ship) throws NotFoundException {
//        Squad squad = getSquadById(squadId);
//        return addShipToSquad(squad, ship);
//    }

//    public Squad.ShipEntry addUpgradeToShipBySquadId(String squadId, Squad.ShipEntry shipEntry, Upgrade upgrade) throws NotFoundException {
//        Squad squad = getSquadById(squadId);
//        return addUpgradeToShip(squad, shipEntry, upgrade);
//    }

//    public Squad.ShipEntry addUpgradeToShipBySquadName(String squadName, String username, Squad.ShipEntry shipEntry, Upgrade upgrade) throws NotFoundException {
//        Squad squad = getSquadBySquadName(squadName, username);
//        return addUpgradeToShip(squad, shipEntry, upgrade);
//    }

//    public Squad.ShipEntry addUpgradeToShipBySquadId(String squadId, Squad.ShipEntry shipEntry, Upgrade upgrade) throws NotFoundException {
//        Squad squad = getSquadById(squadId);
//        return addUpgradeToShip(squad, shipEntry, upgrade);
//    }

//    public Squad.ShipEntry addUpgradeToShipBySquadName(String squadName, String username, Squad.ShipEntry shipEntry, Upgrade upgrade) throws NotFoundException {
//        Squad squad = getSquadBySquadName(squadName, username);
//        return addUpgradeToShip(squad, shipEntry, upgrade);
//    }

//    public Squad.ShipEntry copyShipEntryBySquadId(String squadId, Squad.ShipEntry shipEntry) throws NotFoundException {
//        Squad squad = getSquadById(squadId);
//        return copyShipEntry(squad, shipEntry);
//    }

//    public Squad.ShipEntry copyShipEntryBySquadName(String squadName, String username, Squad.ShipEntry shipEntry) throws NotFoundException {
//        Squad squad = getSquadBySquadName(squadName, username);
//        return copyShipEntry(squad, shipEntry);
//    }

//    public Squad.ShipEntry removeUpgradeFromShipBySquadId(String squadId, Squad.ShipEntry shipEntry, Upgrade upgrade) throws NotFoundException {
//        Squad squad = getSquadById(squadId);
//        return removeUpgradeFromShip(squad, shipEntry, upgrade);
//    }

//    public Squad.ShipEntry removeUpgradeFromShipBySquadName(String squadName, String username, Squad.ShipEntry shipEntry, Upgrade upgrade) throws NotFoundException {
//        Squad squad = getSquadBySquadName(squadName, username);
//        return removeUpgradeFromShip(squad, shipEntry, upgrade);
//    }

}
