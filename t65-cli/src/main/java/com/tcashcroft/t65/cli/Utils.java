package com.tcashcroft.t65.cli;

import com.tcashcroft.t65.cli.model.Inventory;
import com.tcashcroft.t65.cli.model.Ship;
import com.tcashcroft.t65.cli.model.Squad;
import com.tcashcroft.t65.cli.model.Upgrade;
import org.springframework.shell.table.*;

import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    private static final String FRONT_ARC = "Front Arc";
    private static final String REAR_ARC = "Rear Arc";
    private static final String BULLSEYE_ARC = "Bullseye Arc";
    private static final String SINGLE_TURRET_ARC = "Single Turret Arc";
    private static final String DOUBLE_TURRET_ARC = "Double Turret Arc";
    private static final String FULL_FRONT_ARC = "Full Front Arc";
    private static final String FULL_REAR_ARC = "Full Rear Arc";
    private static final String AGILITY = "agility";
    private static final String HULL = "hull";
    private static final String SHIELDS = "shields";
    private static final String FORCE = "force";

    public static String getInventoryAsTable(Inventory inventory) {
        String shipString = "No ships in inventory.";
        if (!inventory.getShips().isEmpty()) {
            int i = 0;
            Map<Ship, Integer> shipCounts = new HashMap<>();
            inventory.getShips().forEach(s -> {
                if (shipCounts.containsKey(s)) {
                    shipCounts.put(s, shipCounts.get(s) + 1);
                } else {
                    shipCounts.put(s, 1);
                }
            });
            Object[][] ships = new Object[shipCounts.size()][2];
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
            int j = 0;
            Map<Upgrade, Integer> upgradeCounts = new HashMap<>();
            inventory.getUpgrades().forEach(u -> {
                if (upgradeCounts.containsKey(u)) {
                    upgradeCounts.put(u, upgradeCounts.get(u) + 1);
                } else {
                    upgradeCounts.put(u, 1);
                }
            });
            Object[][] upgrades = new Object[upgradeCounts.size()][2];
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
        List<Map.Entry<String, String>> sortedStats = sortStats(statsMap);
        Object[][] statsProperties = new Object[sortedStats.size()][2];
        int j = 0;
        for (Map.Entry e : sortedStats) {
            statsProperties[j][0] = ((String) e.getKey()).toLowerCase();
            statsProperties[j++][1] = e.getValue();
        }
        ArrayTableModel statsModel = new ArrayTableModel(statsProperties);
        TableBuilder statsBuilder = new TableBuilder(statsModel);
        statsBuilder.on(CellMatchers.column(0)).addSizer(new AbsoluteWidthSizeConstraints(20));
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

    private static List<Map.Entry<String, String>> sortStats(Map<String, String> statsMap) {
        List<Map.Entry<String, String>> output = new LinkedList<>();

        if (statsMap.containsKey(FRONT_ARC)) {
            output.add(Map.entry(FRONT_ARC, statsMap.get(FRONT_ARC)));
        }

        if (statsMap.containsKey(REAR_ARC)) {
            output.add(Map.entry(REAR_ARC, statsMap.get(REAR_ARC)));
        }

        if (statsMap.containsKey(BULLSEYE_ARC)) {

            output.add(Map.entry(BULLSEYE_ARC, statsMap.get(BULLSEYE_ARC)));
        }

        if (statsMap.containsKey(SINGLE_TURRET_ARC)) {

            output.add(Map.entry(SINGLE_TURRET_ARC, statsMap.get(SINGLE_TURRET_ARC)));
        }

        if (statsMap.containsKey(DOUBLE_TURRET_ARC)) {

            output.add(Map.entry(DOUBLE_TURRET_ARC, statsMap.get(DOUBLE_TURRET_ARC)));
        }

        if (statsMap.containsKey(FULL_FRONT_ARC)) {
            output.add(Map.entry(FULL_FRONT_ARC, statsMap.get(FULL_FRONT_ARC)));
        }

        if (statsMap.containsKey(FULL_REAR_ARC)) {

            output.add(Map.entry(FULL_REAR_ARC, statsMap.get(FULL_REAR_ARC)));
        }

        if (statsMap.containsKey(AGILITY)) {
            output.add(Map.entry(AGILITY, statsMap.get(AGILITY)));
        }

        if (statsMap.containsKey(HULL)) {
            output.add(Map.entry(HULL, statsMap.get(HULL)));
        }

        if (statsMap.containsKey(SHIELDS)) {

            output.add(Map.entry(SHIELDS, statsMap.get(SHIELDS)));
        }

        if (statsMap.containsKey(FORCE)) {
            output.add(Map.entry(FORCE, statsMap.get(FORCE)));
        }

        return output;
    }

    private static String getStat(String stat, List<Map<String, String>> stats) {
        return stats.stream().filter(s -> s.values().contains(stat)).findFirst().orElseThrow(() -> new RuntimeException()).get("value");
    }
}
