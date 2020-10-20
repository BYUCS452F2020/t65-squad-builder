package com.tcashcroft.t65.model;

import lombok.Data;

import java.util.List;

@Data
public class Inventory {
    private String id;
    private String username;
    private List<Ship> ships;
    private List<Upgrade> upgrades;
}
