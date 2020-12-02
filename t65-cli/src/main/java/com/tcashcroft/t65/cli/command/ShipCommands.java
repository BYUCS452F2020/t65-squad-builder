package com.tcashcroft.t65.cli.command;

import com.tcashcroft.t65.cli.client.ShipClient;
import com.tcashcroft.t65.cli.model.Ship;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@Data
public class ShipCommands {

    @Autowired
    private ShipClient shipClient;

    @ShellMethod("Get ships")
    public List<String> getShips() {
       return shipClient.getShips().stream().map(Ship::getNameId).collect(Collectors.toList());
    }

    @ShellMethod("Get ship")
    public Ship getShip(String shipName) {
        return shipClient.getShip(shipName);
    }
}
