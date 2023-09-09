package com.chubanova.command;

import com.chubanova.ioc.Context;
import com.chubanova.ioc.IoC;
import com.chubanova.state.Normal;
import com.chubanova.state.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RunCommandTest {

    @BeforeEach
    public void init() {
        Context.initGeneralScope();
    }

    @Test
    void execute() {
        // Given
        RunCommand c = IoC.resolve("RunCommand");
        IoC.<ThreadLocal<State>>resolve("GameState").set(IoC.resolve("MoveToState"));
        assertNotNull(IoC.<ThreadLocal<State>>resolve("GameState").get());

        // When
        c.execute();

        // Then
        assertEquals(Normal.class, IoC.<ThreadLocal<State>>resolve("GameState").get().getClass());
    }
}