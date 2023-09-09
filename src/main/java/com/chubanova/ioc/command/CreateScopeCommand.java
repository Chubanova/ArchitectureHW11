package com.chubanova.ioc.command;

import com.chubanova.command.Command;
import com.chubanova.ioc.ScopeDictionary;
import com.chubanova.ioc.ScopeDictionaryImpl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateScopeCommand implements Command {

    private final Object[] args;

    @Override
    public void execute() {
        ScopeDictionary scopesDictionary = ScopeDictionaryImpl.getInstance();
        scopesDictionary.createScope(String.valueOf(args[0]));
    }

}
