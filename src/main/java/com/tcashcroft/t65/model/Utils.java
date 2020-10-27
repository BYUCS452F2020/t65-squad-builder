package com.tcashcroft.t65.model;

public class Utils {
    public enum Faction {
        REBEL_ALLIANCE("REBEL_ALLIANCE"),
        GALACTIC_EMPIRE("GALACTIC_EMPIRE"),
        SCUM_AND_VILLAINY("SCUM_AND_VILLAINY"),
        RESISTANCE("RESISTANCE"),
        FIRST_ORDER("FIRST_ORDER"),
        GALACTIC_REPUBLIC("GALACTIC_REPUBLIC"),
        SEPARATIST_ALLIANCE("SEPARATIST_ALLIANCE");

        private String value;

        Faction(String value) {
            this.value = value.toUpperCase();
        }

        public String getValue() {
            return value;
        }
    }
}
