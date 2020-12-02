package com.tcashcroft.t65.cli.model;

import com.tcashcroft.t65.cli.model.shared.Action;
import com.tcashcroft.t65.cli.model.shared.Force;
import com.tcashcroft.t65.cli.model.shared.ShipAbility;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Ship {
    private String nameId;
    private String name;
    private String faction;
    private String shipType;
    private int nameLimit;
    private String callSign;
    private List<Map<String, String>> stats;
    private Force force;
    private String pilotAbility;
    private ShipAbility shipAbility;
    private List<Action> actions;
    private Map<String, Integer> slots;
    private int pointsCost;
    private boolean hyperspaceLegal;
    private boolean extendedLegal;
    private String dialCode;
    private String size;
    private int initiative;
    private String imageUrl;
}
