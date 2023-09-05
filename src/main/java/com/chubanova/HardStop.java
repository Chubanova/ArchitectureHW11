package com.chubanova;

import java.util.Map;

public class HardStop implements Command {
    Map<String, Object> context;

    public HardStop(Map<String, Object> context) {
        this.context = context;
    }

    @Override
    public void execute() {
       context.put("canContinue", false);
    }
}
