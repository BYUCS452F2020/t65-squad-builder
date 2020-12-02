package com.tcashcroft.t65.cli.command;

import com.tcashcroft.t65.cli.client.SquadClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;

@ShellComponent
@Data
public class SquadCommands {

    @Autowired
    private SquadClient squadClient;


}
