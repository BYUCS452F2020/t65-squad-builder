# T-65 Squad Builder

The T-65 Squad Builder is a squad builder and inventory management system for FantasyFlight Games' X-Wing 2.0 tabletop miniatures game. While there are many squad builders for X-Wing available, there are not very many that incorporate an inventory system in the squad builder.

## Team
I am looking for one other developer to join the team. UI experience preferred (but not required).

## Relational Database
We will use a MySQL implementation on the backend. The project will create 5 tables, a user account table, an inventory table, a squad table, a ship table, and an upgrade table. We might condense the tables down a bit or limit features depending on time constraints.

## NoSQL Database
We will use MongoDB for our NoSQL implementation.

## Business
This will be an open source project. It is to be treated as a proof of concept project, as we will not be addressing proper authentication security, etc. It's just to cut our teeth on database design and interactions, and how they integrate into a backend stack with a front end interface.

## Legal
As an open source project offered free of charge no specific licensing is required. All attributions will be made to FantasyFlight Games, Lucasfilms ltd, and Disney.

## Technical
The backend will be created using Java utilizing the Spring framework. The front end stack is TBD.


# Setup
A mongo database must be available for the backend to connect to. The configuration files for the application will define
this connection. Java 11 + maven is required for building the application from source. The configuration file needs to 
be accessible to the application (see Spring documentation for this). Also see the Spring Mongo DB documentation for 
additional configuration. Application specific configuration is structured as follows:

```yaml
tcashcroft:
  t65-squad-builder:
    harvester:
      xwing-data2:
        data-repo-uri: "https://github.com/guidokessels/xwing-data2.git"
        download-dir: "/path/for/downloading/data/repo"
        data-repo-location: "xwing-data2"
        actions-path: "data/actions/actions.json"
        factions-path: "data/factions/factions.json"
        pilots-dir: "data/pilots/"
        upgrades-dir: "data/upgrades/"
        ffg-xws-path: "data/ffg-xws.json"
      upgrade-card-api-uri: "https://x-wing-api.fantasyflightgames.com/cards/upgrades/"
      ship-card-api-uri: "https://x-wing-api.fantasyflightgames.com/cards/pilots/"
```
