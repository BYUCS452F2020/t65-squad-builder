package com.tcashcroft.t65.db.mysql;

import com.tcashcroft.t65.harvester.SqlTransformer;
import com.tcashcroft.t65.model.Action;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class DatabaseLoader {

    @Autowired
    private ColorDao colorDao;

    @Autowired
    private SqlTransformer sqlTransformer;

    public void loadColors() {
        List<Action.Color> colors = sqlTransformer.convertColors();
        for (Action.Color c : colors) {
            colorDao.createColor(c);
        }
    }

}
