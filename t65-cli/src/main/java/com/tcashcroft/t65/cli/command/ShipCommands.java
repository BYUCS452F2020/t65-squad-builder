package com.tcashcroft.t65.cli.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.tcashcroft.t65.cli.Utils;
import com.tcashcroft.t65.cli.client.ShipClient;
import com.tcashcroft.t65.cli.model.Ship;
import com.tcashcroft.t65.cli.provider.FactionProvider;
import com.tcashcroft.t65.cli.provider.ShipNameProvider;
import com.tcashcroft.t65.cli.provider.ShipTypeProvider;
import lombok.Data;
import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@Data
public class ShipCommands {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ShipClient shipClient;

    @Autowired
    private Terminal terminal;

    @ShellMethod("Get all ships")
    public List<String> getShips(@ShellOption(valueProvider = FactionProvider.class, defaultValue = "") String faction, @ShellOption(valueProvider = ShipTypeProvider.class, defaultValue = "") String shipType) {
        if (!Strings.isNullOrEmpty(faction)) {
           return shipClient.getShips("faction", faction).stream().map(Ship::getNameId).collect(Collectors.toList());
        } else if (!Strings.isNullOrEmpty(shipType)) {
            return shipClient.getShips("type", shipType).stream().map(Ship::getNameId).collect(Collectors.toList());
        } else {
            return shipClient.getShips().stream().map(Ship::getNameId).collect(Collectors.toList());
        }
    }

    @ShellMethod("Get ship")
    public Object getShip(@ShellOption(valueProvider = ShipNameProvider.class) String shipName, @ShellOption(defaultValue = "false") boolean pretty) {
        Ship ship = shipClient.getShip(shipName);
        if (pretty) {
            return Utils.getShipAsTable(ship);
        } else {
            return ship;
        }
    }

    @ShellMethod("Get ship types")
    public List<String> getShipTypes() {
        return shipClient.getShipTypes();
    }
}
