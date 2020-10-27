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
public class SizeDao {
    @Autowired
    private DataSource dataSource;

    public void createSize(Ship.Size size) {
        if (readSize(size.getValue()).isPresent()) {
            // value already exists in DB
            return;
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO size Value (?)");
            statement.setString(1, size.getValue());
            int affectedRows = statement.executeUpdate();
        } catch (SQLException e) {
            log.warn(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Optional<Ship.Size> readSize(String color) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM size WHERE name = ?");
            statement.setString(1, color);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                log.info("Result Set: {}", rs.getFetchSize());
                String sizeValue = rs.getString("name");
                if (sizeValue.isEmpty()) {
                    return Optional.empty();
                }
                return Optional.of(Ship.Size.valueOf(sizeValue.toUpperCase()));
            } else return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
