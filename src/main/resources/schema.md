
# Relational Database Schema:
Underlined terms are primary keys. Italicized terms are foreign keys. 
## User-created Data

Inventory(<ins>inventory_id</ins>, <ins>user_id</ins>)

ShipInventory(<ins>ship_inventory_id</ins>, *inventory_id*, *ship_id*, quantity)
 - *inventory_id* relates to Inventory
 - *ship_id* relates to Ship

UpgradeInventory(<ins>upgrade_inventory_id</ins>, *inventory_id*, *upgrade_id*, quantity)
 - *inventory_id* relates to Inventory
 - *upgrade_id* relates to Upgrade
 
Squad(<ins>squad_id</ins>, <ins>user_id</ins>, squad_name, *faction_id*)
 - *faction_id* relates to Faction
 
SquadShips(<ins>squad_ship_id</ins>, *squad_id*, *ship_id*, *faction_id*)
 - *squad_id* relates to Squad
 - *ship_id* relates to Ship
 - *faction_id* relates to Faction
 
SquadUpgrades(<ins>squad_upgrade_id</ins>, *squad_ship_id*, *upgrade_id*)
 - *squad_ship_id* relates to SquadShips
 - *upgrade_id* relates to Upgrade

## Prepared Data

Ship(<ins>ship_id</ins>, <ins>name</ins>, *faction_id*, *ship_type_id*, name_limit, call_sign, front_arc, rear_arc, turret_arc, agility, hull, shield, force, ability_text, *action_1*, *action_2*, *action_3*, *action_4*, astromech_upgrades, cannon_upgrades, configuration_upgrades, crew_upgrades, force_upgrades, gunner_upgrades, hyperdrive_upgrades, illicit_upgrades, modification_upgrades, missile_upgrades, payload_upgrades, sensor_upgrades, tactical_relay_upgrades, talent_upgrades, tech_upgrades, title_upgrades, torpedo_upgrades, turret_upgrades, points_cost, hyperspace_legal, extended_legal, *dial_code*, *size_id*)
 - *faction_id* relates to Faction
 - *ship_type_id* relates to ShipType
 - *action_1*, *action_2*, *action_3*, *action_4* relates to Action
 - *size_id* relates to Size
 
Upgrade(<ins>upgrade_id</ins>, <ins>name</ins>, *faction_id*, name_limit, *ship_type_id*, *upgrade_type_id*, upgrade_text, *action_1*, *action_2*, *action_3*, *action_4*, *flip_side_id*, points_cost, hyperspace_legal, extended_legal)
 - *faction_id* relates to Faction
 - *ship_type_id* relates to ShipType
 - *upgrade_type_id* relates to UpgradeType
 - *action_1*, *action_2*, *action_3*, *action_4* relates to Action
 - *flip_side_id* relates to Upgrade

## Lookup Tables:

ShipType(<ins>ship_type_id</ins>, ship_type)

Size(<ins>size_id</ins>, size_name)

Faction(<ins>faction_id</ins>, faction_name)

Color(<ins>color_id</ins>, color_name)

Action(<ins>action_id</ins>, action_name, *color_id*)
 - *color_id* relates to Color
 
UpgradeType(<ins>upgrade_type_id</ins>, upgrade_type_name)
 

## Explanation of Tables
The core entities for the application are the ship and upgrade. The ship table contains all the stats needed to understand the capabilities of the given ship (which is the core game piece a plyer controls). 
A ship may be equipped with upgrade cards, but only of the type and quantities allowed by the ship (each upgrade column in Ship will contain the count of allowed upgrades of that type).
Both of these entities can be stored in an Inventory through the appropriate ShipInventory and UpgradeInventory tables. Inventory exists to link a player's ShipInventory with their UpgradeInventory.
A Squad functions similarly to an inventory, but with the addition of a reference from the SquadUpgrade to the SquadShip that the upgrade is equipped to. 

The remaining tables, ShipType, Size, Faction, Color, Action, and UpgradeType exist as lookup tables. In application code they would likely be implemented as Enumerated Types. Given their shared nature
across different entities, they are broken out for convenience of adding additional elements as the game evolves. 

All data found in the Ship and Upgrade tables will be known at application startup, so those tables will be populated when the back end application starts up (this includes the data in the lookup tables as well).
The only tables that will be modified by users of the application will be the Inventory* and Squad* tables. 

## Personal Notes
I drafted the schema in its entirety (I am currently a team of 1). I didn't love what I had to do to make the inventory and squads work - I would prefer to find some way to do it as a single table, but since
ships and upgrades are pretty divergent in their data, there wasn't an efficient way to cram those into a single table (that I could come up with). I also debated on the lookup tables whether to have the value
be the key as well (i.e. just have a single column) but opted to have a unique ID as well as the name (I didn't think the difference was big enough to fuss over). Overall, I'm relatively pleased with this schema -
it took a few iterations to get it to this point. If there are any obvious changes or simplifications you would recommend, I would love to hear them. I realize that this is over the table limit, but I don't really
consider the lookup tables as being hefty enough to count. In the worst case scenario, if I have to cut tables for time or complexity constraints, I will remove the upgrades from the application, which will simplify the
squad and inventory bringing the table count down significantly.
