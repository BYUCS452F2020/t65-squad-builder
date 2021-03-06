package com.tcashcroft.t65.cli;

import com.tcashcroft.t65.cli.model.Inventory;
import com.tcashcroft.t65.cli.model.Ship;
import com.tcashcroft.t65.cli.model.Squad;
import com.tcashcroft.t65.cli.model.Upgrade;
import org.springframework.shell.table.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    public static String getInventoryAsTable(Inventory inventory) {
        String shipString = "No ships in inventory.";
        if (!inventory.getShips().isEmpty()) {
            Object[][] ships = new Object[inventory.getShips().size()][2];
            int i = 0;
            Map<Ship, Integer> shipCounts = new HashMap<>();
            inventory.getShips().forEach(s -> {
                if (shipCounts.containsKey(s)) {
                    shipCounts.put(s, shipCounts.get(s) + 1);
                } else {
                    shipCounts.put(s, 1);
                }
            });
            for (Map.Entry<Ship, Integer> s : shipCounts.entrySet()) {
                ships[i][0] = s.getKey().getName();
                ships[i++][1] = s.getValue();
            }
            ArrayTableModel model = new ArrayTableModel(ships);
            TableBuilder shipTableBuilder = new TableBuilder(model);
            shipTableBuilder.addFullBorder(BorderStyle.fancy_light);
            shipTableBuilder.on(CellMatchers.column(0)).addSizer(new AbsoluteWidthSizeConstraints(73));
            shipTableBuilder.on(CellMatchers.column(1)).addSizer(new AbsoluteWidthSizeConstraints(7));
            shipString = shipTableBuilder.build().render(80);
        }

        String upgradeString = "No upgrades in inventory";
        if (!inventory.getUpgrades().isEmpty()) {
            Object[][] upgrades = new Object[inventory.getUpgrades().size()][2];
            int j = 0;
            Map<Upgrade, Integer> upgradeCounts = new HashMap<>();
            inventory.getUpgrades().forEach(u -> {
                if (upgradeCounts.containsKey(u)) {
                    upgradeCounts.put(u, upgradeCounts.get(u) + 1);
                } else {
                    upgradeCounts.put(u, 1);
                }
            });
            for (Map.Entry<Upgrade, Integer> u : upgradeCounts.entrySet()) {
                upgrades[j][0] = u.getKey().getName();
                upgrades[j++][1] = u.getValue();
            }
            ArrayTableModel model2 = new ArrayTableModel(upgrades);
            TableBuilder upgradeTableBuilder = new TableBuilder(model2);
            upgradeTableBuilder.addFullBorder(BorderStyle.fancy_light);
            upgradeTableBuilder.on(CellMatchers.column(0)).addSizer(new AbsoluteWidthSizeConstraints(73));
            upgradeTableBuilder.on(CellMatchers.column(1)).addSizer(new AbsoluteWidthSizeConstraints(7));
            upgradeString = upgradeTableBuilder.build().render(80);
        }

        return String.format("\nShips:\n%s\nUpgrades:\n%s\n", shipString, upgradeString);
    }

    public static String getShipAsTable(Ship ship) {
        Object[][] entityProperties = new Object[7][2];
        int i = 0;
        entityProperties[i][0] = "Pilot Name";
        entityProperties[i++][1] = ship.getName();

        entityProperties[i][0] = "Ship Type";
        entityProperties[i++][1] = ship.getShipType();

        entityProperties[i][0] = "Faction";
        entityProperties[i++][1] = ship.getFaction();

        entityProperties[i][0] = "Stats";
        List<String> stats = ship.getStats().stream().map(map -> {
            String type = map.get("type");
            if (type.equals("attack")) {
                return map.get("arc") + ":\t" + map.get("value");
            } else {
                return map.get("type") + ":\t" + map.get("value");
            }
        }).collect(Collectors.toList());

        Map<String, String> statsMap = new HashMap<>();
        ship.getStats().stream().map(
                map -> {
                    String type = map.get("type");
                    if (type.equals("attack")) {
                        return Map.entry(map.get("arc"), map.get("value"));
                    } else {
                        return Map.entry(map.get("type"), map.get("value"));
                    }
                }
        ).collect(Collectors.toList()).forEach(e -> statsMap.put(e.getKey(), e.getValue()));
        Object[][] statsProperties = new Object[statsMap.size()][2];
        int j = 0;
        for (Map.Entry e : statsMap.entrySet()) {
            statsProperties[j][0] = ((String) e.getKey()).toLowerCase();
            statsProperties[j++][1] = e.getValue();
        }
        ArrayTableModel statsModel = new ArrayTableModel(statsProperties);
        TableBuilder statsBuilder = new TableBuilder(statsModel);
        statsBuilder.on(CellMatchers.column(0)).addSizer(new AbsoluteWidthSizeConstraints(10));
        entityProperties[i++][1] = statsBuilder.build().render(65);

        entityProperties[i][0] = "Actions";
        entityProperties[i++][1] = String.join("\n", ship.getActions().stream().map(a -> {
                    if (a.getLinked() != null) {
                        return a.getDifficulty() + " " + a.getType() + " -> " + a.getLinked().getDifficulty() + " " + a.getLinked().getType();
                    } else {
                        return a.getDifficulty() + " " + a.getType();
                    }
                }
        ).collect(Collectors.toList()));

        entityProperties[i][0] = "Ship Ability";
        entityProperties[i++][1] = ship.getShipAbility();

        entityProperties[i][0] = "Pilot Ability";
        entityProperties[i++][1] = ship.getPilotAbility();

        ArrayTableModel model = new ArrayTableModel(entityProperties);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.fancy_light);
        tableBuilder.on(CellMatchers.column(0)).addSizer(new AbsoluteWidthSizeConstraints(15));
        return tableBuilder.build().render(80);
    }

    public static String getSquadAsTable(Squad squad) {
        Object[][] props = new Object[squad.getShips().size() + 1][2];

        int i = 0;
        props[i][0] = squad.getName();
        props[i++][1] = "Total Cost: " + squad.getTotalPoints();

        for (Squad.ShipEntry e : squad.getShips()) {
            props[i][0] = e.getShip().getName();
            props[i++][1] = getSquadShipEntryAsTable(e, 50);
        }

        ArrayTableModel model = new ArrayTableModel(props);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.fancy_light);
        tableBuilder.on(CellMatchers.column(0)).addSizer(new AbsoluteWidthSizeConstraints(30));
        tableBuilder.on(CellMatchers.column(1)).addSizer(new AbsoluteWidthSizeConstraints(50));
        return tableBuilder.build().render(80);
    }

    public static String getSquadShipEntryAsTable(Squad.ShipEntry shipEntry) {
        return getSquadShipEntryAsTable(shipEntry, 80);
    }

    private static String getSquadShipEntryAsTable(Squad.ShipEntry shipEntry, int size) {
        Object[][] props = new Object[shipEntry.getUpgrades().size() + 1][2];
        int i = 0;
        props[i][0] = shipEntry.getShip().getShipType();
        props[i++][1] = String.format("Cost: %d", shipEntry.getShip().getPointsCost() + shipEntry.getUpgrades().stream().mapToInt(u -> u.getCost().getValue()).sum());

        Map<Upgrade, Integer> upgrades = new HashMap<>();
        shipEntry.getUpgrades().stream().forEach(u -> {
            if (upgrades.containsKey(u)) {
                upgrades.put(u, upgrades.get(u) + 1);
            } else {
                upgrades.put(u, 1);
            }
        });

        for (Map.Entry<Upgrade, Integer> e : upgrades.entrySet()) {
            props[i][0] = e.getKey().getName();
            props[i++][1] = "x " + e.getValue();
        }

        ArrayTableModel model = new ArrayTableModel(props);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.fancy_light);
        tableBuilder.on(CellMatchers.column(0)).addSizer(new AbsoluteWidthSizeConstraints(22));
        tableBuilder.on(CellMatchers.column(1)).addSizer(new AbsoluteWidthSizeConstraints(8));
        return tableBuilder.build().render(size);
    }

    private static String getStat(String stat, List<Map<String, String>> stats) {
        return stats.stream().filter(s -> s.values().contains(stat)).findFirst().orElseThrow(() -> new RuntimeException()).get("value");
    }
}
