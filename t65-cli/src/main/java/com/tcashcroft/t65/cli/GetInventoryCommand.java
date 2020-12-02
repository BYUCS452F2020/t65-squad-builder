package com.tcashcroft.t65.cli;

import com.tcashcroft.t65.cli.model.Inventory;
import com.tcashcroft.t65.cli.model.Ship;
import com.tcashcroft.t65.cli.model.Upgrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class GetInventoryCommand {

    @Autowired
    private T65Client client;

    private Inventory inventory;

    @ShellMethod("Get Inventory")
    public Inventory getInventory() {
        inventory = client.getInventory();
        return inventory;
    }

    @ShellMethod("Add ship")
    public Inventory addShipToInventory(String shipName) {
        Ship ship = client.getShip(shipName);
        inventory.getShips().add(ship);
        client.updateInventory();
        return inventory;
    }

    @ShellMethod("Add upgrade")
    public Inventory addUpgradeToInventory(String upgradeName) {
        Upgrade upgrade = client.getUpgrade(upgradeName);
        inventory.getUpgrades().add(upgrade);
        client.updateInventory();
        return inventory;
    }
}
