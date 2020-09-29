package com.tcashcroft.t65.model;

import lombok.Data;

@Data
public class Upgrade {

    public enum UpgradeType {
        ASTROMECH,
        CANNON,
        CONFIGURATION,
        CREW,
        FORCE,
        GUNNER,
        HYPERDRIVE,
        ILLICIT,
        MODIFICAITON,
        MISSILE,
        PAYLOAD,
        SENSOR,
        TACTICAL_RELAY,
        TALENT,
        TECH,
        TITLE,
        TORPEDO,
        TURRET
    };

    private String upgradeId;
    private Utils.Faction faction;
    private String name;
    private int nameLimit;
    private Ship.ShipType shipType;
    private UpgradeType upgradeType;
    private Action action1;
    private Action action2;
    private Action action3;
    private Action action4;
    private String flipSideId;
    private int pointsCost;
    private boolean hyperspaceLegal;
    private boolean extendedLegal;
}