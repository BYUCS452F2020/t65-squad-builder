package com.tcashcroft.t65.db.mongo;

import com.tcashcroft.t65.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
    Optional<Inventory> findInventoryByUsername(String username);
    void deleteInventoryByUsername(String username);
}
