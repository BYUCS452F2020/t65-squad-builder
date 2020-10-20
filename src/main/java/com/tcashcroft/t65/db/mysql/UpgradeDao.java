package com.tcashcroft.t65.db.mysql;

import com.tcashcroft.t65.model.Upgrade;
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
public class UpgradeDao {

    @Autowired
    private DataSource dataSource;

    @Autowired
    FactionDao factionDao;

    @Autowired
    ShipTypeDao shipTypeDao;

    @Autowired
    UpgradeTypeDao upgradeTypeDao;

    @Autowired
    ActionDao actionDao;

    public void createUpgrade(Upgrade upgrade) {
        if (readUpgrade(upgrade.getId()).isPresent()) {
            // value already exists in DB
            return;
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO upgrade Value (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            int i = 1;
            statement.setString(i++, upgrade.getId());
            statement.setString(i++, upgrade.getFaction().getValue());
            statement.setString(i++, upgrade.getName());
            statement.setInt(i++, upgrade.getNameLimit());
            statement.setString(i++, upgrade.getShipType().getValue());
            statement.setString(i++, upgrade.getUpgradeType().getValue());
            statement.setString(i++, upgrade.getUpgradeText());
            statement.setString(i++, upgrade.getAction1().getId());
            statement.setString(i++, upgrade.getAction2().getId());
            statement.setString(i++, upgrade.getAction3().getId());
            statement.setString(i++, upgrade.getAction4().getId());
            statement.setString(i++, upgrade.getFlipSideId());
            statement.setInt(i++, upgrade.getPointsCost());
            statement.setBoolean(i++, upgrade.isHyperspaceLegal());
            statement.setBoolean(i++, upgrade.isExtendedLegal());

            int affectedRows = statement.executeUpdate();
            log.info("Create upgrade affected {} rows", affectedRows);
        } catch (SQLException e) {
            log.warn(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Optional<Upgrade> readUpgrade(String upgradeId) {
        return readUpgrade(upgradeId, 1);
    }

    private Optional<Upgrade> readUpgrade(String upgradeId, int recurse) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM upgrade WHERE upgrade_id = ?");

            statement.setString(1, upgradeId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                log.info("Result Set: {}", rs.getFetchSize());
                Upgrade upgrade = new Upgrade();

                upgrade.setId(rs.getString("id"));
                upgrade.setFaction(factionDao.readFaction(rs.getString("faction")).orElse(null));
                upgrade.setName(rs.getString("name"));
                upgrade.setNameLimit(rs.getInt("name_limit"));
                upgrade.setShipType(shipTypeDao.readShipType(rs.getString("ship_type")).orElse(null));
                upgrade.setUpgradeType(upgradeTypeDao.readUpgradeType(rs.getString("upgrade_type")).orElseThrow(() -> new RuntimeException("Upgrade type was blank")));
                upgrade.setAction1(actionDao.readAction(rs.getString("action_1")).orElse(null));
                upgrade.setAction2(actionDao.readAction(rs.getString("action_2")).orElse(null));
                upgrade.setAction3(actionDao.readAction(rs.getString("action_3")).orElse(null));
                upgrade.setAction4(actionDao.readAction(rs.getString("action_4")).orElse(null));
                if (recurse > 0) {
                    recurse--;
                    upgrade.setFlipSideId(readUpgrade(rs.getString("flip_side"), recurse).get().getFlipSideId());
                }
                upgrade.setPointsCost(rs.getInt("points_cost"));
                upgrade.setHyperspaceLegal(rs.getBoolean("hyperspace_legal"));
                upgrade.setExtendedLegal(rs.getBoolean("extended_legal"));

                return Optional.of(upgrade);
            } else return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
