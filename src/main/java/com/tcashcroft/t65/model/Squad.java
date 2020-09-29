package com.tcashcroft.t65.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Squad {
    private String squadId;
    private String userId;
    private Utils.Faction faction;
    private Map<Ship, List<Upgrade>> shipsAndUpgrades;
    private int totalPoints;
}
