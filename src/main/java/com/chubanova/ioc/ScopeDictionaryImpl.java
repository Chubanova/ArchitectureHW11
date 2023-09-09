package com.chubanova.ioc;

import com.chubanova.command.Command;
import lombok.SneakyThrows;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;

public class ScopeDictionaryImpl implements ScopeDictionary<IoCDictionary<?>> {

    private static final String SCOPE_NOT_EXIST = "Область видимости не существует";
    private static ScopeDictionaryImpl scopesDictionary;
    private final Map<String, IoCDictionary> mapHandler;
    private final Map<String, ExecutorService> mapThread;
    private static String currentScopeName;

    private ScopeDictionaryImpl() {
        mapHandler = new ConcurrentHashMap<>();
        mapThread = new ConcurrentHashMap<>();
    }

    public static ScopeDictionaryImpl getInstance() {
        if (scopesDictionary == null) {
            synchronized (ScopeDictionaryImpl.class) {
                if (scopesDictionary == null) {
                    scopesDictionary = new ScopeDictionaryImpl();
                }
            }
        }
        return scopesDictionary;
    }

    @SneakyThrows
    @Override
    public <U> U getFromCurrentScope(Function<IoCDictionary<U>, U> extractor) {
        return mapThread.get(currentScopeName).submit(() -> {
            try {
                return (U) extractor.apply(mapHandler.get(currentScopeName));
            } catch (Exception e) {
                System.err.println("Exception " + e.getMessage());
                return null;
            }
        }).get();
    }

    @SneakyThrows
    @Override
    public <U> void executeInCurrentScope(Consumer<IoCDictionary<U>> action) {
        mapThread.get(currentScopeName).submit(() -> {
            try {
                action.accept(mapHandler.get(currentScopeName));
            } catch (Exception e) {
                System.err.println("Exception " + e.getMessage());
            }
        }).get();
    }

    @Override
    public synchronized void createScope(String scopeName) {
        if (mapHandler.containsKey(scopeName)) {
            changeScope(scopeName);
            return;
        }

        IoCDictionary<?> ioCDictionary = new IoCDictionaryImpl<>();

        mapHandler.put(scopeName, ioCDictionary);
        mapThread.put(scopeName, Executors.newSingleThreadExecutor());

        currentScopeName = scopeName;

        initializeCommandHandlerInScope(ioCDictionary);
    }

    @Override
    public void changeScope(String toScopeName) {
        if (!mapHandler.containsKey(toScopeName)) {
            throw new RuntimeException(SCOPE_NOT_EXIST);
        }

        currentScopeName = toScopeName;
    }

    @SneakyThrows
    private void initializeCommandHandlerInScope(IoCDictionary<?> ioCDictionary) {
        mapThread.get(currentScopeName).submit(() -> {
            try {
                Initializer initializerCommandHandler = new InitializerCommandHandler();
                initializerCommandHandler.initialize(ioCDictionary);
            } catch (Exception e) {
                System.err.println("Exception " + e.getMessage());
            }
        }).get();
    }

}
