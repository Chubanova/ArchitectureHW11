package com.chubanova.command;

import com.chubanova.ioc.IoC;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@RequiredArgsConstructor
public class SaveCommand implements Command {

    private final Queue<Command> commands;

    @Override
    public void execute() {
        IoC.<Map<String, Queue<Command>>>resolve("StashedCommandsStorage")
                .computeIfAbsent(Thread.currentThread().getName(), t -> new LinkedList<>())
                .addAll(commands);
    }

}
