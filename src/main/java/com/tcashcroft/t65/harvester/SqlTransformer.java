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

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Data
@Slf4j
public class SqlTransformer extends GameDataTransformer {

    private GameDataHarvester harvester;

    private List<Action> actionsList;

    private List<Utils.Faction> factionList;

    private List<Ship> shipList;

    private List<Upgrade> upgradeList;

    private List<Action.Color> colorList;

    @Override
    public void populateDatabase() throws Exception {
        harvester.cloneDataRepo();
    }

    public List<Utils.Faction> convertFactions() {
        if (factionList == null || factionList.isEmpty()) {
            List<Faction> harvesterFactions = harvestFactions();
            List<Utils.Faction> factions = new ArrayList<>();
            for (Faction f : harvesterFactions) {
                factions.add(Utils.Faction.valueOf(f.getName().replaceAll(" ", "_").toUpperCase()));
            }
            factionList = factions;
            return factions;
        } else return factionList;
//        List<Utils.Faction> factions = harvesterFactions.stream().map(
//                hf -> Utils.Faction.valueOf(hf.getName().toUpperCase())).distinct().collect(Collectors.toList());
//        return factions;
    }

//    public List<com.tcashcroft.t65.model.Action> convertActions() {
//        List<String> harvesterColors = generateColorList(harvestShips(), harvestUpgrades());
//        List<com.tcashcroft.t65.model.Action> actions = harvestActions().stream()
//                .flatMap(ha -> {
//                    List<com.tcashcroft.t65.model.Action> coloredActions = new ArrayList<>();
//                    for (String s : harvesterColors) {
//                        com.tcashcroft.t65.model.Action action = new com.tcashcroft.t65.model.Action();
//                        action.setColor(com.tcashcroft.t65.model.Action.Color.valueOf(s));
//                        action.setAction(ha.getName());
//                        action.setId(ha.getXws() + "-" + ha.getFfg());
//                        coloredActions.add(action);
//                    }
//                    return coloredActions.stream();
//                }).distinct()
//                .collect(Collectors.toList());
//        return actions;
//    }

    public List<com.tcashcroft.t65.model.Action> convertActions() {
        if (actionsList == null || actionsList.isEmpty()) {
            List<String> harvesterColors = generateColorList(harvestShips(), harvestUpgrades());
            Set<Action> actions = new HashSet<>();

            for (com.tcashcroft.t65.harvester.model.Action a : harvestActions()) {
                List<Action> coloredActions = new ArrayList<>();
                for (String s : harvesterColors) {
                    Action action = new Action();
                    action.setColor(Action.Color.valueOf(s));
                    action.setAction(a.getName());
                    coloredActions.add(action);
                }
                actions.addAll(coloredActions);
            }
            actions.forEach(a -> a.setId(UUID.randomUUID().toString()));

            actionsList = actions.stream().collect(Collectors.toList());
            return actionsList;
        } else return actionsList;
    }

    public List<Ship> convertShips() {
        if (shipList == null || shipList.isEmpty()) {
            List<com.tcashcroft.t65.harvester.model.Ship> harvesterShips = harvestShips();
            List<Ship> ships = harvestShips().stream().flatMap(hs -> {
                List<Ship> sublist = new ArrayList<>();
                for (com.tcashcroft.t65.harvester.model.Ship.Pilot p : hs.getPilots()) {
                    Ship ship = new Ship();
                    ship.setId(UUID.randomUUID().toString()); // TODO come back to this
                    ship.setFaction(Utils.Faction.valueOf(hs.getFaction().toUpperCase().replaceAll(" ", "_"))); // TODO come back to this too
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

                    if (p.getForce() != null) {
                        ship.setForce(p.getForce().getValue());
                    } else {
                        ship.setForce(0);
                    }
                    ship.setAbilityText(p.getAbility());

                    ship.setAction1(prepareAction(hs.getActions(), 1));
                    ship.setAction2(prepareAction(hs.getActions(), 2));
                    ship.setAction3(prepareAction(hs.getActions(), 3));
                    ship.setAction4(prepareAction(hs.getActions(), 4));

                    List<String> slots = p.getSlots();
                    if (slots == null) {
                        log.info("Ship with Null Slots: {}", p.getName());
                        slots = new ArrayList<>();
                    }
                    ship.setAstromechUpgrades(prepareUpgradeCount(slots, "Astromech"));
                    ship.setCannonUpgrades(prepareUpgradeCount(slots, "Cannon"));
                    ship.setCargoUpgrades(prepareUpgradeCount(slots, "Cargo"));
                    ship.setConfigurationUpgrades(prepareUpgradeCount(slots, "Configuration"));
                    ship.setCrewUpgrades(prepareUpgradeCount(slots, "Crew"));
                    ship.setDeviceUpgrades(prepareUpgradeCount(slots, "Device"));
                    ship.setForceUpgrades(prepareUpgradeCount(slots, "Force"));
                    ship.setGunnerUpgrades(prepareUpgradeCount(slots, "Gunner"));
                    ship.setHardpointUpgrades(prepareUpgradeCount(slots, "Hardpoint"));
                    ship.setHyperdriveUpgrades(prepareUpgradeCount(slots, "Hyperdrive"));
                    ship.setIllicitUpgrades(prepareUpgradeCount(slots, "Illicit"));
                    ship.setModificationUpgrades(prepareUpgradeCount(slots, "Modification"));
                    ship.setMissileUpgrades(prepareUpgradeCount(slots, "Missile"));
                    ship.setSensorUpgrades(prepareUpgradeCount(slots, "Sensor"));
                    ship.setTacticalRelayUpgrades(prepareUpgradeCount(slots, "Tactical Relay"));
                    ship.setTalentUpgrades(prepareUpgradeCount(slots, "Talent"));
                    ship.setTeamUpgrades(prepareUpgradeCount(slots, "Team"));
                    ship.setTechUpgrades(prepareUpgradeCount(slots, "Tech"));
                    ship.setTitleUpgrades(prepareUpgradeCount(slots, "Title"));
                    ship.setTorpedoUpgrades(prepareUpgradeCount(slots, "Torpedo"));
                    ship.setTurretUpgrades(prepareUpgradeCount(slots, "Turret"));

                    ship.setHyperspaceLegal(p.isHyperspace());
                    ship.setExtendedLegal(true);

                    ship.setDialCode(ship.getShipType().getValue());
                    ship.setSize(Ship.Size.valueOf(hs.getSize().toUpperCase()));
                    ship.setInitiative(p.getInitiative());

                    sublist.add(ship);
                }

                return sublist.stream();
            }).collect(Collectors.toList());
            shipList = ships;
            return shipList;
        } else return shipList;
    }

