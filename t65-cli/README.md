
# T65 Squad Builder CLI
A CLI for the T65 Squad Builder application built using Spring Shell.

### Setup
To build the project you must have Java 11+ and Maven as well as a valid config file. For the purpose of this project
in class, the jar will be shipped in the repo so building will not be necessary (though if the configuration is modified
the project will need to be rebuilt for the changes to take effect). 

A properly formatted config file should be placed in `src/main/resource/application.yml` and should appear as follows:
```yaml
tcashcroft:
  t65-cli:
    squad-builder-url: "www.example.com/path/to/t65/backend"
    username: "someone"
```

### Usage
Spring shell provides some built-in functionality that will be helpful to the user. The shell supports a `help` command
that will list the available commands in the application, as well as offers tab completion of commands (and some values).
The commands have been grouped into four different command families and will be discussed below. It is worth noting that
some commands are not available before another command has been executed (for example, you are not allowed to add a ship
to your inventory before you have gotten your inventory). Viewing the `help` output will put an asterisk next to 
commands that are not currently available. Commands will become available as soon as their pre-condition is met.

#### Inventory Commands
- get-inventory: gets the inventory for the configured user. Supports pretty printing with the `--pretty` flag
- add-ship-to-inventory: adds a ship of the given ship name to your inventory
- remove-ship-from-inventory: removes a ship of the given ship name from your inventory
- add-upgrade-to-inventory: adds an upgrade of the given upgrade name to your inventory
- remove-upgrade-from-inventory: removes an upgrade of the given upgrade name from your inventory

#### Ship Commands
- get-ships: returns a list of all ships
- get-ship: returns a ship of the given ship name. Supports pretty printing with the `--pretty` flag
- get-ship-types: returns a list of ship types

#### Upgrade Commands
- get-upgrades: returns a list of all upgrades
- get-upgrade: returns an upgrade of the given upgrade name. Does not support pretty printing at this time

#### Squad Commands
- get-squads: returns a list of the user's squads
- get-squad: returns a squad with the given name. Supports pretty printing with the `--pretty` flag
- create-squad: creates a squad for the given name
- delete-squad: deletes the squad with the given name
- add-ship-to-squad: adds a ship of the given name to the squad
- get-ship-entry: gets the ship entry for the given ship entry ID
- delete-ship-entry: deletes the ship entry for the given ship entry ID
- get-squad-ships: returns the list of ship entries for the given squad
- add-upgrade-to-squad-ship: adds an upgrade of the given name to the ship entry of the given ship entry ID
- delete-upgrade-from-squad-ship: deletes an upgrade of the given name from the squad ship of the given ship entry ID
