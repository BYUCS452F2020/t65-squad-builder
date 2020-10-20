package com.tcashcroft.t65.harvester;

import com.tcashcroft.t65.harvester.model.Faction;
import com.tcashcroft.t65.model.Action;
import com.tcashcroft.t65.model.Ship;
import com.tcashcroft.t65.model.Upgrade;
import com.tcashcroft.t65.model.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Data
@Slf4j
public class SqlTransformer extends GameDataTransformer {

    private GameDataHarvester harvester;

    @Override
    public void populateDatabase() throws Exception {
        harvester.cloneDataRepo();
    }

    public List<Utils.Faction> convertFactions() {
        List<Faction> harvesterFactions = harvestFactions();
        List<Utils.Faction> factions = harvesterFactions.stream().map(
                hf -> Utils.Faction.valueOf(hf.getName())).distinct().collect(Collectors.toList());
        return factions;
    }

    public List<com.tcashcroft.t65.model.Action> convertActions() {
        List<String> harvesterColors = generateColorList(harvestShips(), harvestUpgrades());
        List<com.tcashcroft.t65.model.Action> actions = harvestActions().stream()
                .flatMap(ha -> {
                    List<com.tcashcroft.t65.model.Action> coloredActions = new ArrayList<>();
                    for (String s : harvesterColors) {
                        com.tcashcroft.t65.model.Action action = new com.tcashcroft.t65.model.Action();
                        action.setColor(com.tcashcroft.t65.model.Action.Color.valueOf(s));
                        action.setAction(ha.getName());
                        action.setId(ha.getXws() + "-" + ha.getFfg());
                        coloredActions.add(action);
                    }
                    return coloredActions.stream();
                }).distinct().collect(Collectors.toList());
        return actions;
    }

