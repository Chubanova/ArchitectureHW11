package com.chubanova.state;


import com.chubanova.command.Command;
import com.chubanova.ioc.IoC;

import java.util.Queue;

public class MoveTo implements State {

    @Override
    public State handle(Queue<Command> commands) {
        IoC.<Command>resolve(
                "SaveCommand",
                commands
        ).execute();
        return null;
    }

}
