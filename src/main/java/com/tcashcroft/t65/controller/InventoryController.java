package com.tcashcroft.t65.controller;

import com.tcashcroft.t65.exception.NotFoundException;
import com.tcashcroft.t65.model.Inventory;
import com.tcashcroft.t65.model.Ship;
import com.tcashcroft.t65.model.Upgrade;
import com.tcashcroft.t65.service.InventoryService;
import com.tcashcroft.t65.service.ShipService;
import com.tcashcroft.t65.service.UpgradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ShipService shipService;

    @Autowired
    private UpgradeService upgradeService;

    @PostMapping("/user/{username}")
    public Inventory createInventory(@PathVariable("username") final String username) {
        Inventory inventory = new Inventory();
        inventory.setUsername(username);
        return inventoryService.createInventory(inventory);
    }

    @GetMapping("/user/{username}")
    public Inventory getInventoryByUsername(@PathVariable("username") final String username) throws NotFoundException {
        return inventoryService.getInventoryByUsername(username);
    }

    @PostMapping("/user/{username}/ship/{shipNameId}")
    public Inventory addShip(@PathVariable("username") final String username, @PathVariable("shipNameId") final String shipNameId) throws NotFoundException {
        Ship ship = shipService.getShip(shipNameId);
        return inventoryService.addShipToInventoryByUsername(username, ship);
    }

    @DeleteMapping("/user/{username}/ship/{shipNameId}")
    public Inventory removeShip(@PathVariable("username") final String username, @PathVariable("shipNameId") final String shipNameId) throws NotFoundException {
        Ship ship = shipService.getShipByNameId(shipNameId);
        return inventoryService.removeShipFromInventoryByUsername(username, ship);
    }

    @GetMapping("/user/{username}/ships")
    public List<Ship> getShips(@PathVariable("username") final String username) throws NotFoundException {
        return inventoryService.getInventoryByUsername(username).getShips();
    }

    @PostMapping("/user/{username}/upgrade/{upgradeId}")
    public Inventory addUpgrade(@PathVariable("username") final String username, @PathVariable("upgradeId") final String upgradeId) throws NotFoundException {
        Upgrade upgrade = upgradeService.getUpgrade(upgradeId);
        return inventoryService.addUpgradeToInventoryByUsername(username, upgrade);
    }

    @DeleteMapping("/user/{username}/upgrade/{upgradeId}")
    public Inventory removeUpgrade(@PathVariable("username") final String username, @PathVariable("upgradeId") final String upgradeId) throws NotFoundException {
        Upgrade upgrade = upgradeService.getUpgrade(upgradeId);
        return inventoryService.removeUpgradeFromInventoryByUsername(username, upgrade);
    }

    @GetMapping("/user/{username}/upgrades")
    public List<Upgrade> getUpgrades(@PathVariable("username") final String username) throws NotFoundException {
        return inventoryService.getInventoryByUsername(username).getUpgrades();
    }
}
