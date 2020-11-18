package com.tcashcroft.t65;

import com.tcashcroft.t65.db.mysql.DatabaseLoader;
import com.tcashcroft.t65.harvester.CardImageService;
import com.tcashcroft.t65.harvester.GameDataHarvester;
import com.tcashcroft.t65.harvester.model.Action;
import com.tcashcroft.t65.harvester.model.Faction;
import com.tcashcroft.t65.harvester.model.Ship;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@AllArgsConstructor
@RestController
public class TempController {

    @Autowired
    private CardImageService cardImageRepository;

    @Autowired
    private GameDataHarvester gameDataHarvester;

    @Autowired
    private DatabaseLoader databaseLoader;

    @Autowired
    private DatabaseLoader sqlDatabaseLoader;

    @GetMapping("card/{cardId}")
    public String getCardImageUri(@PathVariable("cardId") final String cardId) {
        System.out.println("Card ID Requested: " + cardId);
       return cardImageRepository.getCardImageUri(cardId);
    }

    @GetMapping(value = "harvest/factions")
    public List<Faction> harvestFactions() throws Exception {
       gameDataHarvester.cloneDataRepo();
       return gameDataHarvester.readFactions();
    }

    @GetMapping(value = "harvest/actions")
    public List<Action> harvestActions() throws Exception {
        gameDataHarvester.cloneDataRepo();
        return gameDataHarvester.readActions();
    }

    @GetMapping(value = "harvest/ships")
    public List<Ship> harvestShips() throws Exception {
        gameDataHarvester.cloneDataRepo();
        return gameDataHarvester.readShips();
    }

    @GetMapping(value = "load/colors")
    public void loadColors() throws Exception {
        databaseLoader.loadColors();
    }

    @GetMapping(value = "load")
    public void load() {
        sqlDatabaseLoader.loadColors();
        sqlDatabaseLoader.loadActions();
        sqlDatabaseLoader.loadFactions();
        sqlDatabaseLoader.loadSizes();
        sqlDatabaseLoader.loadShipTypes();
        sqlDatabaseLoader.loadUpgradeTypes();
        sqlDatabaseLoader.loadShips();
        sqlDatabaseLoader.loadUpgrades();
    }
}
