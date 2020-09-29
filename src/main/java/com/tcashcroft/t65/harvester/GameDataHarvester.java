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

    private URI dataRepoUri;

    private Path dataRepoLocation;

    private String actionsPath;
    private String factionsPath;
    private String pilotsPath;
    private String ffgXwsPath;
    private ObjectMapper mapper;

    public void cloneDataRepo() throws Exception {
        if (dataRepoLocation.toFile().exists()) {
            new Git(new FileRepository(dataRepoLocation.toFile())).pull();
        } else {
            Git.cloneRepository().setURI(dataRepoUri.toString()).setDirectory(dataRepoLocation.toFile()).call();
        }
    }

    public List<Faction> readFactions() throws Exception {
        Path factionFilePath = Paths.get(dataRepoLocation.toString(), factionsPath);
        List<Faction> factions = mapper.treeToValue(mapper.readTree(factionFilePath.toFile()), List.class);
        return factions;
    }

    public List<Action> readActions() throws Exception {
        Path actionFilePath = Paths.get(dataRepoLocation.toString(), actionsPath);
        List<Action> actions = mapper.treeToValue(mapper.readTree(actionFilePath.toFile()), List.class);
        return actions;
    }

    public List<Ship> readShips() throws Exception {
        File shipDir = Paths.get(dataRepoLocation.toString(), pilotsPath).toFile();
        if (shipDir.isDirectory()) {
            List<Ship> ships = new ArrayList<>();
            for (File subdir : shipDir.listFiles()) {
                if (subdir.isDirectory()) {
                    for (File file : subdir.listFiles()) {
                        List<Ship> factionList = mapper.treeToValue(mapper.readTree(file), ships.getClass());
                        ships.addAll(factionList);
                    }
                } else {
                    List<Ship> factionList = mapper.treeToValue(mapper.readTree(subdir), ships.getClass());
                    ships.addAll(factionList);
                }
            }
            return ships;
        }
        return Collections.emptyList();
    }
}
