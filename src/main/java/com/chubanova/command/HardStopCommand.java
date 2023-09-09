package com.chubanova.command;

import com.chubanova.ioc.IoC;
import com.chubanova.state.State;

public class HardStopCommand implements Command {

    @Override
    public void execute() {
        IoC.<ThreadLocal<State>>resolve("GameState").remove();
    }

}
