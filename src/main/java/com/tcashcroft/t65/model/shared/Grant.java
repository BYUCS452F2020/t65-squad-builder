package com.tcashcroft.t65.model.shared;

import lombok.Data;

import java.util.Map;

@Data
public class Grant {
    // TODO make sure this is a complete mapping
    private String type;
    private Map<String, String> value;
}
