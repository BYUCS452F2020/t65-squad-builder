package com.tcashcroft.t65.harvester;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.tcashcroft.t65.db.mongo.ShipRepository;
import com.tcashcroft.t65.db.mongo.UpgradeRepository;
import com.tcashcroft.t65.model.harvester.Ship;
import com.tcashcroft.t65.model.harvester.Upgrade;
import edu.byu.hbll.json.UncheckedObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Service
@Slf4j
public class GameDataHarvester {

    @Autowired
    private GameDataHarvesterConfiguration config;

    @Autowired
    ShipRepository shipRepository;

    @Autowired
    UpgradeRepository upgradeRepository;

    @PostConstruct
    public void postConstruct() throws Exception {
        cloneDataRepo();
        shipRepository.saveAll(GameDataTransformer.transformShips(harvestShips()));
        upgradeRepository.saveAll(GameDataTransformer.transformUpgrades(harvestUpgrades()));
    }

    public void cloneDataRepo() throws Exception {
        if (config.dataRepoLocation.toFile().exists()) {
            new Git(new FileRepository(config.dataRepoLocation.toFile())).pull();
        } else {
            Git.cloneRepository().setURI(config.dataRepoUri.toString()).setDirectory(config.dataRepoLocation.toFile()).call();
        }
    }

    public List<Ship> harvestShips() {
        List<Ship> ships = new ArrayList<>();
        for (File subDir : Paths.get(config.dataRepoLocation.toString(), config.pilotsDir.toString()).toFile().listFiles()) {
            String faction = subDir.getName().toUpperCase();
            for (File shipFile : subDir.listFiles()) {
                String shipType = FilenameUtils.getBaseName(shipFile.getName()).replaceAll("-", "_");
                log.info("Parsing " + faction + " " + shipType);
                Ship ship = config.mapper.readValue(shipFile, Ship.class);
                ship.setType(shipType.toUpperCase());
                ships.add(ship);
            }
        }
        return ships;
    }

    public List<Upgrade> harvestUpgrades() {
        List<Upgrade> upgrades = new ArrayList<>();
        for (File upgradeFile : Paths.get(config.dataRepoLocation.toString(), config.upgradesDir.toString()).toFile().listFiles()) {
            String upgradeType = FilenameUtils.getBaseName(upgradeFile.getName()).toUpperCase().replaceAll("-", "_");
            log.info("Parsing upgrades " + upgradeType);
            List<Upgrade> sublist = new ArrayList<>();
            JsonNode upgradeData = config.mapper.readValue(upgradeFile, ArrayNode.class);
            for (JsonNode node : upgradeData) {
                Upgrade u = config.mapper.treeToValue(node, Upgrade.class);
                u.setType(upgradeType);
                sublist.add(u);
            }
            upgrades.addAll(sublist);
        }
        return upgrades;
    }

    @Data
    public static class GameDataHarvesterConfiguration {
        private URI dataRepoUri;
        private Path dataRepoLocation;
        private Path actionsPath;
        private Path factionsPath;
        private Path pilotsPath;
        private Path ffgXwsPath;
        private Path pilotsDir;
        private Path upgradesDir;
        private UncheckedObjectMapper mapper;
    }

}
