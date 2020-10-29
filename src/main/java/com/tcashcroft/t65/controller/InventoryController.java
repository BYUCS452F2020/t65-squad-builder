package com.tcashcroft.t65.controller;

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
        return inventoryService.createInventory(username);
    }

    @GetMapping("/user/{username}")
    public Inventory getInventoryByUsername(@PathVariable("username") final String username) {
        return inventoryService.getInventoryByUsername(username);
    }

    @GetMapping("/{id}")
    public Inventory getInventoryById(@PathVariable("id") final String id) {
        return inventoryService.getInventoryById(id);
    }

    @PostMapping("/{id}/ship/{shipid}")
    public void addShip(@PathVariable("id") final String id, @PathVariable("shipId") final String shipId) {
        Ship ship = shipService.getShip(shipId);
        inventoryService.createOrIncrementShipInventory(id, ship);
    }

    @DeleteMapping("/{id}/ship/{shipId}")
    public void removeShip(@PathVariable("id") final String id, @PathVariable("shipId") final String shipId) {
        Ship ship = shipService.getShip(shipId);
        inventoryService.deleteOrDecrementShipInventory(id, ship);
    }

    @GetMapping("/{id}/ships")
    public List<Ship> getShips(@PathVariable("id") final String id) {
        return inventoryService.readAllShipInventory(id);
    }

    @PostMapping("/{id}/upgrade/{upgradeId}")
    public void addUpgrade(@PathVariable("id") final String id, @PathVariable("upgradeId") final String upgradeId) {
        Upgrade upgrade = upgradeService.getUpgrade(upgradeId);
        inventoryService.createOrIncrementUpgradeInventory(id, upgrade);
    }

    @DeleteMapping("/{id}/upgrade/{upgradeId}")
    public void removeUpgrade(@PathVariable("id") final String id, @PathVariable("upgradeId") final String upgradeId) {
        Upgrade upgrade = upgradeService.getUpgrade(upgradeId);
        inventoryService.deleteOrDecrementUpgradeInventory(id, upgrade);
    }
}
