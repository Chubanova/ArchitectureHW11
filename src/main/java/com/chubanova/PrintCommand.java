package com.chubanova;

public class PrintCommand implements Command{
    @Override
    public void execute() {
        System.out.println("Ля-ля-ля");
    }
}
