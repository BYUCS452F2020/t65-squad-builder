package com.tcashcroft.t65.harvester;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tcashcroft.t65.harvester.model.Action;
import com.tcashcroft.t65.harvester.model.Faction;
import com.tcashcroft.t65.harvester.model.Ship;
import com.tcashcroft.t65.harvester.model.Upgrade;
import edu.byu.hbll.json.UncheckedObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Component
@Slf4j
public abstract class GameDataTransformer {

    @Autowired
    private Path dataRepoLocation;

    @Autowired
    private Path actionsPath;

    @Autowired
    private Path factionsPath;

    @Autowired
    private Path pilotsDir;

    @Autowired
    private Path upgradesDir;

    @Autowired
    private UncheckedObjectMapper mapper;

    abstract void populateDatabase() throws Exception;

    public List<Faction> harvestFactions() {
        List<Faction> factions = new ArrayList<>();
        factions = mapper.readValue(Paths.get(dataRepoLocation.toString(), factionsPath.toString()).toFile(), factions.getClass());
        return factions;
    }

    public List<Action> harvestActions() {
        List<Action> actions = new ArrayList<>();
        actions = mapper.readValue(Paths.get(dataRepoLocation.toString(), actionsPath.toString()).toFile(), actions.getClass());
        return actions;
    }

    public List<Ship> harvestShips() {
        List<Ship> ships = new ArrayList<>();
        for (File subDir : Paths.get(dataRepoLocation.toString(), pilotsDir.toString()).toFile().listFiles()) {
            String faction = subDir.getName().toLowerCase();
            for (File shipFile : subDir.listFiles()) {
                String shipType = shipFile.getName();
                log.info("Parsing " + faction + " " + shipType);
                Ship ship = mapper.readValue(shipFile, Ship.class);
                ship.setType(shipType.toLowerCase());
                ships.add(ship);
            }
        }
        return ships;
    }

    public List<Upgrade> harvestUpgrades() {
        List<Upgrade> upgrades = new ArrayList<>();
        for (File upgradeFile : Paths.get(dataRepoLocation.toString(), upgradesDir.toString()).toFile().listFiles()) {
            String upgradeType = upgradeFile.getName().toLowerCase();
            log.info("Parsing upgrades " + upgradeType);
            List<Upgrade> sublist = new ArrayList<>();
            JsonNode upgradeData = mapper.readValue(upgradeFile, ArrayNode.class);
            for (JsonNode node : upgradeData) {
                Upgrade u = mapper.treeToValue(node, Upgrade.class);
                u.setType(upgradeType);
                sublist.add(u);
            }
            upgrades.addAll(sublist);
        }
        return upgrades;
    }

    public List<String> generateSizeList(List<Ship> ships) {
        return ships.stream().map(Ship::getSize).map(String::toLowerCase).distinct().collect(Collectors.toList());
    }

    public List<String> generateShipTypeList(List<Ship> ships) {
        return ships.stream().map(Ship::getType).distinct().collect(Collectors.toList());
    }

    public List<String> generateColorList(List<Ship> ships, List<Upgrade> upgrades) {
        List<String> shipColors = ships.stream().flatMap(s -> s.getActions().stream()).flatMap(m -> {
            List<String> colors = new ArrayList<>();
            colors.add(m.getDifficulty());
            if (m.getLinked() != null) {
                colors.add(m.getLinked().getDifficulty());
            }
            return colors.stream();
        }).filter(Objects::nonNull).map(String::toLowerCase).distinct().collect(Collectors.toList());
        List<String> upgradeColors = ships.stream().flatMap(u -> u.getActions().stream()).flatMap(m -> {
            List<String> colors = new ArrayList<>();
            colors.add(m.getDifficulty());
            if (m.getLinked() != null) {
                colors.add(m.getLinked().getDifficulty());
            }
            return colors.stream();
        }).filter(Objects::nonNull).map(String::toLowerCase).distinct().collect(Collectors.toList());
        Set<String> colorSet = new HashSet<>();
        colorSet.addAll(shipColors);
        colorSet.addAll(upgradeColors);
        return colorSet.stream().collect(Collectors.toList());
    }

    public List<String> generateUpgradeType(List<Upgrade> upgrades) {
        return upgrades.stream().map(Upgrade::getType).map(String::toLowerCase).distinct().collect(Collectors.toList());
    }
}
