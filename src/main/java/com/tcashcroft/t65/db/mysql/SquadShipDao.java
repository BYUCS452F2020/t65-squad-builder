package com.tcashcroft.t65.db.mysql;

import com.tcashcroft.t65.model.Ship;
import com.tcashcroft.t65.model.SquadShip;
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
public class SquadShipDao {

    @Autowired
    DataSource dataSource;

    @Autowired
    ShipDao shipDao;

    @Autowired
    SquadDao squadDao;

    @Autowired
    SquadUpgradeDao squadUpgradeDao;

    public void createSquadShip(String squadId, Ship ship) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO squad_ship VALUE (?,?,?)");
            int i = 1;
            statement.setString(i++, UUID.randomUUID().toString());
            statement.setString(i++, squadId);
            statement.setString(i++, ship.getId());
            int affectedRows = statement.executeUpdate();
            log.info("Create squad ship affected {} rows", affectedRows);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Optional<SquadShip> readSquadShip(String squadShipId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM squad_ship WHERE id = ?");
            int i = 1;
            statement.setString(i++, squadShipId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                SquadShip squadShip = new SquadShip();
                squadShip.setId(rs.getString("id"));
                squadShip.setSquad(rs.getString("squad"));
                squadShip.setShip(shipDao.readShip(rs.getString("ship")).orElseThrow(() -> new RuntimeException("The ship in squad ship was not found. Squad ship id: " + squadShipId)));
                return Optional.of(squadShip);
            } else return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<SquadShip> readSquadShips(String squadId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM squad_ship WHERE squad = ?");
            int i = 1;
            statement.setString(i++, squadId);
            ResultSet rs = statement.executeQuery();

            List<SquadShip> output = new ArrayList<>();
            while (rs.next()) {
                SquadShip squadShip = new SquadShip();
                squadShip.setId(rs.getString("id"));
                squadShip.setSquad(rs.getString("squad"));
                squadShip.setShip(shipDao.readShip(rs.getString("ship")).orElseThrow(() -> new RuntimeException("The ship in squad ship was not found. Squad id: " + squadId)));
                output.add(squadShip);
            }

            return output;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public int deleteSquadShips(String squadId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE * FROM squad_ship WHERE squad = ?");
            int i = 1;
            statement.setString(i++, squadId);

            List<SquadShip> ships = readSquadShips(squadId);
            for (SquadShip s : ships) {
                squadUpgradeDao.deleteSquadUpgrades(s.getId());
            }

            int affectedRows = statement.executeUpdate();
            log.info("Delete squad ships affected {} rows", affectedRows);
            return affectedRows;

        } catch (SQLException e) {
           log.error(e.getMessage(), e);
           throw new RuntimeException(e);
        }
    }

    public int deleteSquadShip(String squadShipId) {
        try (Connection connection = dataSource.getConnection()) {
            if (readSquadShip(squadShipId).isEmpty()) {
                return 0;
            }
            PreparedStatement statement = connection.prepareStatement("DELETE FROM squad_ship WHERE id = ?");
            int i = 1;
            statement.setString(i++, squadShipId);
            int deletedUpgrades = squadUpgradeDao.deleteSquadUpgrades(squadShipId);
            int affectedRows = statement.executeUpdate();
            log.info("Delete squad ship affected {} rows in squad ship and {} rows in squad upgrade", affectedRows, deletedUpgrades);
            return affectedRows;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
