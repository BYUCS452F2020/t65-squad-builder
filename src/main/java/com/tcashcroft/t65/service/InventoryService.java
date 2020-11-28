package com.tcashcroft.t65.service;

import com.tcashcroft.t65.db.mongo.InventoryRepository;
import com.tcashcroft.t65.exception.NotFoundException;
import com.tcashcroft.t65.model.Inventory;
import com.tcashcroft.t65.model.Ship;
import com.tcashcroft.t65.model.Upgrade;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Setter
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryDao;

    public Inventory createInventory(Inventory inventory) {
        return inventoryDao.save(inventory);
    }

    public Inventory getInventoryById(String inventoryId) throws NotFoundException {
        return inventoryDao.findById(inventoryId).orElseThrow(() -> new NotFoundException());
    }

    public Inventory getInventoryByUsername(String username) throws NotFoundException {
        return inventoryDao.findInventoryByUsername(username).orElseThrow(() -> new NotFoundException());
    }

    public Inventory addShipToInventoryByUsername(String username, Ship ship) throws NotFoundException {
        Inventory inventory = getInventoryByUsername(username);
        inventory.getShips().add(ship);
        inventoryDao.save(inventory);
        return inventory;
    }

    public Inventory removeShipFromInventoryByUsername(String username, Ship ship) throws NotFoundException {
        Inventory inventory = getInventoryByUsername(username);
        inventory.getShips().remove(ship);
        return inventoryDao.save(inventory);
    }

    public Inventory addUpgradeToInventoryByUsername(String username, Upgrade upgrade) throws NotFoundException {
        Inventory inventory = getInventoryByUsername(username);
        inventory.getUpgrades().add(upgrade);
        return inventoryDao.save(inventory);
    }

    public Inventory removeUpgradeFromInventoryByUsername(String username, Upgrade upgrade) throws NotFoundException {
        Inventory inventory = getInventoryByUsername(username);
        inventory.getUpgrades().remove(upgrade);
        return inventoryDao.save(inventory);
    }

}
