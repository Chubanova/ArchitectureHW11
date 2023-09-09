package com.chubanova.ioc;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class IoCDictionaryImpl<T> implements IoCDictionary<T> {

    private ThreadLocal<Map<String, Function<Object[], T>>> map = new ThreadLocal<>();

    private void init() {
        if (map.get() == null) {
            map.set(new HashMap<>());
        }
    }

    @Override
    public void add(String commandName, Function<Object[], T> returnObject) {
        init();
        Map<String, Function<Object[], T>> localMap = map.get();
        localMap.put(commandName, returnObject);
        map.set(localMap);
    }

    @Override
    public T get(String commandName, Object... args) {
        init();
        var command = map.get().get(commandName);
        if (command == null) {
            return null;
        } else {
            return map.get().get(commandName).apply(args);
        }
    }

}
