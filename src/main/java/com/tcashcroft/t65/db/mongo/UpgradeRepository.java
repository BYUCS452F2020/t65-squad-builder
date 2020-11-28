package com.tcashcroft.t65.db.mongo;

import com.tcashcroft.t65.model.Upgrade;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UpgradeRepository extends MongoRepository<Upgrade, String> {
    Optional<Upgrade> findUpgradeByNameId(String nameId);
    List<Upgrade> findUpgradesByFaction(String faction);
    List<Upgrade> findUpgradesByShipType(String shipType);
    List<Upgrade> findUpgradesByHyperspaceLegal(boolean hyperspaceLegal);
}
