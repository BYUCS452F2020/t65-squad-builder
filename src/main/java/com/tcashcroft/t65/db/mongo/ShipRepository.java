package com.tcashcroft.t65.db.mongo;

import com.tcashcroft.t65.model.Ship;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ShipRepository extends MongoRepository<Ship, String> {
    Optional<Ship> findShipByNameId(String name);
    List<Ship> findAllByFaction(String faction);
    List<Ship> findAllByShipType(String shipType);
}
