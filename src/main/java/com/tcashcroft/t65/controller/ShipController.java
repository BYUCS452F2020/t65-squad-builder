package com.tcashcroft.t65.controller;

import com.tcashcroft.t65.db.mysql.FactionDao;
import com.tcashcroft.t65.model.Ship;
import com.tcashcroft.t65.model.Utils;
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

    @Autowired
    private FactionDao factionDao;

    @GetMapping("{id}")
    public Ship getShip(@PathVariable("id") final String id) {
        return shipService.getShip(id);
    }

    @GetMapping("faction/{factionId}")
    public List<Ship> getShipsByFaction(@PathVariable("factionId") final String factionId) {
        Utils.Faction faction = factionDao.readFaction(factionId.toUpperCase()).orElseThrow(() -> new RuntimeException("bad request"));
        return shipService.getShipsByFaction(faction);
    }

    @GetMapping("ship_type/{shipTypeId}")
    public List<Ship> getShipsByShipType(@PathVariable("shipTypeId") final String shipTypeId) {
        Ship.ShipType shipType = Ship.ShipType.valueOf(shipTypeId.toUpperCase());
        return shipService.getShipsByShipType(shipType);
    }

    @GetMapping("/all")
    public List<Ship> getAllShips() {
        return shipService.getAllShips();
    }
}
