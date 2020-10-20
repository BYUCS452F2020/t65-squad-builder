package com.tcashcroft.t65.model;

import lombok.Data;

@Data
public class Action {

    public enum Color {
        RED("RED"), BLUE("BLUE"), PURPLE("PURPLE"), WHITE("WHITE");

        private String value;

        Color(String value) {
            this.value = value.toUpperCase();
        }

        public String getValue() {
            return value;
        }

    }

    private String id;
    private String action;
    private Color color;
}
