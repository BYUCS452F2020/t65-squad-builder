package com.tcashcroft.t65.db.mysql;

import com.tcashcroft.t65.model.Action;
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
public class UpgradeTypeDao {

    @Autowired
    private DataSource dataSource;

    public void createUpgradeType(Upgrade.UpgradeType upgradeType) {
        if (readUpgradeType(upgradeType.getValue()).isPresent()) {
            // value already exists in DB
            return;
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO upgrade_type Value (?)");
            statement.setString(1, upgradeType.getValue());
            int affectedRows = statement.executeUpdate();
        } catch (SQLException e) {
            log.warn(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Optional<Upgrade.UpgradeType> readUpgradeType(String upgradeType) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM upgrade_type WHERE name = ?");
            statement.setString(1, upgradeType);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                log.info("Result Set: {}", rs.getFetchSize());
                String upgradeTypeValue = rs.getString("name");
                if (upgradeTypeValue.isEmpty()) {
                    return Optional.empty();
                }
                return Optional.of(Upgrade.UpgradeType.valueOf(upgradeTypeValue));
            } else return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