    public List<Ship> convertShips() {
        List<com.tcashcroft.t65.harvester.model.Ship> harvesterShips = harvestShips();
        List<Ship> ships = harvestShips().stream().flatMap(hs -> {
            List<Ship> sublist = new ArrayList<>();
            for (com.tcashcroft.t65.harvester.model.Ship.Pilot p : hs.getPilots()) {
                Ship ship = new Ship();
                ship.setId(null); // TODO come back to this
                ship.setFaction(null); // TODO come back to this too
                ship.setName(p.getName());
                ship.setShipType(Ship.ShipType.valueOf(hs.getType()));
                ship.setNameLimit(p.getLimited());
                ship.setCallSign(p.getCaption());
                List<Map<String, String>> stats = hs.getStats();
                ship.setFrontArc(getArcValue(stats, "Front Arc"));
                ship.setRearArc(getArcValue(stats, "Rear Arc"));
                ship.setTurretArc(getArcValue(stats, "Turret Arc"));
                // TODO bullseye arc?
                ship.setAgility(getTypeStat(stats, "agility"));
                ship.setHull(getTypeStat(stats, "hull"));
                ship.setShield(getTypeStat(stats, "shields"));
                ship.setForce(p.getForce().getValue());
                ship.setAbilityText(p.getAbility());
                ship.setAction1(prepareAction(hs.getActions(), 1));
                ship.setAction2(prepareAction(hs.getActions(), 2));
                ship.setAction3(prepareAction(hs.getActions(), 3));
                ship.setAction4(prepareAction(hs.getActions(), 4));

                List<String> slots = p.getSlots();
                ship.setAstromechUpgrades(prepareUpgradeCount(slots, "Astromech"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Cannon"));
                ship.setCargoUpgrades(prepareUpgradeCount(slots, "Cargo"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Configuration"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Crew"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Device"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Force"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Gunner"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Hardpoint"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Hyperdrive"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Illicit"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Modification"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Missile"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Sensor"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Tactical Relay"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Talent"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Team"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Tech"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Title"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Torpedo"));
                ship.setCannonUpgrades(prepareUpgradeCount(slots, "Turret"));

                ship.setHyperspaceLegal(p.isHyperspace());
                ship.setExtendedLegal(true);

                ship.setDialCode(ship.getShipType().getValue());
                ship.setSize(Ship.Size.valueOf(hs.getSize()));
                ship.setInitiative(p.getInitiative());

                sublist.add(ship);
            }

            return sublist.stream();
        }).collect(Collectors.toList());
        return ships;
    }

    public List<Upgrade> convertUpgrades() {
        List<com.tcashcroft.t65.harvester.model.Upgrade> harvesterUpgrades = harvestUpgrades();
        List<Upgrade> upgrades = harvesterUpgrades.stream().flatMap(hu -> {
            List<Upgrade> sublist = new ArrayList<>();
            for (com.tcashcroft.t65.harvester.model.Upgrade.Side s : hu.getSides()) {
                Upgrade upgrade = new Upgrade();

                upgrade.setId(hu.getName() + "-" + s.getTitle());
                // TODO revisit this - they are a list in the source data
                upgrade.setFaction(null);
                upgrade.setName(s.getTitle());
                upgrade.setNameLimit(hu.getLimited());
                // TODO this is also handled in the restrictions list
                upgrade.setShipType(null);
                upgrade.setUpgradeType(Upgrade.UpgradeType.valueOf(s.getType()));
                upgrade.setAction1(prepareAction(s.getActions(), 1));
                upgrade.setAction2(prepareAction(s.getActions(), 2));
                upgrade.setAction3(prepareAction(s.getActions(), 3));
                upgrade.setAction4(prepareAction(s.getActions(), 4));
                upgrade.setPointsCost(hu.getCost().getValue());
                upgrade.setHyperspaceLegal(hu.isHyperspace());
                upgrade.setExtendedLegal(true);

                sublist.add(upgrade);
            }

            if (sublist.size() > 1) {
               if (sublist.size() > 2) {
                   log.warn("Upgrade sides list had size " + sublist.size());
               }

               String idSide1 = hu.getName() + "-" + sublist.get(0).getName();
               String idSide2 = hu.getName() + "-" + sublist.get(1).getName();
               sublist.get(0).setFlipSideId(idSide2);
               sublist.get(1).setFlipSideId(idSide1);
            }

            return sublist.stream();
        }).collect(Collectors.toList());
        return upgrades;
    }

    public List<Action.Color> convertColors() {
        List<String> colorStrings = generateColorList(harvestShips(), harvestUpgrades());
        return colorStrings.stream().map(String::toUpperCase).map(Action.Color::valueOf).collect(Collectors.toList());
    }

    private int getArcValue(List<Map<String, String>> stats, String arc) {
        // TODO the arc stats have a type, for example "type": "attack". This may be future proofing for the game, but I need to verify.
        // alternatively, the stats could be reimplemented as proper objects...
        Optional<String> optionalValue = stats.stream().filter(s -> s.containsKey("arc")).filter(s -> s.containsKey(arc)).map(s -> s.get("value")).findFirst();
        return optionalValue.isPresent() ? Integer.parseInt(optionalValue.get()) : -1;
    }

    private int getTypeStat(List<Map<String, String>> stats, String stat) {
        Optional<String> optionalValue = stats.stream().filter(s -> s.containsKey("type")).filter(s -> s.containsValue(stat)).map(s -> s.get("value")).findFirst();
        return optionalValue.isPresent() ? Integer.parseInt(optionalValue.get()) : -1;
    }

    private Action prepareAction(List<com.tcashcroft.t65.harvester.model.Ship.Action> actions, int actionNumber) {
        if (actions.size() < actionNumber - 1) {
            return null;
        }

        com.tcashcroft.t65.harvester.model.Ship.Action a = actions.get(actionNumber);
        Action action = new Action();
        action.setColor(Action.Color.valueOf(a.getDifficulty()));
        action.setAction(a.getType());
        action.setId(action.getAction() + "-" + action.getColor().getValue());
        // TODO add linked action
        return action;
    }
    private com.tcashcroft.t65.model.Action prepareActionFromMap(List<Map<String, String>> actions, int actionNumber) {
        if (actions.size() < actionNumber - 1) {
            return null;
        }

        Map<String, String> actionMap = actions.get(actionNumber - 1);
        com.tcashcroft.t65.model.Action action = new Action();
        String actionType = actionMap.get("type");
        String actionColor = actionMap.get("difficulty");
        action.setAction(actionType);
        action.setColor(Action.Color.valueOf(actionColor));
        action.setId(actionType + "-" + actionColor);
        return action;
    }

    private int prepareUpgradeCount(List<String> slots, String upgradeType) {
        return slots.stream().filter(s -> s.equals(upgradeType)).mapToInt(i -> 1).sum();
    }
}
