package com.tcashcroft.t65.model.harvester;

import com.tcashcroft.t65.model.shared.Action;
import com.tcashcroft.t65.model.shared.Attack;
import com.tcashcroft.t65.model.shared.Cost;
import com.tcashcroft.t65.model.shared.Restriction;
import lombok.Data;

import java.util.List;

@Data
public class Upgrade {
    private String type;
    private String name;
    private int limited;
    private String xws;
    private List<Side> sides;
    private Cost cost;
    private List<Restriction> restrictions;
    private boolean hyperspace;

    @Data
    public static class Side {
       private String title;
       private String type;
       private String ability;
       private List<String> slots;
       private String image;
       private String ffg;
       private Attack attack;
        private List<Action> actions;
    }
}
