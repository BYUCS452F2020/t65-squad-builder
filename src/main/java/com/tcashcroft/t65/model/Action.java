package com.tcashcroft.t65.model;

import lombok.Data;

@Data
public class Action {
    public enum Color {};
    private String actionId;
    private String actionName;
    private Color color;
}
