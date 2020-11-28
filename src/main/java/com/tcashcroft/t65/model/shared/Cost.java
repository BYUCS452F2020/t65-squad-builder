package com.tcashcroft.t65.model.shared;

import lombok.Data;

import java.util.Map;

@Data
public class Cost {
    private int value;
    private String variable;
    private Map<String, String> values;
}
