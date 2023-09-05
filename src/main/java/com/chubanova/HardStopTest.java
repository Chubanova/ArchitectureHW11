package com.chubanova;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class HardStopTest {
    @Test
    public void test() throws InterruptedException {
        Map<String, Object> context = new HashMap<>();
        new InitCommand(context).execute();

        Processor processor = new Processor(new ProcessableImpl(context));

        LinkedBlockingQueue<Command> queue = (LinkedBlockingQueue<Command>) context.get("queue");

        queue.add(new PrintCommand());
        queue.add(new HardStop(context));
        queue.add(new PrintCommand());

        processor.wait1();

        Assert.assertEquals(1, queue.size());
    }

    @Test
    public void test_softStop() throws InterruptedException {
        Map<String, Object> context = new HashMap<>();
        new InitCommand(context).execute();

        Processor processor = new Processor(new ProcessableImpl(context));

        LinkedBlockingQueue<Command> queue = (LinkedBlockingQueue<Command>) context.get("queue");

        queue.add(new PrintCommand());
        queue.add(new SoftStop(context));
        queue.add(new PrintCommand());

        processor.wait1();

        Assert.assertEquals(0, queue.size());
    }
}
