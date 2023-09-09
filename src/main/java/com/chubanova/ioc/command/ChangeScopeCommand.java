package com.chubanova.ioc.command;

import com.chubanova.command.Command;
import com.chubanova.ioc.ScopeDictionary;
import com.chubanova.ioc.ScopeDictionaryImpl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChangeScopeCommand implements Command {

    private final Object[] args;

    @Override
    public void execute() {
        ScopeDictionary scopesDictionary = ScopeDictionaryImpl.getInstance();
        scopesDictionary.changeScope(String.valueOf(args[0]));
    }

}
