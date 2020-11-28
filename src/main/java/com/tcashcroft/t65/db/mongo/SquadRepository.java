package com.tcashcroft.t65.db.mongo;

import com.tcashcroft.t65.model.Squad;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SquadRepository extends MongoRepository<Squad, String> {
    Optional<Squad> findSquadByUsernameAndName(String username, String name);
    List<Squad> findSquadsByUsername(String username);
    List<Squad> findSquadsByUsernameAndFaction(String username, String faction);
    void removeSquadByUsernameAndName(String username, String name);
    void removeById(String squadId);
}
