package com.tcashcroft.t65.model.harvester;

import lombok.Data;

import java.util.Map;

@Data
public class FfgXws {
    private Map<Integer, String> pilots;
    private Map<Integer, String> upgrades;
    private Map<Integer, String> factions;
    private Map<Integer, String> ships;
    private Map<Integer, String> actions;
    private Map<Integer, String> stats;
}
