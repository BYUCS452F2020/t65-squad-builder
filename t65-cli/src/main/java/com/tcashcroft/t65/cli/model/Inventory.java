package com.tcashcroft.t65.cli.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Inventory {
    private String username;
    private List<com.tcashcroft.t65.cli.model.Ship> ships = new ArrayList<>();
    private List<com.tcashcroft.t65.cli.model.Upgrade> upgrades = new ArrayList<>();
}
