package com.tcashcroft.t65.service;

import com.tcashcroft.t65.db.mysql.ShipDao;
import com.tcashcroft.t65.model.Ship;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Setter
public class ShipService {

    @Autowired
    private ShipDao shipDao;

    public void createShip(Ship ship) {
        shipDao.createShip(ship);
    }

    public Ship getShip(String shipId) {
        return shipDao.readShip(shipId).orElseThrow(() -> new RuntimeException());
    }
}