    public List<Upgrade> convertUpgrades() {
        if (upgradeList == null || upgradeList.isEmpty()) {
            List<com.tcashcroft.t65.harvester.model.Upgrade> harvesterUpgrades = harvestUpgrades();
            List<Upgrade> upgrades = harvesterUpgrades.stream().flatMap(hu -> {
                List<Upgrade> sublist = new ArrayList<>();
                for (com.tcashcroft.t65.harvester.model.Upgrade.Side s : hu.getSides()) {
                    Upgrade upgrade = new Upgrade();

//                upgrade.setId(hu.getName() + "-" + s.getTitle());
                    upgrade.setId(UUID.randomUUID().toString());
                    // TODO revisit this - they are a list in the source data
                    upgrade.setFaction(null);
                    upgrade.setName(s.getTitle());
                    upgrade.setNameLimit(hu.getLimited());
                    // TODO this is also handled in the restrictions list
                    upgrade.setShipType(null);
                    upgrade.setUpgradeType(Upgrade.UpgradeType.valueOf(s.getType().toUpperCase().replaceAll(" ", "_")));
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

                    String idSide1 = sublist.get(0).getId();
                    String idSide2 = sublist.get(1).getId();
                    sublist.get(0).setFlipSideId(idSide2);
                    sublist.get(1).setFlipSideId(idSide1);
                }

                return sublist.stream();
            }).collect(Collectors.toList());
            upgradeList = upgrades;
            return upgradeList;
        } else return upgradeList;
    }

    public List<Action.Color> convertColors() {
        if (colorList == null || colorList.isEmpty()) {
            List<String> colorStrings = generateColorList(harvestShips(), harvestUpgrades());
            colorList = colorStrings.stream().map(String::toUpperCase).map(Action.Color::valueOf).collect(Collectors.toList());
            return colorList;
        } else return colorList;
    }

    private int getArcValue(List<Map<String, String>> stats, String arc) {
        // TODO the arc stats have a type, for example "type": "attack". This may be future proofing for the game, but I need to verify.
        // alternatively, the stats could be reimplemented as proper objects...
        Optional<String> optionalValue = stats.stream().filter(s -> s.containsKey("arc")).filter(s -> s.containsValue(arc)).map(s -> s.get("value")).findFirst();
        return optionalValue.isPresent() ? Integer.parseInt(optionalValue.get()) : 0;
    }

    private int getTypeStat(List<Map<String, String>> stats, String stat) {
        Optional<String> optionalValue = stats.stream().filter(s -> s.containsKey("type")).filter(s -> s.containsValue(stat)).map(s -> s.get("value")).findFirst();
        return optionalValue.isPresent() ? Integer.parseInt(optionalValue.get()) : 0;
    }

    private Action prepareAction(List<com.tcashcroft.t65.harvester.model.Ship.Action> actions, int actionNumber) {
        log.info("Action Number: {}", actionNumber);
        actionNumber--;
        log.info("Action Number: {}", actionNumber);
        if (actions == null || actions.size() <= actionNumber) {
            log.info("Actions was null or actions size was less than action number. Actions: {}, Actions Number: {}", actions, actionNumber);
            return null;
        }
        log.info("Actions size: {}", actions.size());

        log.info("Actions: {} Action Number: {}", actions, actionNumber);
        com.tcashcroft.t65.harvester.model.Ship.Action a = actions.get(actionNumber);
        Action action = new Action();
        action.setColor(Action.Color.valueOf(a.getDifficulty().toUpperCase()));
        action.setAction(a.getType());
        action.setId(UUID.randomUUID().toString());

        Optional<Action> optionalAction = convertActions().stream().filter(s -> action.getColor().equals(s.getAction())).filter(s -> action.getAction().equals(s.getAction())).findFirst();
        if (optionalAction.isEmpty()) {
            log.warn("A new action was found from the ship list. {}", action);
            return null;
        } else return optionalAction.get();

//        action.setId(action.getAction() + "-" + action.getColor().getValue());
        // TODO add linked action
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
        action.setId(UUID.randomUUID().toString());
//        action.setId(actionType + "-" + actionColor);
        return action;
    }

    private int prepareUpgradeCount(List<String> slots, String upgradeType) {
        if (upgradeType == null || upgradeType.isBlank()) {
            return 0;
        }
        log.info("Slots: {} Upgrade Type: {}", slots, upgradeType);
        return slots.stream().filter(Objects::nonNull).filter(s -> s.equals(upgradeType)).mapToInt(i -> 1).sum();
    }
}
