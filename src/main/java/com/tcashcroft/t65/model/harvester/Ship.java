package com.tcashcroft.t65.model.harvester;

import com.tcashcroft.t65.model.shared.Action;
import com.tcashcroft.t65.model.shared.Force;
import com.tcashcroft.t65.model.shared.ShipAbility;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Ship {
   private String name;
   private String type;
   private String xws;
   private int ffg;
   private String size;
   private List<String> dialCodes;
   private String faction;
   private List<Map<String, String>> stats;
   private List<Action> actions;
   private String icon;
   private List<Pilot> pilots;

   @Data
   public static class Pilot {
      private String name;
      private String caption;
      private int initiative;
      private int limited;
      private int cost;
      private String xws;
      private String ability;
      private List<String> slots;
      private boolean hyperspace;
      private int ffg;
      private String artwork;
      private String image;
      private Force force;
      private ShipAbility shipAbility;
   }
}
