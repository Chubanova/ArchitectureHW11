package com.chubanova.command;

import com.chubanova.ioc.IoC;
import com.chubanova.state.State;
import lombok.Getter;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
public class GameStartCommand implements Command {

    private final Queue<Command> commands;
    private final AtomicBoolean isRunning;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public GameStartCommand(Queue<Command> commands, AtomicBoolean isRunning) {
        this.commands = commands;
        this.isRunning = isRunning;
    }

    @Override
    public void execute() {
        executor.execute(() -> {
            if (isRunning.get()) {
                IoC.<Command>resolve("RunCommand").execute();

                ThreadLocal<State> state = IoC.resolve("GameState");

                while (state.get() != null) {
                    if (state.get().handle(commands) == null) {
                        state.set(null);
                    }
                }
            }

            isRunning.set(false);
        });
    }
}
