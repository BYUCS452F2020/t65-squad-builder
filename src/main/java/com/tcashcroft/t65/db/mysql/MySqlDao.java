package com.tcashcroft.t65.db.mysql;

import com.tcashcroft.t65.model.Action;
import com.tcashcroft.t65.model.Ship;
import com.tcashcroft.t65.model.Upgrade;
import com.tcashcroft.t65.model.Utils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

public class MySqlDao {

    private DataSource dataSource;

    public String createInventory() {
        return null;
    }

    public void addShipToInventory(Ship ship, String inventoryId) {

    }

    public void addUpgradeToInventory(Upgrade upgrade, String inventoryId) {

    }

    public void deleteInventory(String inventoryId) {

    }

    public String createSquad() {
        return null;
    }

    public void addShipToSquad(Ship ship, String squadId) {

    }

    public void addUpgradeToShip(Upgrade upgrade, Ship ship, String squadId) {

    }

    public void deleteUpgradeFromShip(Upgrade upgrade, Ship ship, String squadId) {

    }

    public void deleteSquad(String squadId) {

    }

    public void loadColors(List<Action.Color> colors) {

    }

    public void loadActions(List<Action> actions) {

    }

    public void loadFactions(List<Utils.Faction> factions) {

    }

    public void loadShips(List<Ship> ships) {

    }

    public void loadShipTypes(List<Ship.ShipType> shipTypes) {

    }

    public void loadShipSize(List<Ship.Size> sizes) {

    }

    public void loadUpgrades(List<Upgrade> upgrades) {

    }

    public void loadUpgradeTypes() {

    }
}
