package com.tcashcroft.t65.db.mysql;

import com.tcashcroft.t65.harvester.SqlTransformer;
import com.tcashcroft.t65.model.Action;
import com.tcashcroft.t65.model.Ship;
import com.tcashcroft.t65.model.Upgrade;
import com.tcashcroft.t65.model.Utils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Setter
public class DatabaseLoader {

    @Autowired
    private ColorDao colorDao;

    @Autowired
    private ActionDao actionDao;

    @Autowired
    private FactionDao factionDao;

    @Autowired
    private ShipDao shipDao;

    @Autowired
    private ShipTypeDao shipTypeDao;

    @Autowired
    private SizeDao sizeDao;

    @Autowired
    private UpgradeDao upgradeDao;

    @Autowired
    private UpgradeTypeDao upgradeTypeDao;

    @Autowired
    private SqlTransformer sqlTransformer;

    public void loadColors() {
        List<Action.Color> colors = sqlTransformer.convertColors();
        for (Action.Color c : colors) {
            colorDao.createColor(c);
        }
    }

    public void loadActions() {
        List<Action> actions = sqlTransformer.convertActions();
        for (Action action : actions) {
            actionDao.createAction(action);
        }
    }

    public void loadFactions() {
        List<Utils.Faction> factions = sqlTransformer.convertFactions();
        for (Utils.Faction faction : factions) {
            factionDao.createFaction(faction);
        }
    }

    public void loadShipTypes() {
        List<Ship.ShipType> shipTypes = sqlTransformer.generateShipTypeList(sqlTransformer.harvestShips()).stream().map(Ship.ShipType::valueOf).collect(Collectors.toList());
        for (Ship.ShipType shipType : shipTypes) {
            shipTypeDao.createShipType(shipType);
        }
    }

    public void loadUpgradeTypes() {
        List<Upgrade.UpgradeType> upgradeTypes = sqlTransformer.generateUpgradeType(sqlTransformer.harvestUpgrades()).stream().map(Upgrade.UpgradeType::valueOf).collect(Collectors.toList());
        for (Upgrade.UpgradeType upgradeType : upgradeTypes) {
            upgradeTypeDao.createUpgradeType(upgradeType);
        }
    }

    public void loadSizes() {
        List<Ship.Size> sizes = sqlTransformer.generateSizeList(sqlTransformer.harvestShips()).stream().map(Ship.Size::valueOf).collect(Collectors.toList());
        for (Ship.Size size : sizes) {
            sizeDao.createSize(size);
        }
    }

    public void loadShips() {
        List<Ship> ships = sqlTransformer.convertShips();
        for (Ship ship : ships) {
            shipDao.createShip(ship);
        }
    }

    public void loadUpgrades() {
        List<Upgrade> upgrades = sqlTransformer.convertUpgrades();
        List<Upgrade> upgradesWithoutFlipSide = upgrades.stream().filter(u -> u.getFlipSideId() == null ? true : u.getFlipSideId().isBlank()).collect(Collectors.toList());
        List<Upgrade> upgradesWithFlipSide = upgrades.stream().filter(u -> u.getFlipSideId() != null && !u.getFlipSideId().isBlank()).collect(Collectors.toList());


        for (Upgrade upgrade : upgradesWithoutFlipSide) {
            upgradeDao.createUpgrade(upgrade);
        }
        upgradeDao.createFlipSideUpgrades(upgradesWithFlipSide);
    }

}
