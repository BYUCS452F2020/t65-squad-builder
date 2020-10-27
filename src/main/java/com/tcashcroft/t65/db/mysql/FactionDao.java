package com.tcashcroft.t65.db.mysql;

import com.tcashcroft.t65.model.Utils;
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
public class FactionDao {

    @Autowired
    private DataSource dataSource;

    public void createFaction(Utils.Faction faction) {
        if (readFaction(faction.getValue()).isPresent()) {
            // value already exists in DB
            return;
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO faction Value (?)");
            log.info("Faction: {}", faction.getValue());
            statement.setString(1, faction.getValue());
            int affectedRows = statement.executeUpdate();
        } catch (SQLException e) {
            log.warn(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Optional<Utils.Faction> readFaction(String faction) {
       try (Connection connection = dataSource.getConnection()) {
           PreparedStatement statement = connection.prepareStatement("SELECT name FROM faction WHERE name = ?");
           statement.setString(1, faction);
           ResultSet rs = statement.executeQuery();
           if (rs.next()) {
                String factionValue = rs.getString("name");
                if (factionValue.isEmpty()) {
                    return Optional.empty();
                }
                return Optional.of(Utils.Faction.valueOf(factionValue.toUpperCase()));
           } else return Optional.empty();
       } catch (SQLException e) {
           log.error(e.getMessage(), e);
           throw new RuntimeException(e);
       }
    }
}
