package com.tcashcroft.t65.cli.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.tcashcroft.t65.cli.Utils;
import com.tcashcroft.t65.cli.client.ShipClient;
import com.tcashcroft.t65.cli.model.Ship;
import com.tcashcroft.t65.cli.provider.ShipNameProvider;
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

    @ShellMethod("Get all ships or by ship type or faction if flagged as faction")
    public List<String> getShips(@ShellOption(defaultValue="") String value, @ShellOption(arity=1, defaultValue="false") boolean faction) {
        if (Strings.isNullOrEmpty(value)) {
            return shipClient.getShips().stream().map(Ship::getNameId).collect(Collectors.toList());
        } else {
            if (faction) {
                return shipClient.getShips("faction", value).stream().map(Ship::getNameId).collect(Collectors.toList());
            } else {
                return shipClient.getShips("type", value).stream().map(Ship::getNameId).collect(Collectors.toList());
            }
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
