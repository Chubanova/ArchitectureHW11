package com.chubanova.command;

import com.chubanova.ioc.Context;
import com.chubanova.ioc.IoC;
import com.chubanova.state.MoveTo;
import com.chubanova.state.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveToCommandTest {

    @BeforeEach
    public void init() {
        Context.initGeneralScope();
    }

    @Test
    void execute() {
        // Given
        MoveToCommand c = IoC.resolve("MoveToCommand");
        IoC.<ThreadLocal<State>>resolve("GameState").set(IoC.resolve("NormalState"));
        assertNotNull(IoC.<ThreadLocal<State>>resolve("GameState").get());

        // When
        c.execute();

        // Then
        assertEquals(MoveTo.class, IoC.<ThreadLocal<State>>resolve("GameState").get().getClass());
    }
}