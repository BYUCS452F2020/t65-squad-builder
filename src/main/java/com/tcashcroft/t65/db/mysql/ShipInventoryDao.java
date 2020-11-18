package com.tcashcroft.t65.db.mysql;

import com.tcashcroft.t65.model.Ship;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Setter
public class ShipInventoryDao {

    @Autowired
    DataSource dataSource;

    @Autowired
    InventoryDao inventoryDao;

    @Autowired
    ShipDao shipDao;

    public void createOrIncrementShipInventory(String inventoryId, Ship ship) {
        if (readShipInventory(inventoryId, ship).isPresent()) {
            // value already exists in DB, so increment quantity
            try (Connection connection = dataSource.getConnection()) {
                int quantity = getShipInventoryQuantity(inventoryId, ship.getId()) + 1;
                updateShipInventoryQuantity(inventoryId, ship.getId(), quantity);
                return;
            } catch (SQLException e) {
                log.warn("Unable to increment count for id {} ship {}", inventoryId, ship.getId());
                throw new RuntimeException();
            }
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ship_inventory Value (?,?,?,?)");
            int i = 1;
            statement.setString(i++, UUID.randomUUID().toString());
            statement.setString(i++, inventoryId);
            statement.setString(i++, ship.getId());
            statement.setInt(i++, 1);

            int affectedRows = statement.executeUpdate();
            log.info("Create ship inventory affected {} rows", affectedRows);
        } catch (SQLException e) {
            log.warn(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void deleteOrDecrementShipInventory(String inventoryId, Ship ship) {
        if (readShipInventory(inventoryId, ship).isPresent()) {
            int quantity = getShipInventoryQuantity(inventoryId, ship.getId());
            if (quantity <= 1) {
                try(Connection connection = dataSource.getConnection()) {
                    PreparedStatement statement = connection.prepareStatement("DELETE FROM ship_inventory WHERE inventory = ? AND ship = ?");
                    int i = 1;
                    statement.setString(i++, inventoryId);
                    statement.setString(i++, ship.getId());
                    int affectedRows = statement.executeUpdate();
                    log.info("Delete from ship inventory affected {} rows", affectedRows);
                } catch (SQLException e) {
                    log.error(e.getMessage(), e);
                    throw new RuntimeException();
                }
            } else {
                updateShipInventoryQuantity(inventoryId, ship.getId(), --quantity);
            }
        } else {
            return;
        }
    }

    public void createAllShipInventory(String inventoryId, List<Ship> ships) {
        for (Ship s : ships) {
            try {
               createOrIncrementShipInventory(inventoryId, s);
            } catch (RuntimeException e) {
               log.warn("Unable to create ship inventory for id {} ship {}", inventoryId, s.getId());
            }
        }
        return;
    }

    public Optional<ShipInventory> readShipInventory(String shipInventoryId, Ship ship) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ship_inventory WHERE inventory = ? AND ship = ?");
            int i = 1;
            statement.setString(i++, shipInventoryId);
            statement.setString(i++, ship.getId());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                log.info("Result Set: {}", rs.getFetchSize());
                ShipInventory shipInventory = new ShipInventory();
                shipInventory.setShip(shipDao.readShip(rs.getString("ship")).orElse(null));
                shipInventory.setId(rs.getString("id"));
                shipInventory.setInventory(rs.getString("inventory"));
                shipInventory.setQuantity(rs.getInt("quantity"));
                return Optional.of(shipInventory);
            } else return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<Ship> readAllShipInventory(String inventoryId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ship_inventory WHERE inventory = ?");
            int i = 1;
            statement.setString(i++, inventoryId);
            ResultSet rs = statement.executeQuery();
            List<Ship> ships = new ArrayList<>();
            while (rs.next()) {
               String shipId = rs.getString("ship");
               try {
                   Optional<Ship> shipOptional = shipDao.readShip(shipId);
                   if (shipOptional.isPresent()) {
                       ships.add(shipOptional.get());
                   }
               } catch (RuntimeException e) {
                   log.warn("An error occurred fetching the ship for ship inventory");
                   log.warn(e.getMessage(), e);
               }
            }
            return ships;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    private int getShipInventoryQuantity(String inventoryId, String shipId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT quantity FROM ship_inventory WHERE inventory = ? AND ship = ?");
            int i = 1;
            statement.setString(i++, inventoryId);
            statement.setString(i++, shipId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int quantity = rs.getInt("quantity");
                quantity++;
                return quantity;
            } else return 0;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    private void updateShipInventoryQuantity(String inventoryId, String shipId, int newQuantity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE ship_inventory SET quantity = ? WHERE inventory = ? AND ship = ?");
            int i = 1;
            statement.setInt(i++, newQuantity);
            statement.setString(i++, inventoryId);
            statement.setString(i++, shipId);
            int affectedRows = statement.executeUpdate();
            log.info("Update ship inventory affected {} rows", affectedRows);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    @Data
    public static class ShipInventory {
       private String id;
       private String inventory;
       private Ship ship;
       private int quantity;
    }
}
