package com.chubanova;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;

public class InitCommand implements Command{

    Map<String, Object> context;

    public InitCommand(Map<String, Object> context) {
        this.context = context;
    }

    @Override
    public void execute() {
        LinkedBlockingQueue<Command> c = new LinkedBlockingQueue<>();
        context.put("canContinue",true);
        context.put("queue", c);
        Runnable runnable = () -> Optional.ofNullable(c.poll()).ifPresent(Command::execute);
        context.put("process", runnable);

    }
}
