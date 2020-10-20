package com.tcashcroft.t65.harvester.model;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

import java.util.List;
import java.util.Map;

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
//       private List<Map<String, String>> actions;
        private List<Ship.Action> actions;
//       private Object grants;
//       private List<Map<String, String>> grants;
//        private ObjectNode grants;
//        private List<Grant> grants;
    }

    @Data
    public static class Cost {
        private int value;
    }

    @Data
    public static class Restriction {
        List<String> factions;
    }

    @Data
    public static class Attack {
        private String arc;
        private int value;
    }

    @Data
    public static class Grant {
        private String type;
        private Map<String, String> value;
    }
}
