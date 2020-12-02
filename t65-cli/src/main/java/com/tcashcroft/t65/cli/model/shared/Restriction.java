package com.tcashcroft.t65.cli.model.shared;

import lombok.Data;

import java.util.List;

@Data
public class Restriction {
    List<String> factions;
    List<String> sizes;
    List<String> ships;
    List<String> equipped;
    List<String> arcs;
    List<String> forceSide;
    Action action;
}
