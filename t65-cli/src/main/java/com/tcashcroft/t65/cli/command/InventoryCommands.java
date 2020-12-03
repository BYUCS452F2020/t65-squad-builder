package com.tcashcroft.t65.cli.command;

import com.tcashcroft.t65.cli.client.InventoryClient;
import com.tcashcroft.t65.cli.model.Inventory;
import com.tcashcroft.t65.cli.provider.ShipNameProvider;
import com.tcashcroft.t65.cli.provider.UpgradeNameProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

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
    public Inventory addShipToInventory(@ShellOption(valueProvider = ShipNameProvider.class) String shipName) {
        inventory = inventoryClient.addShipToInventory(shipName);
        return inventory;
    }

    // TODO create inventory provider to only complete ships in the inventory. Do the same from removing upgrades.
    @ShellMethod("Remove ship")
    public Inventory removeShipFromInventory(@ShellOption(valueProvider = ShipNameProvider.class) String shipName) {
        inventory = inventoryClient.removeShipFromInventory(shipName);
        return inventory;
    }

    @ShellMethod("Add upgrade")
    public Inventory addUpgradeToInventory(@ShellOption(valueProvider = UpgradeNameProvider.class) String upgradeName) {
        inventory = inventoryClient.addUpgradeToInventory(upgradeName);
        return inventory;
    }

    @ShellMethod("Remove upgrade")
    public Inventory removeUpgradeFromInventory(@ShellOption(valueProvider = UpgradeNameProvider.class) String upgradeName) {
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
