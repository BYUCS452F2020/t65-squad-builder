package com.tcashcroft.t65.service;

import com.tcashcroft.t65.db.mongo.ShipRepository;
import com.tcashcroft.t65.exception.NotFoundException;
import com.tcashcroft.t65.model.Ship;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
public class ShipService {

    @Autowired
    private ShipRepository shipDao;

    public void createShip(Ship ship) {
        shipDao.save(ship);
    }

    public Ship getShip(String shipId) throws NotFoundException {
        Optional<Ship> shipOptional = shipDao.findById(shipId);
        return shipOptional.orElseThrow(NotFoundException::new);
    }

    public Ship getShipByNameId(String shipNameId) throws NotFoundException {
        Optional<Ship> shipOptional = shipDao.findShipByNameId(shipNameId);
        return shipOptional.orElseThrow(NotFoundException::new);
    }

    public List<Ship> getShipsByFaction(String faction) {
        return shipDao.findAllByFaction(faction);
    }

    public List<Ship> getShipsByShipType(String shipType) {
        return shipDao.findAllByShipType(shipType);
    }

    public List<Ship> getAllShips() {
        return shipDao.findAll();
    }
}
