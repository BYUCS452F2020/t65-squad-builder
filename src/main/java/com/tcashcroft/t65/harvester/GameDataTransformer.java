package com.tcashcroft.t65.harvester;

import com.tcashcroft.t65.model.Ship;
import com.tcashcroft.t65.model.Upgrade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class GameDataTransformer {

    public static List<Ship> transformShips(List<com.tcashcroft.t65.model.harvester.Ship> ships) {
        return ships.stream().map(GameDataTransformer::transformShip).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public static List<Upgrade> transformUpgrades(List<com.tcashcroft.t65.model.harvester.Upgrade> upgrades) {
        return upgrades.stream().map(GameDataTransformer::transformUpgrade).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private static List<Ship> transformShip(com.tcashcroft.t65.model.harvester.Ship s) {
        List<Ship> ships = new ArrayList<>();
        for (com.tcashcroft.t65.model.harvester.Ship.Pilot p : s.getPilots()) {
            Ship ship = new Ship();
            ship.setFaction(s.getFaction().replaceAll(" ", "_"));
            ship.setName(p.getName().replaceAll("\\\"", "").replaceAll("\"", ""));
            ship.setNameId(p.getName().replaceAll("\\\"", "").replaceAll("\"", "").replaceAll(" ", "_"));
            ship.setShipType(s.getType());
            ship.setNameLimit(p.getLimited());
            ship.setCallSign(p.getCaption());
            ship.setStats(s.getStats());

            if (p.getForce() != null) {
                ship.setForce(p.getForce());
            }

            ship.setPilotAbility(p.getAbility());
            if (p.getShipAbility() != null) {
                ship.setShipAbility(p.getShipAbility());
            }
            ship.setActions(s.getActions());

            Map<String, Integer> slots = new HashMap<>();
            if (p.getSlots() != null && !p.getSlots().isEmpty()) {
                for (String slot : p.getSlots()) {
                    if (slots.containsKey(slot)) {
                        slots.put(slot, slots.get(slot) + 1);
                    } else {
                        slots.put(slot, 1);
                    }
                }
            }
            ship.setSlots(slots);

            ship.setHyperspaceLegal(p.isHyperspace());
            ship.setExtendedLegal(true);
            ship.setDialCode(ship.getShipType());
            ship.setSize(s.getSize());
            ship.setInitiative(p.getInitiative());

            ships.add(ship);
        }
        return ships;
    }

    private static List<Upgrade> transformUpgrade(com.tcashcroft.t65.model.harvester.Upgrade u) {
        List<Upgrade> upgrades = new ArrayList<>();

        for (com.tcashcroft.t65.model.harvester.Upgrade.Side s : u.getSides()) {
            Upgrade upgrade = new Upgrade();

            // TODO revisit this - they are a list in the source data
            upgrade.setFaction(null);
            upgrade.setName(s.getTitle().replaceAll("\\\"", ""));
            upgrade.setNameId(upgrade.getName().replaceAll(" ", "_").replaceAll("\\.", ""));
            upgrade.setNameLimit(u.getLimited());
            // TODO this is also handled in the restrictions list
            upgrade.setShipType(null);
            upgrade.setUpgradeText(s.getAbility());
            upgrade.setUpgradeType(s.getType());
            upgrade.setActions(s.getActions());
            upgrade.setCost(u.getCost());
            upgrade.setHyperspaceLegal(u.isHyperspace());
            upgrade.setExtendedLegal(true);

            upgrades.add(upgrade);
        }
        if (upgrades.size() > 1) {
            if (upgrades.size() > 2) {
                log.warn("Upgrade sides list had size " + upgrades.size());
            }

            String idSide1 = upgrades.get(0).getName();
            String idSide2 = upgrades.get(1).getName();
            upgrades.get(0).setFlipSideId(idSide2);
            upgrades.get(1).setFlipSideId(idSide1);
        }

        return upgrades;
    }
}
