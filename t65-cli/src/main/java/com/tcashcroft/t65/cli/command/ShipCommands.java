package com.tcashcroft.t65.cli.command;

import com.google.common.base.Strings;
import com.tcashcroft.t65.cli.client.ShipClient;
import com.tcashcroft.t65.cli.model.Ship;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ShellComponent
@Data
public class ShipCommands {

    @Autowired
    private ShipClient shipClient;

//    @ShellMethod("Get ships")
//    public List<String> getShips() {
//       return shipClient.getShips().stream().map(Ship::getNameId).collect(Collectors.toList());
//    }

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

//    @ShellMethod("Get ships by ship type")
//    public List<String> getShips(String shipType) {
//        return shipClient.getShips("ship_type", shipType).stream().map(Ship::getNameId).collect(Collectors.toList());
//    }

    @ShellMethod("Get ship")
    public Ship getShip(String shipName) {
        return shipClient.getShip(shipName);
    }

    @ShellMethod("Get ship types")
    public List<String> getShipTypes() {
        return shipClient.getShipTypes();
    }
}
