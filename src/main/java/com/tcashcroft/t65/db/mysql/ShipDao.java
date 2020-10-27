package com.tcashcroft.t65.db.mysql;

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
public class ShipDao {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private FactionDao factionDao;

    @Autowired
    private ShipTypeDao shipTypeDao;

    @Autowired
    private ActionDao actionDao;

    @Autowired
    private SizeDao sizeDao;

    public void createShip(Ship ship) {
        if (readShip(ship.getId()).isPresent()) {
            // value already exists in DB
            return;
        }
        try (Connection connection = dataSource.getConnection()) {
            log.info("Attempting to insert ship {}", ship.getName());
            log.info("{}", ship);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ship Value (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            int i = 1;
            statement.setString(i++, ship.getId());
            if (ship.getFaction() == null) {
                statement.setString(i++, null);
            } else {
                statement.setString(i++, ship.getFaction().getValue());
            }
            statement.setString(i++, ship.getName());
            statement.setString(i++, ship.getShipType().getValue());
            statement.setInt(i++, ship.getNameLimit());
            statement.setString(i++, ship.getCallSign());
            statement.setInt(i++, ship.getFrontArc());
            statement.setInt(i++, ship.getRearArc());
            statement.setInt(i++, ship.getTurretArc());
            statement.setInt(i++, ship.getAgility());
            statement.setInt(i++, ship.getHull());
            statement.setInt(i++, ship.getShield());
            statement.setInt(i++, ship.getForce());
            statement.setString(i++, ship.getAbilityText());

            if (ship.getAction1() == null) {
                statement.setString(i++, null);
            } else {
                statement.setString(i++, ship.getAction1().getId());
            }

            if (ship.getAction2() == null) {
                statement.setString(i++, null);
            } else {
                statement.setString(i++, ship.getAction2().getId());
            }

            if (ship.getAction3() == null) {
                statement.setString(i++, null);
            } else {
                statement.setString(i++, ship.getAction3().getId());
            }

            if (ship.getAction4() == null) {
                statement.setString(i++, null);
            } else {
                statement.setString(i++, ship.getAction4().getId());
            }

            statement.setInt(i++, ship.getAstromechUpgrades());
            statement.setInt(i++, ship.getCannonUpgrades());
            statement.setInt(i++, ship.getCargoUpgrades());
            statement.setInt(i++, ship.getCommandUpgrades());
            statement.setInt(i++, ship.getConfigurationUpgrades());
            statement.setInt(i++, ship.getCrewUpgrades());
            statement.setInt(i++, ship.getDeviceUpgrades());
            statement.setInt(i++, ship.getForceUpgrades());
            statement.setInt(i++, ship.getGunnerUpgrades());
            statement.setInt(i++, ship.getHardpointUpgrades());
            statement.setInt(i++, ship.getHyperdriveUpgrades());
            statement.setInt(i++, ship.getIllicitUpgrades());
            statement.setInt(i++, ship.getModificationUpgrades());
            statement.setInt(i++, ship.getMissileUpgrades());
            statement.setInt(i++, ship.getSensorUpgrades());
            statement.setInt(i++, ship.getTacticalRelayUpgrades());
            statement.setInt(i++, ship.getTalentUpgrades());
            statement.setInt(i++, ship.getTeamUpgrades());
            statement.setInt(i++, ship.getTechUpgrades());
            statement.setInt(i++, ship.getTitleUpgrades());
            statement.setInt(i++, ship.getTorpedoUpgrades());
            statement.setInt(i++, ship.getTurretUpgrades());
            statement.setInt(i++, ship.getPointsCost());
            statement.setBoolean(i++, ship.isHyperspaceLegal());
            statement.setBoolean(i++, ship.isExtendedLegal());
            statement.setString(i++, ship.getDialCode());
            statement.setString(i++, ship.getSize().getValue());
            statement.setInt(i++, ship.getInitiative());

            int affectedRows = statement.executeUpdate();
        } catch (SQLException e) {
            log.warn(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Optional<Ship> readShip(String shipId) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ship WHERE id = ?");
            statement.setString(1, shipId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Ship ship = new Ship();
                ship.setId(rs.getString("id"));
                ship.setFaction(factionDao.readFaction(rs.getString("faction")).orElseThrow(() -> new RuntimeException("Faction was empty")));
                ship.setName(rs.getString("name"));
                ship.setShipType(shipTypeDao.readShipType(rs.getString("ship_type")).orElseThrow(() -> new RuntimeException("Ship Type was empty")));
                ship.setNameLimit(rs.getInt("name_limit"));
                ship.setCallSign(rs.getString("call_sign"));
                ship.setFrontArc(rs.getInt("front_arc"));
                ship.setRearArc(rs.getInt("rear_arc"));
                ship.setTurretArc(rs.getInt("turret_arc"));
                ship.setAgility(rs.getInt("agility"));
                ship.setHull(rs.getInt("hull"));
                ship.setShield(rs.getInt("shield"));
                ship.setForce(rs.getInt("force"));
                ship.setAbilityText(rs.getString("ability_text"));
                ship.setAction1(actionDao.readAction(rs.getString("action_1")).orElse(null));
                ship.setAction2(actionDao.readAction(rs.getString("action_2")).orElse(null));
                ship.setAction3(actionDao.readAction(rs.getString("action_3")).orElse(null));
                ship.setAction4(actionDao.readAction(rs.getString("action_4")).orElse(null));
                ship.setAstromechUpgrades(rs.getInt("astromech_upgrades"));
                ship.setCannonUpgrades(rs.getInt("cannon_upgrades"));
                ship.setCargoUpgrades(rs.getInt("cargo_upgrades"));
                ship.setConfigurationUpgrades(rs.getInt("configuration_upgrades"));
                ship.setCommandUpgrades(rs.getInt("command_upgrades"));
                ship.setCrewUpgrades(rs.getInt("crew_upgrades"));
                ship.setDeviceUpgrades(rs.getInt("device_upgrades"));
                ship.setForceUpgrades(rs.getInt("force_upgrades"));
                ship.setGunnerUpgrades(rs.getInt("gunner_upgrades"));
                ship.setHardpointUpgrades(rs.getInt("hardpoint_upgrades"));
                ship.setHyperdriveUpgrades(rs.getInt("hyperdrive_upgrades"));
                ship.setIllicitUpgrades(rs.getInt("illicit_upgrades"));
                ship.setModificationUpgrades(rs.getInt("modification_upgrades"));
                ship.setMissileUpgrades(rs.getInt("missile_upgrades"));
                ship.setSensorUpgrades(rs.getInt("sensor_upgrades"));
                ship.setTacticalRelayUpgrades(rs.getInt("tactical_relay_upgrades"));
                ship.setTalentUpgrades(rs.getInt("talent_upgrades"));
                ship.setTeamUpgrades(rs.getInt("team_upgrades"));
                ship.setTechUpgrades(rs.getInt("tech_upgrades"));
                ship.setTitleUpgrades(rs.getInt("title_upgrades"));
                ship.setTorpedoUpgrades(rs.getInt("torpedo_upgrades"));
                ship.setTurretUpgrades(rs.getInt("turret_upgrades"));
                ship.setPointsCost(rs.getInt("points_cost"));
                ship.setHyperspaceLegal(rs.getBoolean("hyperspace_legal"));
                ship.setExtendedLegal(rs.getBoolean("extended_legal"));
                ship.setDialCode(rs.getString("dial_code"));
                ship.setSize(sizeDao.readSize(rs.getString("size")).orElseThrow(() -> new RuntimeException("Size was missing")));
                ship.setInitiative(rs.getInt("initiative"));

                return Optional.of(ship);
            } else return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
