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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    public void createFlipSideUpgrades(List<Upgrade> upgrades) {
        // TODO handle inserting flip-side ID upgrades
        List<Upgrade> upgradesSansFlipSideId = upgrades.stream().map(u -> {
            Upgrade upgrade = new Upgrade();
            upgrade.setId(u.getId());
            upgrade.setFaction(u.getFaction());
            upgrade.setName(u.getName());
            upgrade.setNameLimit(u.getNameLimit());
            upgrade.setShipType(u.getShipType());
            upgrade.setUpgradeType(u.getUpgradeType());
            upgrade.setUpgradeText(u.getUpgradeText());
            upgrade.setAction1(u.getAction1());
            upgrade.setAction2(u.getAction2());
            upgrade.setAction3(u.getAction3());
            upgrade.setAction4(u.getAction4());
            upgrade.setPointsCost(u.getPointsCost());
            upgrade.setHyperspaceLegal(u.isHyperspaceLegal());
            upgrade.setExtendedLegal(u.isExtendedLegal());
            return upgrade;
        }).collect(Collectors.toList());

        for (Upgrade u : upgradesSansFlipSideId) {
            createUpgrade(u);
        }

        try (Connection connection = dataSource.getConnection()) {
            boolean autoCommitSetting = connection.getAutoCommit();
            connection.setAutoCommit(false);
            for (Upgrade u : upgrades) {
                PreparedStatement statement = connection.prepareStatement("UPDATE upgrade SET flip_side = ? WHERE id = ?");
                int i = 1;
                statement.setString(i++, u.getFlipSideId());
                statement.setString(i++, u.getId());
                int rowsAffected = statement.executeUpdate();
                log.info("Updated {} rows setting upgrade flip side id", rowsAffected);
            }
            connection.commit();
            connection.setAutoCommit(autoCommitSetting);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }


    public void createUpgrade(Upgrade upgrade) {
        if (readUpgrade(upgrade.getId()).isPresent()) {
            // value already exists in DB
            return;
        }
        try (Connection connection = dataSource.getConnection()) {
            log.info("Attempting to insert upgrade {}", upgrade.getName());
            log.info("{}", upgrade);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO upgrade Value (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            int i = 1;
            statement.setString(i++, upgrade.getId());

            if (upgrade.getFaction() == null) {
                statement.setString(i++, null);
            } else {
                statement.setString(i++, upgrade.getFaction().getValue());
            }

            statement.setString(i++, upgrade.getName());
            statement.setInt(i++, upgrade.getNameLimit());

            if (upgrade.getShipType() == null) {
                statement.setString(i++, null);
            } else {
                statement.setString(i++, upgrade.getShipType().getValue());
            }

            statement.setString(i++, upgrade.getUpgradeType().getValue());
            statement.setString(i++, upgrade.getUpgradeText());

            if (upgrade.getAction1() == null) {
                statement.setString(i++, null);
            } else {
                statement.setString(i++, upgrade.getAction1().getId());
            }

            if (upgrade.getAction2() == null) {
                statement.setString(i++, null);
            } else {
                statement.setString(i++, upgrade.getAction2().getId());
            }

            if (upgrade.getAction3() == null) {
                statement.setString(i++, null);
            } else {
                statement.setString(i++, upgrade.getAction3().getId());
            }

            if (upgrade.getAction4() == null) {
                statement.setString(i++, null);
            } else {
                statement.setString(i++, upgrade.getAction4().getId());
            }

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
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM upgrade WHERE id = ?");

            statement.setString(1, upgradeId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                log.info("Result Set: {}", rs.getFetchSize());

                Upgrade upgrade = parseUpgrade(rs, recurse);

                return Optional.of(upgrade);
            } else return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<Upgrade> readAllUpgrades() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM upgrade");
            ResultSet rs = statement.executeQuery();
            List<Upgrade> upgrades = new ArrayList<>();
            while (rs.next()) {
                Upgrade upgrade = parseUpgrade(rs, 1);
                upgrades.add(upgrade);
            }
            return upgrades;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private Upgrade parseUpgrade(ResultSet rs, int recurse) throws SQLException {
        Upgrade upgrade = new Upgrade();

        upgrade.setId(rs.getString("id"));
        upgrade.setFaction(factionDao.readFaction(rs.getString("faction")).orElse(null));
        upgrade.setName(rs.getString("name"));
        upgrade.setNameLimit(rs.getInt("name_limit"));
        upgrade.setShipType(shipTypeDao.readShipType(rs.getString("ship_type")).orElse(null));
        upgrade.setUpgradeType(upgradeTypeDao.readUpgradeType(rs.getString("upgrade_type")).orElseThrow(() -> new RuntimeException("Upgrade type was blank")));
        upgrade.setUpgradeText(rs.getString("upgrade_text"));
        upgrade.setAction1(actionDao.readAction(rs.getString("action_1")).orElse(null));
        upgrade.setAction2(actionDao.readAction(rs.getString("action_2")).orElse(null));
        upgrade.setAction3(actionDao.readAction(rs.getString("action_3")).orElse(null));
        upgrade.setAction4(actionDao.readAction(rs.getString("action_4")).orElse(null));
        upgrade.setFlipSideId(rs.getString("flip_side"));
//        if (recurse > 0) {
//            recurse--;
//            upgrade.setFlipSideId(readUpgrade(rs.getString("flip_side"), recurse).get().getFlipSideId());
//        }
        upgrade.setPointsCost(rs.getInt("points_cost"));
        upgrade.setHyperspaceLegal(rs.getBoolean("hyperspace_legal"));
        upgrade.setExtendedLegal(rs.getBoolean("extended_legal"));

        return upgrade;
    }
}
