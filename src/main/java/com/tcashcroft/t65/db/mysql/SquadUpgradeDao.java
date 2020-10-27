package com.tcashcroft.t65.db.mysql;

import com.tcashcroft.t65.model.SquadUpgrade;
import com.tcashcroft.t65.model.Upgrade;
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

@Service
@Slf4j
@Setter
public class SquadUpgradeDao {

    @Autowired
    DataSource dataSource;

    @Autowired
    UpgradeDao upgradeDao;

    @Autowired
    SquadDao squadDao;

    public void createSquadUpgrade(String squadShip, Upgrade upgrade) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO squad_upgrade VALUE (?,?,?)");
            int i = 1;
            statement.setString(i++, null);
            statement.setString(i++, squadShip);
            statement.setString(i++, upgrade.getId());
            int affectedRows = statement.executeUpdate();
            log.info("Create squad upgrade affected {} rows", affectedRows);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Optional<SquadUpgrade> readSquadUpgrade(String squadUpgradeId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM squad_upgrade WHERE id = ?");
            int i = 1;
            statement.setString(i++, squadUpgradeId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                SquadUpgrade squadUpgrade = new SquadUpgrade();
                squadUpgrade.setId(rs.getString("id"));
                squadUpgrade.setSquadShip(rs.getString("squad_ship"));
                squadUpgrade.setUpgrade(upgradeDao.readUpgrade(rs.getString("upgrade")).orElseThrow(() -> new RuntimeException("The upgrade in squad upgrade was not found. Squad upgrade id: " + squadUpgradeId)));
                return Optional.of(squadUpgrade);
            } else return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<SquadUpgrade> readSquadUpgrades(String squadShip) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM squad_upgrade WHERE squad_ship = ?");
            int i = 1;
            statement.setString(i++, squadShip);
            ResultSet rs = statement.executeQuery();

            List<SquadUpgrade> output = new ArrayList<>();
            while (rs.next()) {
                SquadUpgrade squadUpgrade = new SquadUpgrade();
                squadUpgrade.setId(rs.getString("id"));
                squadUpgrade.setSquadShip(rs.getString("squad_ship"));
                squadUpgrade.setUpgrade(upgradeDao.readUpgrade(rs.getString("upgrade")).orElseThrow(() -> new RuntimeException("The upgrade in squad upgrade was not found. Squad ship: " + squadShip)));
                output.add(squadUpgrade);
            }

            return output;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public int deleteSquadUpgrade(String squadUpgradeId) {
        if (readSquadUpgrade(squadUpgradeId).isEmpty()) {
            log.info("The given squad upgrade was not found. Id {}", squadUpgradeId);
            return 0;
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM squad_upgrade WHERE id = ?");
            int i = 1;
            statement.setString(i++, squadUpgradeId);
            int affectedRows = statement.executeUpdate();
            log.info("Delete squad upgrade affected {} rows", affectedRows);
            return affectedRows;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public int deleteSquadUpgrades(String squadShip) {
        try (Connection connection = dataSource.getConnection()) {
           PreparedStatement statement = connection.prepareStatement("DELETE FROM squad_upgrade WHERE squad_ship = ?");
           int i = 1;
           statement.setString(i++, squadShip);
           int affectedRows = statement.executeUpdate();
           log.info("Delete from squad upgrades affected {} rows", affectedRows);
           return affectedRows;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
