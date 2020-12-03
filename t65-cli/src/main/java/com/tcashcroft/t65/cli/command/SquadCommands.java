package com.tcashcroft.t65.cli.command;

import com.tcashcroft.t65.cli.client.SquadClient;
import com.tcashcroft.t65.cli.model.Squad;
import com.tcashcroft.t65.cli.provider.ShipNameProvider;
import com.tcashcroft.t65.cli.provider.SquadNameProvider;
import com.tcashcroft.t65.cli.provider.SquadShipEntryIdProvider;
import com.tcashcroft.t65.cli.provider.UpgradeNameProvider;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
@Data
public class SquadCommands {

    @Autowired
    private SquadClient squadClient;

    private Squad squad;

    private Squad.ShipEntry shipEntry;

    @ShellMethod("Get squad")
    public Squad getSquad(@ShellOption(valueProvider = SquadNameProvider.class) String name) {
        squad = squadClient.getSquad(name);
        return squad;
    }

    @ShellMethod("Create squad")
    public Squad createSquad(String name) {
        squad = squadClient.createSquad(name);
        return squad;
    }

    @ShellMethod("Delete squad")
    public void deleteSquad(@ShellOption(valueProvider = SquadNameProvider.class) String name) {
        squadClient.deleteSquad(name);
        squad = null;
    }

    @ShellMethod("Add ship to squad")
    public Squad.ShipEntry addShipToSquad(@ShellOption(valueProvider = ShipNameProvider.class) String shipName) {
        shipEntry = squadClient.addShipToSquad(squad.getName(), shipName);
        squad = squadClient.getSquad(squad.getName());
        return shipEntry;
    }

    @ShellMethod("Get ship entry")
    public Squad.ShipEntry getShipEntry(@ShellOption(valueProvider = SquadShipEntryIdProvider.class, defaultValue = "") String shipEntryId) {
        if (shipEntryId != null && !shipEntryId.isBlank()) {
            shipEntry = squadClient.getShipEntry(squad.getName(), shipEntryId);
        }
        return shipEntry;
    }

    @ShellMethod("Delete ship entry")
    public Squad deleteShipEntry(@ShellOption(valueProvider = SquadShipEntryIdProvider.class, defaultValue = "") String shipEntryId) {
        if (shipEntryId == null || shipEntryId.isBlank()) {
            squadClient.deleteShipEntry(squad.getName(), shipEntry.getId());
        } else {
            squadClient.deleteShipEntry(squad.getName(), shipEntryId);
            squad = squadClient.getSquad(squad.getName());
        }
        return squad;
    }

    @ShellMethod("Get ship entries")
    public List<Squad.ShipEntry> getSquadShips() {
        return squad.getShips();
    }

    @ShellMethod("Add upgrade to squad ship")
    public Squad.ShipEntry addUpgradeToSquadShip(@ShellOption(valueProvider = UpgradeNameProvider.class) String upgradeName) {
        shipEntry = squadClient.addUpgradeToSquadShip(squad.getName(), shipEntry.getId(), upgradeName);
        squad = squadClient.getSquad(squad.getName());
        return shipEntry;
    }

    @ShellMethod("Delete upgrade from squad ship")
    public Squad.ShipEntry deleteUpgradeFromSquadShip(@ShellOption(valueProvider = UpgradeNameProvider.class) String upgradeName) {
        shipEntry = squadClient.deleteUpgradeFromShip(squad.getName(), shipEntry.getId(), upgradeName);
        squad = squadClient.getSquad(squad.getName());
        return shipEntry;
    }

    public Availability addShipToSquadAvailability() {
        return isSquadAvailable();
    }

    public Availability getShipEntryAvailability() {
        return isSquadAvailable();
    }

    public Availability deleteShipEntryAvailability() {
        return isSquadAvailable();
    }

    public Availability getSquadShipsAvailability() {
        return isSquadAvailable();
    }

    public Availability addUpgradeToSquadShipAvailability() {
        return isShipEntryAvailable();
    }

    public Availability deleteUpgradeFromSquadShipAvailability() {
        return isShipEntryAvailable();
    }

    private Availability isSquadAvailable() {
        if (squad == null) {
            return Availability.unavailable("a squad has not been selected");
        } else {
            return Availability.available();
        }
    }

    private Availability isShipEntryAvailable() {
        if (shipEntry == null) {
            return Availability.unavailable("a ship entry has not been selected");
        } else {
            return Availability.available();
        }
    }


}
