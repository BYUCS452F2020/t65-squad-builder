package com.tcashcroft.t65.model;

import lombok.Data;

@Data
public class Ship {

    public enum ShipType {};
    public enum Size {SMALL, MEDIUM, LARGE, HUGE};

    private String shipId;
    private Utils.Faction faction;
    private String name;
    private ShipType shipType;
    private int nameLimit;
    private String callSign;
    private int frontArc;
    private int rearArc;
    private int turretArc;
    private int agility;
    private int hull;
    private int shield;
    private int force;
    private String abilityText;
    private Action action1;
    private Action action2;
    private Action action3;
    private Action action4;
    private int astromechUpgrades;
    private int cannonUpgrades;
    private int cargoUpgrades;
    private int configurationUpgrades;
    private int crewUpgrades;
    private int deviceUpgrades;
    private int forceUpgrades;
    private int gunnerUpgrades;
    private int hardpointUpgrades;
    private int hyperdriveUpgrades;
    private int illicitUpgrades;
    private int modificationUpgrades;
    private int missileUpgrades;
    private int sensorUpgrades;
    private int tacticalRelayUpgrades;
    private int talentUpgrades;
    private int teamUpgrades;
    private int techUpgrades;
    private int titleUpgrades;
    private int torpedoUpgrades;
    private int turretUpgrades;
    private int pointsCost;
    private boolean hyperspaceLegal;
    private boolean extendedLegal;
    private String dialCode;
    private Size size;
    private int initiative;
}
