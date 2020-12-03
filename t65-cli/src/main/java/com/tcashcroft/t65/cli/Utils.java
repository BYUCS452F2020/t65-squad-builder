package com.tcashcroft.t65.cli;

import com.tcashcroft.t65.cli.model.Ship;
import org.springframework.shell.table.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
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

    private static String getStat(String stat, List<Map<String, String>> stats) {
        return stats.stream().filter(s -> s.values().contains(stat)).findFirst().orElseThrow(() -> new RuntimeException()).get("value");
    }
}
