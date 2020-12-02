package com.tcashcroft.t65.cli.model;

import com.tcashcroft.t65.cli.model.shared.Action;
import com.tcashcroft.t65.cli.model.shared.Cost;
import lombok.Data;

import java.util.List;

@Data
public class Upgrade {
    private String nameId;
    private String faction;
    private String name;
    private int nameLimit;
    private String shipType;
    private String upgradeType;
    private String upgradeText;
    private List<Action> actions;
    private String flipSideId;
    private Cost cost;
    private boolean hyperspaceLegal;
    private boolean extendedLegal;
}