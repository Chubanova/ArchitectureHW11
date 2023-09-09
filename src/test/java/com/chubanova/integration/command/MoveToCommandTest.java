package com.chubanova.integration.command;

import com.chubanova.command.Command;
import com.chubanova.command.MoveToCommand;
import com.chubanova.ioc.Context;
import com.chubanova.ioc.IoC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MoveToCommandTest {

    @BeforeEach
    public void init() {
        Context.initGeneralScope();
    }

    @Test
    public void test() throws InterruptedException {
        // Given
        Command c1 = mock(Command.class);
        Command c2 = mock(Command.class);
        MoveToCommand moveToCommand = IoC.resolve("MoveToCommand");
        Queue<Command> commands = IoC.resolve("CommandsQueue", c1, moveToCommand, c2);

        AtomicBoolean isRunning = new AtomicBoolean(true);

        Map<String, Queue<Command>> stashedCommands = IoC.resolve("StashedCommandsStorage");
        assertEquals(0, stashedCommands.size());

        // When
        IoC.<Command>resolve("GameStartCommand", commands, isRunning).execute();

        // Then
        while (isRunning.get()) {
            TimeUnit.MILLISECONDS.sleep(100);
        }
        verify(c1, times(1)).execute();
        verifyNoInteractions(c2);
        assertEquals(1, stashedCommands.size());
        assertEquals(1, stashedCommands.get(stashedCommands.keySet().toArray()[0]).size());
        assertEquals(c2, stashedCommands.get(stashedCommands.keySet().toArray()[0]).peek());
    }

}
