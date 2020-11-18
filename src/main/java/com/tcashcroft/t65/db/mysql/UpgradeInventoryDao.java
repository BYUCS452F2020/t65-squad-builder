package com.tcashcroft.t65.db.mysql;

import com.tcashcroft.t65.model.Upgrade;
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
public class UpgradeInventoryDao {

    @Autowired
    DataSource dataSource;

    @Autowired
    InventoryDao inventoryDao;

    @Autowired
    UpgradeDao upgradeDao;


    public void createOrIncrementUpgradeInventory(String inventoryId, Upgrade upgrade) {
        if (readUpgradeInventory(inventoryId, upgrade).isPresent()) {
            // value already exists in DB, so increment quantity
            try (Connection connection = dataSource.getConnection()) {
                int quantity = getUpgradeInventoryQuantity(inventoryId, upgrade.getId()) + 1;
                updateUpgradeInventoryQuantity(inventoryId, upgrade.getId(), quantity);
                return;
            } catch (SQLException e) {
                log.warn("Unable to increment count for id {} upgrade {}", inventoryId, upgrade.getId());
                throw new RuntimeException();
            }
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO upgrade_inventory Value (?,?,?,?)");
            int i = 1;
            statement.setString(i++, UUID.randomUUID().toString());
            statement.setString(i++, inventoryId);
            statement.setString(i++, upgrade.getId());
            statement.setInt(i++, 1);

            int affectedRows = statement.executeUpdate();
            log.info("Create upgrade inventory affected {} rows", affectedRows);
        } catch (SQLException e) {
            log.warn(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void deleteOrDecrementUpgradeInventory(String inventoryId, Upgrade upgrade) {
        if (readUpgradeInventory(inventoryId, upgrade).isPresent()) {
            int quantity = getUpgradeInventoryQuantity(inventoryId, upgrade.getId());
            if (quantity <= 1) {
                try(Connection connection = dataSource.getConnection()) {
                   PreparedStatement statement = connection.prepareStatement("DELETE FROM upgrade_inventory WHERE inventory = ? AND upgrade = ?");
                   int i = 1;
                   statement.setString(i++, inventoryId);
                   statement.setString(i++, upgrade.getId());
                   int affectedRows = statement.executeUpdate();
                   log.info("Delete from upgrade inventory affected {} rows", affectedRows);
                } catch (SQLException e) {
                    log.error(e.getMessage(), e);
                    throw new RuntimeException();
                }
            } else {
               updateUpgradeInventoryQuantity(inventoryId, upgrade.getId(), --quantity);
            }
        } else {
            return;
        }
    }

    public void createAllUpgradeInventory(String inventoryId, List<Upgrade> upgrades) {
        for (Upgrade u : upgrades) {
            try {
                createOrIncrementUpgradeInventory(inventoryId, u);
            } catch (RuntimeException e) {
                log.warn("Unable to create ship inventory for id {} ship {}", inventoryId, u.getId());
            }
        }
        return;
    }

    public Optional<UpgradeInventory> readUpgradeInventory(String upgradeInventoryId, Upgrade upgrade) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM upgrade_inventory WHERE inventory = ? AND upgrade = ?");
            int i = 1;
            statement.setString(i++, upgradeInventoryId);
            statement.setString(i++, upgrade.getId());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                log.info("Result Set: {}", rs.getFetchSize());
                UpgradeInventory upgradeInventory = new UpgradeInventory();
                upgradeInventory.setUpgrade(upgradeDao.readUpgrade(rs.getString("upgrade")).orElse(null));
                upgradeInventory.setId(rs.getString("id"));
                upgradeInventory.setInventory(rs.getString("inventory"));
                upgradeInventory.setQuantity(rs.getInt("quantity"));
                return Optional.of(upgradeInventory);
            } else return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<Upgrade> readAllUpgradeInventory(String inventoryId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM upgrade_inventory WHERE inventory = ?");
            int i = 1;
            statement.setString(i++, inventoryId);
            ResultSet rs = statement.executeQuery();
            List<Upgrade> upgrades = new ArrayList<>();
            while (rs.next()) {
                String shipId = rs.getString("upgrade");
                try {
                    Optional<Upgrade> upgradeOptional = upgradeDao.readUpgrade(shipId);
                    if (upgradeOptional.isPresent()) {
                        upgrades.add(upgradeOptional.get());
                    }
                } catch (RuntimeException e) {
                    log.warn("An error occurred fetching the upgrade for upgrade inventory");
                    log.warn(e.getMessage(), e);
                }
            }
            return upgrades;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    private int getUpgradeInventoryQuantity(String inventoryId, String upgradeid) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT quantity FROM upgrade_inventory WHERE inventory = ? AND upgrade = ?");
            int i = 1;
            statement.setString(i++, inventoryId);
            statement.setString(i++, upgradeid);
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

    private void updateUpgradeInventoryQuantity(String inventoryId, String upgradeId, int newQuantity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE upgrade_inventory SET quantity = ? WHERE inventory = ? AND upgrade = ?");
            int i = 1;
            statement.setInt(i++, newQuantity);
            statement.setString(i++, inventoryId);
            statement.setString(i++, upgradeId);
            int affectedRows = statement.executeUpdate();
            log.info("Update upgrade inventory affected {} rows", affectedRows);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    @Data
    public static class UpgradeInventory {
        private String id;
        private String inventory;
        private Upgrade upgrade;
        private int quantity;
    }
}
