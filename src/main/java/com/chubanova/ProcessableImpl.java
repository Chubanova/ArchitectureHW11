package com.chubanova;

import java.util.Map;

public class ProcessableImpl implements Processable{
    Map<String,Object> context;

    public ProcessableImpl(Map<String, Object> context) {
        this.context = context;
    }

    @Override
    public boolean canContinue() {
        return (boolean) context.get("canContinue");
    }

    @Override
    public void process() {
        ((Runnable) context.get("process")).run();
    }
}
