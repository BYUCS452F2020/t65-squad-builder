-- CREATE DATABASE `t65_squad_builder_test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
-- USE `t65_squad_builder_test`;

CREATE TABLE IF NOT EXISTS `color` (
  `name` varchar(10) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `action` (
  `id` varchar(36) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `color` varchar(10) DEFAULT NULL,
  `linked_action` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `action_color_idx` (`color`),
  KEY `action_linked_action_idx` (`linked_action`),
  CONSTRAINT `action_color` FOREIGN KEY (`color`) REFERENCES `color` (`name`),
  CONSTRAINT `action_linked_action` FOREIGN KEY (`linked_action`) REFERENCES `action` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `faction` (
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `upgrade_type` (
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(15) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `ship_type` (
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `size` (
  `name` varchar(10) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `ship` (
  `id` varchar(36) NOT NULL,
  `faction` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `ship_type` varchar(45) DEFAULT NULL,
  `name_limit` int DEFAULT NULL,
  `call_sign` varchar(45) DEFAULT NULL,
  `front_arc` int DEFAULT NULL,
  `rear_arc` int DEFAULT NULL,
  `turret_arc` int DEFAULT NULL,
  `agility` int DEFAULT NULL,
  `hull` int DEFAULT NULL,
  `shield` int DEFAULT NULL,
  `force` int DEFAULT NULL,
  `ship_ability_text` varchar(300) DEFAULT NULL,
  `pilot_ability_text` varchar(300) DEFAULT NULL,
  `action_1` varchar(36) DEFAULT NULL,
  `action_2` varchar(36) DEFAULT NULL,
  `action_3` varchar(36) DEFAULT NULL,
  `action_4` varchar(36) DEFAULT NULL,
  `action_5` varchar(36) DEFAULT NULL,
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
  `dial_code` varchar(45) DEFAULT NULL,
  `size` varchar(10) DEFAULT NULL,
  `initiative` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ship_faction_idx` (`faction`),
  KEY `ship_type_idx` (`ship_type`),
  KEY `ship_action_1_idx` (`action_1`),
  KEY `ship_action_2_idx` (`action_2`),
  KEY `ship_action_3_idx` (`action_3`),
  KEY `ship_action_4_idx` (`action_4`),
  KEY `ship_size_idx` (`size`),
  CONSTRAINT `ship_action_1` FOREIGN KEY (`action_1`) REFERENCES `action` (`id`),
  CONSTRAINT `ship_action_2` FOREIGN KEY (`action_2`) REFERENCES `action` (`id`),
  CONSTRAINT `ship_action_3` FOREIGN KEY (`action_3`) REFERENCES `action` (`id`),
  CONSTRAINT `ship_action_4` FOREIGN KEY (`action_4`) REFERENCES `action` (`id`),
  CONSTRAINT `ship_action_5` FOREIGN KEY (`action_5`) REFERENCES `action` (`id`),
  CONSTRAINT `ship_faction` FOREIGN KEY (`faction`) REFERENCES `faction` (`name`),
  CONSTRAINT `ship_size` FOREIGN KEY (`size`) REFERENCES `size` (`name`),
  CONSTRAINT `ship_type` FOREIGN KEY (`ship_type`) REFERENCES `ship_type` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `upgrade` (
  `id` varchar(36) NOT NULL,
  `faction` varchar(45) DEFAULT NULL,
  `name` varchar(300) DEFAULT NULL,
  `name_limit` int DEFAULT NULL,
  `ship_type` varchar(45) DEFAULT NULL,
  `upgrade_type` varchar(45) DEFAULT NULL,
  `upgrade_text` varchar(500) DEFAULT NULL,
  `action_1` varchar(36) DEFAULT NULL,
  `action_2` varchar(36) DEFAULT NULL,
  `action_3` varchar(36) DEFAULT NULL,
  `action_4` varchar(36) DEFAULT NULL,
  `flip_side` varchar(36) DEFAULT NULL,
  `points_cost` int DEFAULT NULL,
  `hyperspace_legal` tinyint DEFAULT NULL,
  `extended_legal` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `upgrade_faction_idx` (`faction`),
  KEY `upgrade_ship_type_idx` (`ship_type`),
  KEY `upgrade_upgrade_type_idx` (`upgrade_type`),
  KEY `upgrade_action_1_idx` (`action_1`),
  KEY `upgrade_action_2_idx` (`action_2`),
  KEY `upgrade_action_3_idx` (`action_3`),
  KEY `upgrade_action_4_idx` (`action_4`),
  KEY `upgrade_flip_side_idx` (`flip_side`),
  CONSTRAINT `upgrade_action_1` FOREIGN KEY (`action_1`) REFERENCES `action` (`id`),
  CONSTRAINT `upgrade_action_2` FOREIGN KEY (`action_2`) REFERENCES `action` (`id`),
  CONSTRAINT `upgrade_action_3` FOREIGN KEY (`action_3`) REFERENCES `action` (`id`),
  CONSTRAINT `upgrade_action_4` FOREIGN KEY (`action_4`) REFERENCES `action` (`id`),
  CONSTRAINT `upgrade_faction` FOREIGN KEY (`faction`) REFERENCES `faction` (`name`),
  CONSTRAINT `upgrade_flip_side` FOREIGN KEY (`flip_side`) REFERENCES `upgrade` (`id`),
  CONSTRAINT `upgrade_ship_type` FOREIGN KEY (`ship_type`) REFERENCES `ship_type` (`name`),
  CONSTRAINT `upgrade_upgrade_type` FOREIGN KEY (`upgrade_type`) REFERENCES `upgrade_type` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `inventory` (
  `id` varchar(36) NOT NULL,
  `username` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `inventory_username_idx` (`username`),
  CONSTRAINT `inventory_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `ship_inventory` (
  `id` varchar(36) NOT NULL,
  `inventory` varchar(36) DEFAULT NULL,
  `ship` varchar(36) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ship_inventory_inventory_id_idx` (`inventory`),
  KEY `ship_inventory_ship_idx` (`ship`),
  CONSTRAINT `ship_inventory_inventory_id` FOREIGN KEY (`inventory`) REFERENCES `inventory` (`id`),
  CONSTRAINT `ship_inventory_ship` FOREIGN KEY (`ship`) REFERENCES `ship` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `upgrade_inventory` (
  `id` varchar(36) NOT NULL,
  `inventory` varchar(36) DEFAULT NULL,
  `upgrade` varchar(36) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `upgrade_inventory_inventory_id_idx` (`inventory`),
  KEY `upgrade_inventory_upgrade_idx` (`upgrade`),
  CONSTRAINT `upgrade_inventory_inventory_id` FOREIGN KEY (`inventory`) REFERENCES `inventory` (`id`),
  CONSTRAINT `upgrade_inventory_upgrade` FOREIGN KEY (`upgrade`) REFERENCES `upgrade` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `squad` (
  `id` varchar(36) NOT NULL,
  `username` varchar(15) DEFAULT NULL,
  `squad_name` varchar(45) DEFAULT NULL,
  `faction` varchar(45) DEFAULT NULL,
  `total_points` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `squad_username_idx` (`username`),
  KEY `squad_faction_idx` (`faction`),
  CONSTRAINT `squad_faction` FOREIGN KEY (`faction`) REFERENCES `faction` (`name`),
  CONSTRAINT `squad_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `squad_ship` (
  `id` varchar(36) NOT NULL,
  `squad` varchar(36) DEFAULT NULL,
  `ship` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `squad_ship_squad_idx` (`squad`),
  KEY `squad_ship_ship_idx` (`ship`),
  CONSTRAINT `squad_ship_ship` FOREIGN KEY (`ship`) REFERENCES `ship` (`id`),
  CONSTRAINT `squad_ship_squad` FOREIGN KEY (`squad`) REFERENCES `squad` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `squad_upgrade` (
  `id` varchar(36) NOT NULL,
  `squad_ship` varchar(36) DEFAULT NULL,
  `upgrade` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `squad_upgrade_squad_ship_idx` (`squad_ship`),
  KEY `squad_upgrade_upgrade_idx` (`upgrade`),
  CONSTRAINT `squad_upgrade_squad_ship` FOREIGN KEY (`squad_ship`) REFERENCES `squad_ship` (`id`),
  CONSTRAINT `squad_upgrade_upgrade` FOREIGN KEY (`upgrade`) REFERENCES `upgrade` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
