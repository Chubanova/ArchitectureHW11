package com.chubanova.state;

import com.chubanova.command.Command;

import java.util.Queue;

public class Normal implements State {

    @Override
    public State handle(Queue<Command> commands) {
        try {
            Command nextCommand;
            if ((nextCommand = commands.poll()) != null)
                nextCommand.execute();
        } catch (Exception exception) {
            System.err.println("Unexpected exception " + exception.getMessage());
        }
        return this;
    }

}
