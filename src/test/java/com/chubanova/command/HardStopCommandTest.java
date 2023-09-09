package com.chubanova.command;

import com.chubanova.ioc.Context;
import com.chubanova.ioc.IoC;
import com.chubanova.state.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class HardStopCommandTest {

    @BeforeEach
    public void init() {
        Context.initGeneralScope();
    }

    @Test
    void execute() {
        // Given
        HardStopCommand c = IoC.resolve("HardStopCommand");
        IoC.<ThreadLocal<State>>resolve("GameState").set(IoC.resolve("NormalState"));
        assertNotNull(IoC.<ThreadLocal<State>>resolve("GameState").get());

        // When
        c.execute();

        // Then
        assertNull(IoC.<ThreadLocal<State>>resolve("GameState").get());
    }
}