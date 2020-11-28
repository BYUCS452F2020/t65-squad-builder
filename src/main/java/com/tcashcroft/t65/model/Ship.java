package com.tcashcroft.t65.model;

import com.tcashcroft.t65.model.shared.Action;
import com.tcashcroft.t65.model.shared.Force;
import com.tcashcroft.t65.model.shared.ShipAbility;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Document
public class Ship {
    @Id
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
