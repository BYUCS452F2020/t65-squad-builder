package com.tcashcroft.t65.service;

import com.tcashcroft.t65.db.mysql.InventoryDao;
import com.tcashcroft.t65.db.mysql.ShipInventoryDao;
import com.tcashcroft.t65.db.mysql.UpgradeInventoryDao;
import com.tcashcroft.t65.model.Inventory;
import com.tcashcroft.t65.model.Ship;
import com.tcashcroft.t65.model.Upgrade;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
public class InventoryService {

    @Autowired
    private InventoryDao inventoryDao;

    @Autowired
    private ShipInventoryDao shipInventoryDao;

    @Autowired
    private UpgradeInventoryDao upgradeInventoryDao;

    public Inventory createInventory(String username) {
        inventoryDao.createInventory(username);
        Optional<Inventory> inventoryOptional = inventoryDao.readInventory(username, true);
        if (inventoryOptional.isPresent()) {
            return inventoryOptional.get();
        } else return null;
    }

    public Inventory getInventoryById(String inventoryId) {
        return inventoryDao.readInventory(inventoryId, false).orElse(null);
    }

    public Inventory getInventoryByUsername(String username) {
        return inventoryDao.readInventory(username, true).orElse(null);
    }

    public void deleteInventoryByUsername(String username) {
        inventoryDao.deleteInventory(username, true);
    }

    public void deleteInventoryById(String id) {
        inventoryDao.deleteInventory(id, false);
    }

    public void createOrIncrementShipInventory(String inventoryId, Ship ship) {
        shipInventoryDao.createOrIncrementShipInventory(inventoryId, ship);
    }

    public void deleteOrDecrementShipInventory(String inventoryId, Ship ship) {
        shipInventoryDao.deleteOrDecrementShipInventory(inventoryId, ship);
    }

    public void createAllShipInventory(String inventoryId, List<Ship> ships) {
        shipInventoryDao.createAllShipInventory(inventoryId, ships);
    }

    public List<Ship> readAllShipInventory(String inventoryId) {
        return shipInventoryDao.readAllShipInventory(inventoryId);
    }

    public void createOrIncrementUpgradeInventory(String inventoryId, Upgrade upgrade) {
        upgradeInventoryDao.createOrIncrementUpgradeInventory(inventoryId, upgrade);
    }

    public void deleteOrDecrementUpgradeInventory(String inventoryId, Upgrade upgrade) {
        upgradeInventoryDao.deleteOrDecrementUpgradeInventory(inventoryId, upgrade);
    }

    public List<Upgrade> readAllUpgradeInventory(String inventoryId) {
        return upgradeInventoryDao.readAllUpgradeInventory(inventoryId);
    }
}
