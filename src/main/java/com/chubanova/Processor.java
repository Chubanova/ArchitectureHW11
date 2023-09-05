package com.chubanova;

public class Processor {
    Thread thread;
    Processable processable;

    public Processor(Processable context){
        processable = context;
        thread = new Thread(this::evaluation);
        thread.start();
    }

    public void wait1() throws InterruptedException {
        thread.join();
    }
    private void evaluation(){
        while (processable.canContinue()){
            processable.process();
        }
    }
}
