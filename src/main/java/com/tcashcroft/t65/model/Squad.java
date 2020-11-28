package com.tcashcroft.t65.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Data
@Document
@CompoundIndexes({
        @CompoundIndex(name = "identifier", def = "{'username':1, 'faction':1, 'name':1}")
})
public class Squad {
    private String id;
    private String username;
    private String faction;
    private String name;
//    @Indexed
//    private SquadIdentifier identifier;
    private List<ShipEntry> ships = new ArrayList<>();
    private int totalPoints;



    public ShipEntry addShip(Ship ship) {
        ShipEntry entry = new ShipEntry(ship);
        ships.add(entry);
        updateTotalPoints();
        return entry;
    }

    public void removeShip(String id) {
        ships.removeIf(s -> s.getId().equals(id));
        updateTotalPoints();
    }

    public void removeShip(ShipEntry shipEntry) {
        removeShip(shipEntry.getId());
    }

    private void updateTotalPoints() {
        int points = 0;
        for (ShipEntry e : ships) {
            Ship ship = e.getShip();
            points += ship.getPointsCost();
            for (Upgrade u : e.getUpgrades()) {
                points += u.getCost().getValue();
            }
        }
        this.totalPoints = points;
    }

//    public String getUsername() {
//        return identifier.getUsername();
//    }
//
//    public String getFaction() {
//        return identifier.getFaction();
//    }
//
//    public String getName() {
//        return identifier.getName();
//    }

//    @Data
//    public static class SquadIdentifier {
//        private String username;
//        private String faction;
//        private String name;
//    }

    @Data
    public static class ShipEntry {
        private Ship ship;
        private List<Upgrade> upgrades;
        private String id;

        public ShipEntry(Ship ship) {
            id = UUID.randomUUID().toString();
            upgrades = new ArrayList<>();
            this.ship = ship;
        }
    }

}
