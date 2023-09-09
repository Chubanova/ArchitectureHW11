package com.chubanova.ioc;

import lombok.SneakyThrows;

import java.util.function.Function;

public class IoC {

    @SneakyThrows
    public static <T> T resolve(String key, Object... args) {
        ScopeDictionary<IoCDictionary<?>> scopesDictionary = ScopeDictionaryImpl.getInstance();
        Function<IoCDictionary<T>, T> resolver = handler -> handler.get(key, args);
        return scopesDictionary.getFromCurrentScope(resolver);
    }

}
