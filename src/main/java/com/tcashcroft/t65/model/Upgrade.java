package com.tcashcroft.t65.model;

import lombok.Data;

@Data
public class Upgrade {

    public enum UpgradeType {
        ASTROMECH("ASTROMECH"),
        CANNON("CANNON"),
        CONFIGURATION("CONFIGURATION"),
        CREW("CREW"),
        FORCE("FORCE"),
        GUNNER("GUNNER"),
        HYPERDRIVE("HYPERDRIVE"),
        ILLICIT("ILLICIT"),
        MODIFICAITON("MODIFICATION"),
        MISSILE("MISSILE"),
        PAYLOAD("PAYLOAD"),
        SENSOR("SENSOR"),
        TACTICAL_RELAY("TACTICAL_RELAY"),
        TALENT("TALENT"),
        TECH("TECH"),
        TITLE("TITLE"),
        TORPEDO("TORPEDO"),
        TURRET("TURRET");

        private String value;

        UpgradeType(String value) {
            this.value = value.toUpperCase();
        }

        public String getValue() {
            return value;
        }
    };

    private String id;
    private Utils.Faction faction;
    private String name;
    private int nameLimit;
    private Ship.ShipType shipType;
    private UpgradeType upgradeType;
    private String upgradeText;
    private Action action1;
    private Action action2;
    private Action action3;
    private Action action4;
    private String flipSideId;
    private int pointsCost;
    private boolean hyperspaceLegal;
    private boolean extendedLegal;
}