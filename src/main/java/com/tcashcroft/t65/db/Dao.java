package com.tcashcroft.t65.db;

import com.tcashcroft.t65.model.Ship;
import com.tcashcroft.t65.model.Upgrade;

public interface Dao {

    public String createInventory();
    public void addShipToInventory(Ship ship, String inventoryId);
    public void addUpgradeToInventory(Upgrade upgrade, String inventoryId);
    public void deleteInventory(String inventoryId);

    public String createSquad();
    public void addShipToSquad(Ship ship, String squadId);
    public void addUpgradeToShip(Upgrade upgrade, Ship ship, String squadId);
    public void deleteUpgradeFromShip(Upgrade upgrade, Ship ship, String squadId);
    public void deleteSquad(String squadId);

    public void loadColors();
    public void loadActions();
    public void loadFactions();
    public void loadShips();
    public void loadShipTypes();
    public void loadShipSize();
    public void loadUpgrades();
    public void loadUpgradeTypes();

}
