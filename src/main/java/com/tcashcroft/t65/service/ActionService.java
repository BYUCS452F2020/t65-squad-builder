package com.tcashcroft.t65.service;

import com.tcashcroft.t65.db.mysql.ActionDao;
import com.tcashcroft.t65.model.Action;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Setter
public class ActionService {

    @Autowired
    private ActionDao actionDao;

    public void createAction(Action action) {
        actionDao.createAction(action);
    }

    public Action getAction(String actionId) {
        return actionDao.readAction(actionId).orElseThrow(() -> new RuntimeException());
    }
}
