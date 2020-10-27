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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Setter
public class SquadShipsDao {

    @Autowired
    DataSource dataSource;

    @Autowired
    ShipDao shipDao;

    @Autowired
    SquadDao squadDao;

    public void createSquadShip(String squadName, Ship ship) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO squad_ship VALUE (?,?,?)");
            int i = 1;
            statement.setString(i++, null);
            statement.setString(i++, squadName);
            statement.setString(i++, ship.getId());
            int affectedRows = statement.executeUpdate();
            log.info("Create squad ship affected {} rows", affectedRows);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Optional<SquadShip> readSquadShip(String squadShipId) {
        // TODO I don't think I'll ever use this, but stubbed it out just in case.
        return Optional.empty();
    }

    public List<SquadShip> readSquadShips(String squadName) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM squad_ship WHERE squad = ?");
            int i = 1;
            statement.setString(i++, squadName);
            ResultSet rs = statement.executeQuery();

            List<SquadShip> output = new ArrayList<>();
            while (rs.next()) {
               SquadShip squadShip = new SquadShip();
               squadShip.setId(rs.getString("id"));
               squadShip.setSquad(rs.getString("squad"));
               squadShip.setShip(shipDao.readShip(rs.getString("ship")).orElseThrow(() -> new RuntimeException("The ship in squad ship was not found. Squad name: " + squadName)));
               output.add(squadShip);
            }

            return output;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Data
    public static class SquadShip {
        private String id;
        private String squad;
        private Ship ship;
    }
}
