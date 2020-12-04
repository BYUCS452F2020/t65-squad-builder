package com.tcashcroft.t65.cli.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Squad {
    private String id;
    private String username;
    private String faction;
    private String name;
    private List<ShipEntry> ships = new ArrayList<>();
    private int totalPoints;

    public ShipEntry addShip(com.tcashcroft.t65.cli.model.Ship ship) {
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
            for (com.tcashcroft.t65.cli.model.Upgrade u : e.getUpgrades()) {
                points += u.getCost().getValue();
            }
        }
        this.totalPoints = points;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShipEntry {
        private com.tcashcroft.t65.cli.model.Ship ship;
        private List<com.tcashcroft.t65.cli.model.Upgrade> upgrades;
        private String id;

        public ShipEntry(com.tcashcroft.t65.cli.model.Ship ship) {
            id = UUID.randomUUID().toString();
            upgrades = new ArrayList<>();
            this.ship = ship;
        }
    }

}
