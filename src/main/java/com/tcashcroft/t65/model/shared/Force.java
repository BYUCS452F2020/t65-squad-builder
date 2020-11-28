package com.tcashcroft.t65.model.shared;

import lombok.Data;

import java.util.List;

@Data
public class Force {
    private int value;
    private int recovers;
    private List<String> side;
}
