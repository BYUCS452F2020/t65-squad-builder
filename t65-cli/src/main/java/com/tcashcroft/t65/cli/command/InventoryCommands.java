package com.tcashcroft.t65.cli.command;

import com.tcashcroft.t65.cli.client.InventoryClient;
import com.tcashcroft.t65.cli.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Objects;

@ShellComponent
public class InventoryCommands {

    @Autowired
    private InventoryClient inventoryClient;

    private Inventory inventory;

    @ShellMethod("Get Inventory")
    public Inventory getInventory() {
        inventory = inventoryClient.getInventory();
        return inventory;
    }

    @ShellMethod("Add ship")
    public Inventory addShipToInventory(String shipName) {
        inventory = inventoryClient.addShipToInventory(shipName);
        return inventory;
    }

    @ShellMethod("Remove ship")
    public Inventory removeShipFromInventory(String shipName) {
        inventory = inventoryClient.removeShipFromInventory(shipName);
        return inventory;
    }

    @ShellMethod("Add upgrade")
    public Inventory addUpgradeToInventory(String upgradeName) {
        inventory = inventoryClient.addUpgradeToInventory(upgradeName);
        return inventory;
    }

    @ShellMethod("Remove upgrade")
    public Inventory removeUpgradeFromInventory(String upgradeName) {
        inventory = inventoryClient.removeUpgradeFromInventory(upgradeName);
        return inventory;
    }

    public Availability addShipToInventoryAvailability() {
        return isInventoryAvailable();
    }

    public Availability addUpgradeToInventoryAvailability() {
        return isInventoryAvailable();
    }

    private Availability isInventoryAvailable() {
        return Objects.isNull(inventory) ? Availability.unavailable("you have not retrieved your inventory") : Availability.available();
    }
}