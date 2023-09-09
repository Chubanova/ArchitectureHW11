package com.chubanova.ioc;

import java.util.function.Consumer;
import java.util.function.Function;

public interface ScopeDictionary<T> {

     <U> U getFromCurrentScope(Function<IoCDictionary<U>, U> extractor);

     <U> void executeInCurrentScope(Consumer<IoCDictionary<U>> action);

     void createScope(String scopeName);
     void changeScope(String toScopeName);

}
