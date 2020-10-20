package com.tcashcroft.t65.model;

public class Utils {
    public enum Faction {
        REBEL_ALLIANCE("rebel alliance"),
        GALACTIC_EMPIRE("galactic empire"),
        SCUM_AND_VILLAINY("scum and villainy"),
        RESISTANCE("resistance"),
        FIRST_ORDER("first order"),
        GALACTIC_REPUBLIC("galactic republic"),
        SEPARATIST_ALLIANCE("separatist alliance");

        private String value;

        Faction(String value) {
            this.value = value.toLowerCase();
        }

        public String getValue() {
            return value;
        }
    }
}
