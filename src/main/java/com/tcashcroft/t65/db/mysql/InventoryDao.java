package com.tcashcroft.t65.db.mysql;

import com.tcashcroft.t65.model.Inventory;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Service
@Slf4j
@Setter
public class InventoryDao {

    @Autowired
    UsersDao usersDao;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ShipInventoryDao shipInventoryDao;

    @Autowired
    private UpgradeInventoryDao upgradeInventoryDao;

    public void createInventory(String username) {
        if (readInventory(username, true).isPresent()) {
            return;
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO inventory Value (?, ?)");
            int i = 1;
            statement.setString(i++, null);
            statement.setString(i++, username);
            int affectedRows = statement.executeUpdate();
            log.info("Create inventory affected {} rows", affectedRows);
        } catch (SQLException e) {
            log.warn(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Optional<Inventory> readInventory(String id, boolean isUsername) {
        try (Connection connection = dataSource.getConnection()){
            String sql;
            if (isUsername) {
               sql = "SELECT * FROM inventory WHERE username = ?";
            } else {
                sql = "SELECT * FROM inventory WHERE id = ?";
            }
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                log.info("Result Set: {}", rs.getFetchSize());
                Inventory inventory = new Inventory();
                inventory.setId(rs.getString("id"));
                inventory.setUsername(rs.getString("username"));
                inventory.setShips(shipInventoryDao.readAllShipInventory(rs.getString("id")));
                inventory.setUpgrades(upgradeInventoryDao.readAllUpgradeInventory(rs.getString("id")));

                return Optional.of(inventory);
            } else return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
