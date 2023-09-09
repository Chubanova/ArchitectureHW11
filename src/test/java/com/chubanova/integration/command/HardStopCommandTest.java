package com.chubanova.integration.command;

import com.chubanova.command.Command;
import com.chubanova.command.HardStopCommand;
import com.chubanova.ioc.Context;
import com.chubanova.ioc.IoC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HardStopCommandTest {

    @BeforeEach
    public void init() {
        Context.initGeneralScope();
    }

    @Test
    public void test() throws InterruptedException {
        // Given
        Command c1 = mock(Command.class);
        Command c2 = mock(Command.class);
        HardStopCommand hardStopCommand = IoC.resolve("HardStopCommand");
        Queue<Command> commands = IoC.resolve("CommandsQueue", c1, hardStopCommand, c2);

        AtomicBoolean isRunning = new AtomicBoolean(true);

        // When
        IoC.<Command>resolve("GameStartCommand", commands, isRunning).execute();

        // Then
        while (isRunning.get()) {
            TimeUnit.MILLISECONDS.sleep(100);
        }
        verify(c1, times(1)).execute();
        verifyNoInteractions(c2);
    }

}
