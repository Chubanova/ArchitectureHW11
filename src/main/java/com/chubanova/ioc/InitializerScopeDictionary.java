package com.chubanova.ioc;

public class InitializerScopeDictionary implements Initializer<ScopeDictionary<IoCDictionary<?>>> {

    @Override
    public void initialize(ScopeDictionary<IoCDictionary<?>> scopesDictionary) {
        scopesDictionary.createScope("default");
    }

}
