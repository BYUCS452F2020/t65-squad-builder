package com.tcashcroft.t65.model.shared;

import lombok.Data;

@Data
public class Action {
    private String difficulty;
    private String type;
    private Action linked;
}
