package com.tcashcroft.t65.controller;

import com.tcashcroft.t65.exception.NotFoundException;
import com.tcashcroft.t65.model.Ship;
import com.tcashcroft.t65.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("ship")
public class ShipController {

    @Autowired
    private ShipService shipService;

    @GetMapping("{id}")
    public Ship getShip(@PathVariable("id") final String id) throws NotFoundException {
        return shipService.getShip(id);
    }

    @GetMapping("faction/{faction}")
    public List<Ship> getShipsByFaction(@PathVariable final String faction) {
        return shipService.getShipsByFaction(faction);
    }

    @GetMapping("type/{shipType}")
    public List<Ship> getShipsByShipType(@PathVariable final String shipType) {
        return shipService.getShipsByShipType(shipType);
    }

    @GetMapping("/name/{name}")
    public Ship getShipsByShipName(@PathVariable final String name) throws NotFoundException {
        return shipService.getShipByNameId(name);
    }

    @GetMapping("/all")
    public List<Ship> getAllShips() {
        return shipService.getAllShips();
    }
}
