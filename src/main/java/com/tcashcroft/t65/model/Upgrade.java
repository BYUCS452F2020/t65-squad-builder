package com.tcashcroft.t65.model;

import com.tcashcroft.t65.model.shared.Action;
import com.tcashcroft.t65.model.shared.Cost;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Upgrade {
    @Id
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