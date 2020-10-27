package com.tcashcroft.t65.controller;

import com.tcashcroft.t65.model.Ship;
import com.tcashcroft.t65.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ship")
public class ShipController {

    @Autowired
    private ShipService shipService;

    @GetMapping("{id}")
    public Ship getShip(@PathVariable("id") final String id) {
        return shipService.getShip(id);
    }
}
