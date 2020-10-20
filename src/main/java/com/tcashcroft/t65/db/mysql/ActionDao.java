package com.tcashcroft.t65.db.mysql;

import com.tcashcroft.t65.model.Action;
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
public class ActionDao {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private ColorDao colorDao;

    public void createAction(Action action) {
        if (readAction(action.getId()).isPresent()) {
            // value already exists in DB
            return;
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO action Value (?, ?, ?, ?)");
            statement.setString(1, action.getId());
            statement.setString(2, action.getAction());
            statement.setString(3, action.getColor().getValue());
            // TODO handle linked actions
            statement.setString(4, null);
            int affectedRows = statement.executeUpdate();
        } catch (SQLException e) {
            log.warn(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public Optional<Action> readAction(String actionId) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM action WHERE id = ?");
            statement.setString(1, actionId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                log.info("Result Set: {}", rs.getFetchSize());
                String retrievedActionId = rs.getString("id");
                String actionName = rs.getString("name");
                String color = rs.getString("color");
                String linkedAction = rs.getString("linked_action");
                Action action = new Action();
                action.setId(retrievedActionId);
                action.setAction(actionName);
                action.setColor(colorDao.readColor(color).orElse(Action.Color.WHITE));
                // TODO handle linked actions
                return Optional.of(action);
            } else return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
