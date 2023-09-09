package com.chubanova.ioc.command;

import com.chubanova.command.Command;
import com.chubanova.ioc.ScopeDictionaryImpl;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class RegisterCommand implements Command {

    private final Object[] args;

    @Override
    public void execute() {
        ScopeDictionaryImpl.getInstance()
            .executeInCurrentScope(dictionary ->
                    dictionary.add(String.valueOf(args[0]), (Function<Object[], Object>) args[1]));

    }

}
