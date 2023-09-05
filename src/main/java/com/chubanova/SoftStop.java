package com.chubanova;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class SoftStop implements Command{

    Map<String, Object> context;

    public SoftStop(Map<String, Object> context) {
        this.context = context;
    }

    @Override
    public void execute() {
        Runnable previousProcess = (Runnable) context.get("process");
        Runnable runnable = () -> {
            previousProcess.run();
            LinkedBlockingQueue<Command> c= (LinkedBlockingQueue<Command>) context.get("queue");
            if (c.size() == 0) {
                context.put("canContinue", false);
            }
        };
        context.put("process", runnable);
    }
}
