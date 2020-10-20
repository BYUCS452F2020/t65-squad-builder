package com.tcashcroft.t65.harvester;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcashcroft.t65.harvester.model.Action;
import com.tcashcroft.t65.harvester.model.Faction;
import com.tcashcroft.t65.harvester.model.Ship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@Service
@Slf4j
public class GameDataHarvester {

    @Autowired
    private GameDataHarvesterConfiguration config;

    public void cloneDataRepo() throws Exception {
        if (config.dataRepoLocation.toFile().exists()) {
            new Git(new FileRepository(config.dataRepoLocation.toFile())).pull();
        } else {
            Git.cloneRepository().setURI(config.dataRepoUri.toString()).setDirectory(config.dataRepoLocation.toFile()).call();
        }
    }

    public List<Faction> readFactions() throws Exception {
        Path factionFilePath = Paths.get(config.dataRepoLocation.toString(), config.factionsPath.toString());
        List<Faction> factions = config.mapper.treeToValue(config.mapper.readTree(factionFilePath.toFile()), List.class);
        return factions;
    }

    public List<Action> readActions() throws Exception {
        Path actionFilePath = Paths.get(config.dataRepoLocation.toString(), config.actionsPath.toString());
        List<Action> actions = config.mapper.treeToValue(config.mapper.readTree(actionFilePath.toFile()), List.class);
        return actions;
    }

    public List<Ship> readShips() throws Exception {
        File shipDir = Paths.get(config.dataRepoLocation.toString(), config.pilotsPath.toString()).toFile();
        if (shipDir.isDirectory()) {
            List<Ship> ships = new ArrayList<>();
            for (File subdir : shipDir.listFiles()) {
                if (subdir.isDirectory()) {
                    for (File file : subdir.listFiles()) {
                        List<Ship> factionList = config.mapper.treeToValue(config.mapper.readTree(file), ships.getClass());
                        ships.addAll(factionList);
                    }
                } else {
                    List<Ship> factionList = config.mapper.treeToValue(config.mapper.readTree(subdir), ships.getClass());
                    ships.addAll(factionList);
                }
            }
            return ships;
        }
        return Collections.emptyList();
    }

    @Data
    public static class GameDataHarvesterConfiguration {
        private URI dataRepoUri;
        private Path dataRepoLocation;
        private Path actionsPath;
        private Path factionsPath;
        private Path pilotsPath;
        private Path ffgXwsPath;
        private ObjectMapper mapper;
    }

}
