package com.tcashcroft.t65.db.mysql;

import com.tcashcroft.t65.model.Squad;
import com.tcashcroft.t65.model.SquadShip;
import com.tcashcroft.t65.model.SquadUpgrade;
import com.tcashcroft.t65.model.Utils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
@Slf4j
@Setter
public class SquadDao {

    @Autowired
    DataSource dataSource;

    @Autowired
    FactionDao factionDao;

    @Autowired
    SquadShipDao squadShipDao;

    @Autowired
    SquadUpgradeDao squadUpgradeDao;

    public void createSquad(String username, String squadName, Utils.Faction faction) {
        if (readSquad(username, squadName).isPresent()) {
            return;
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO squad Value (?,?,?,?,?)");
            int i = 1;
            statement.setString(i++, UUID.randomUUID().toString());
            statement.setString(i++, username);
            statement.setString(i++, squadName);
            statement.setString(i++, faction.getValue());
            statement.setInt(i++, 0);
            int affectedRows = statement.executeUpdate();
            log.info("Create squad affected {} rows", affectedRows);
        } catch (SQLException e) {
            log.warn(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Optional<Squad> readSquad(String squadId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM squad WHERE id = ?");
            int i = 1;
            statement.setString(i++, squadId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Squad squad = new Squad();

                squad.setId(rs.getString("id"));
                squad.setFaction(factionDao.readFaction(rs.getString("faction")).orElseThrow());
                squad.setTotalPoints(rs.getInt("total_points"));
                squad.setUsername(rs.getString("username"));
                squad.setName(rs.getString("squad_name"));

                List<SquadShip> squadShips = squadShipDao.readSquadShips(rs.getString("id"));
                Map<String, List<SquadUpgrade>> shipIdToUpgrades = new HashMap<>();

                for (SquadShip ss : squadShips) {
                    List<SquadUpgrade> squadUpgrades = squadUpgradeDao.readSquadUpgrades(ss.getId());
                    shipIdToUpgrades.put(ss.getId(), squadUpgrades);
                }
                squad.setShips(squadShips);
                squad.setShipIdToUpgrade(shipIdToUpgrades);

                return Optional.of(squad);
            } else return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Optional<Squad> readSquad(String username, String squadName) {
        try (Connection connection = dataSource.getConnection()) {
            return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public int deleteSquad(String squadId) {
        try (Connection connection = dataSource.getConnection()) {
            if (readSquad(squadId).isEmpty()) {
                return 0;
            }
            PreparedStatement statement = connection.prepareStatement("DELETE * FROM squad WHERE id = ?");
            int i = 1;
            statement.setString(i++, squadId);
            squadShipDao.deleteSquadShips(squadId);
            int affectedRows = statement.executeUpdate();
            log.info("Delete squad affected {} rows", affectedRows);
            return affectedRows;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
