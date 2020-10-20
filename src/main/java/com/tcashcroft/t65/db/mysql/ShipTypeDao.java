package com.tcashcroft.t65.db.mysql;

import com.tcashcroft.t65.model.Action;
import com.tcashcroft.t65.model.Ship;
import lombok.Data;
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
@Data
public class ShipTypeDao {
    @Autowired
    private DataSource dataSource;

    public void createShipType(Ship.ShipType shipType) {
        if (readShipType(shipType.getValue()).isPresent()) {
            // value already exists in DB
            return;
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ship_type Value (?)");
            statement.setString(1, shipType.getValue());
            int affectedRows = statement.executeUpdate();
        } catch (SQLException e) {
            log.warn(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Optional<Ship.ShipType> readShipType(String shipType) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM ship_type WHERE name = ?");
            statement.setString(1, shipType);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                log.info("Result Set: {}", rs.getFetchSize());
                String shipTypeValue = rs.getString("name");
                if (shipTypeValue.isEmpty()) {
                    return Optional.empty();
                }
                return Optional.of(Ship.ShipType.valueOf(shipTypeValue));
            } else return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
