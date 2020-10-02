CREATE DATABASE `t65_squad_builder` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `t65_squad_builder`;

CREATE TABLE `color` (
  `color_id` int NOT NULL,
  `color_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`color_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `action` (
  `action_id` int NOT NULL,
  `action_name` varchar(45) DEFAULT NULL,
  `action_color` int DEFAULT NULL,
  `linked_action` int DEFAULT NULL,
  PRIMARY KEY (`action_id`),
  KEY `color_id_idx` (`action_color`),
  KEY `action_id_idx` (`linked_action`),
  CONSTRAINT `action_id` FOREIGN KEY (`linked_action`) REFERENCES `action` (`action_id`),
  CONSTRAINT `color_id` FOREIGN KEY (`action_color`) REFERENCES `color` (`color_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `faction` (
  `faction_id` int NOT NULL,
  `faction_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`faction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `upgrade_type` (
  `upgrade_type_id` int NOT NULL,
  `upgrade_type_name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`upgrade_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `user_id` int NOT NULL,
  `username` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ship_type` (
  `ship_type_id` int NOT NULL,
  `ship_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ship_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `size` (
  `size_id` int NOT NULL,
  `size_name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`size_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ship` (
  `ship_id` int NOT NULL,
  `faction_id` int DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `ship_type` int DEFAULT NULL,
  `name_limit` int DEFAULT NULL,
  `call_sign` varchar(45) DEFAULT NULL,
  `front_arc` int DEFAULT NULL,
  `rear_arc` int DEFAULT NULL,
  `turret_arc` int DEFAULT NULL,
  `agility` int DEFAULT NULL,
  `hull` int DEFAULT NULL,
  `shield` int DEFAULT NULL,
  `force` int DEFAULT NULL,
  `ability_text` varchar(45) DEFAULT NULL,
  `action_1` int DEFAULT NULL,
  `action_2` int DEFAULT NULL,
  `action_3` int DEFAULT NULL,
  `action_4` int DEFAULT NULL,
  `astromech_upgrades` int DEFAULT NULL,
  `cannon_upgrades` int DEFAULT NULL,
  `cargo_upgrades` int DEFAULT NULL,
  `command_upgrades` int DEFAULT NULL,
  `configuration_upgrades` int DEFAULT NULL,
  `crew_upgrades` int DEFAULT NULL,
  `device_upgrades` int DEFAULT NULL,
  `force_upgrades` int DEFAULT NULL,
  `gunner_upgrades` int DEFAULT NULL,
  `hardpoint_upgrades` int DEFAULT NULL,
  `hyperdrive_upgrades` int DEFAULT NULL,
  `illicit_upgrades` int DEFAULT NULL,
  `modification_upgrades` int DEFAULT NULL,
  `missile_upgrades` int DEFAULT NULL,
  `sensor_upgrades` int DEFAULT NULL,
  `tactical_relay_upgrades` int DEFAULT NULL,
  `talent_upgrades` int DEFAULT NULL,
  `team_upgrades` int DEFAULT NULL,
  `tech_upgrades` int DEFAULT NULL,
  `title_upgrades` int DEFAULT NULL,
  `torpedo_upgrades` int DEFAULT NULL,
  `turret_upgrades` int DEFAULT NULL,
  `points_cost` int DEFAULT NULL,
  `hyperspace_legal` tinyint DEFAULT NULL,
  `extended_legal` tinyint DEFAULT NULL,
  `dial_code` int DEFAULT NULL,
  `size_id` int DEFAULT NULL,
  `initiative` int DEFAULT NULL,
  PRIMARY KEY (`ship_id`),
  KEY `faction_id_idx` (`faction_id`),
  KEY `ship_type_id_idx` (`ship_type`),
  KEY `action_id1_idx` (`action_1`),
  KEY `action_id2_idx` (`action_2`),
  KEY `action_id3_idx` (`action_3`),
  KEY `action_id4_idx` (`action_4`),
  KEY `size_id_idx` (`size_id`),
  CONSTRAINT `action_id1_ship` FOREIGN KEY (`action_1`) REFERENCES `action` (`action_id`),
  CONSTRAINT `action_id2_ship` FOREIGN KEY (`action_2`) REFERENCES `action` (`action_id`),
  CONSTRAINT `action_id3_ship` FOREIGN KEY (`action_3`) REFERENCES `action` (`action_id`),
  CONSTRAINT `action_id4_ship` FOREIGN KEY (`action_4`) REFERENCES `action` (`action_id`),
  CONSTRAINT `faction_id_ship` FOREIGN KEY (`faction_id`) REFERENCES `faction` (`faction_id`),
  CONSTRAINT `ship_type_id_ship` FOREIGN KEY (`ship_type`) REFERENCES `ship_type` (`ship_type_id`),
  CONSTRAINT `size_id` FOREIGN KEY (`size_id`) REFERENCES `size` (`size_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `upgrade` (
  `upgrade_id` int NOT NULL,
  `faction_id` int DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `name_limit` int DEFAULT NULL,
  `ship_type` int DEFAULT NULL,
  `upgrade_type` int DEFAULT NULL,
  `upgrade_text` varchar(300) DEFAULT NULL,
  `action_1` int DEFAULT NULL,
  `action_2` int DEFAULT NULL,
  `action_3` int DEFAULT NULL,
  `action_4` int DEFAULT NULL,
  `flip_side_id` int DEFAULT NULL,
  `points_cost` int DEFAULT NULL,
  `hyperspace_legal` tinyint DEFAULT NULL,
  `extended_legal` tinyint DEFAULT NULL,
  PRIMARY KEY (`upgrade_id`),
  KEY `faction_id_idx` (`faction_id`),
  KEY `ship_type_id_idx` (`ship_type`),
  KEY `upgrade_type_id_idx` (`upgrade_type`),
  KEY `action_id_idx` (`action_1`),
  KEY `action_id2_idx` (`action_2`),
  KEY `action_id3_idx` (`action_3`),
  KEY `action_id4_idx` (`action_4`),
  KEY `upgrade_id_idx` (`flip_side_id`),
  CONSTRAINT `action_id1` FOREIGN KEY (`action_1`) REFERENCES `action` (`action_id`),
  CONSTRAINT `action_id2` FOREIGN KEY (`action_2`) REFERENCES `action` (`action_id`),
  CONSTRAINT `action_id3` FOREIGN KEY (`action_3`) REFERENCES `action` (`action_id`),
  CONSTRAINT `action_id4` FOREIGN KEY (`action_4`) REFERENCES `action` (`action_id`),
  CONSTRAINT `faction_id` FOREIGN KEY (`faction_id`) REFERENCES `faction` (`faction_id`),
  CONSTRAINT `ship_type_id` FOREIGN KEY (`ship_type`) REFERENCES `ship_type` (`ship_type_id`),
  CONSTRAINT `upgrade_id` FOREIGN KEY (`flip_side_id`) REFERENCES `upgrade` (`upgrade_id`),
  CONSTRAINT `upgrade_type_id` FOREIGN KEY (`upgrade_type`) REFERENCES `upgrade_type` (`upgrade_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `inventory` (
  `inventory_id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`inventory_id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ship_inventory` (
  `ship_inventory_id` int NOT NULL,
  `inventory_id` int DEFAULT NULL,
  `ship_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`ship_inventory_id`),
  KEY `inventory_id_idx` (`inventory_id`),
  KEY `ship_id_idx` (`ship_id`),
  CONSTRAINT `inventory_id` FOREIGN KEY (`inventory_id`) REFERENCES `inventory` (`inventory_id`),
  CONSTRAINT `ship_id` FOREIGN KEY (`ship_id`) REFERENCES `ship` (`ship_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `upgrade_inventory` (
  `upgrade_inventory_id` int NOT NULL,
  `inventory_id` int DEFAULT NULL,
  `upgrade_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`upgrade_inventory_id`),
  KEY `inventory_id_upgrade_idx` (`inventory_id`),
  KEY `upgrade_id_inventory_idx` (`upgrade_id`),
  CONSTRAINT `inventory_id_upgrade` FOREIGN KEY (`inventory_id`) REFERENCES `inventory` (`inventory_id`),
  CONSTRAINT `upgrade_id_inventory` FOREIGN KEY (`upgrade_id`) REFERENCES `upgrade` (`upgrade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `squad` (
  `squad_id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `squad_name` varchar(60) DEFAULT NULL,
  `faction_id` int DEFAULT NULL,
  `linked_to` int DEFAULT NULL,
  `total_points` int DEFAULT NULL,
  PRIMARY KEY (`squad_id`),
  KEY `user_id_squad_idx` (`user_id`),
  KEY `faction_id_squad_idx` (`faction_id`),
  KEY `linked_to_idx` (`linked_to`),
  CONSTRAINT `faction_id_squad` FOREIGN KEY (`faction_id`) REFERENCES `faction` (`faction_id`),
  CONSTRAINT `linked_to` FOREIGN KEY (`linked_to`) REFERENCES `ship` (`ship_id`),
  CONSTRAINT `user_id_squad` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `squad_ships` (
  `squad_ships_id` int NOT NULL,
  `squad_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `ship_id` int DEFAULT NULL,
  `faction_id` int DEFAULT NULL,
  PRIMARY KEY (`squad_ships_id`),
  KEY `squad_id_idx` (`squad_id`),
  KEY `user_id_squad_ship_idx` (`user_id`),
  KEY `faction_id_squad_ship_idx` (`faction_id`),
  KEY `ship_id_squad_idx` (`ship_id`),
  CONSTRAINT `faction_id_squad_ship` FOREIGN KEY (`faction_id`) REFERENCES `faction` (`faction_id`),
  CONSTRAINT `ship_id_squad` FOREIGN KEY (`ship_id`) REFERENCES `ship` (`ship_id`),
  CONSTRAINT `squad_id` FOREIGN KEY (`squad_id`) REFERENCES `squad` (`squad_id`),
  CONSTRAINT `user_id_squad_ship` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



