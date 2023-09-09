package com.chubanova.ioc;

import com.chubanova.command.*;
import com.chubanova.state.MoveTo;
import com.chubanova.state.Normal;
import com.chubanova.state.State;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class Context {

    private static final ThreadLocal<Boolean> initialized = new ThreadLocal<>();

    private static final Map<String, Queue<Command>> stashedCommands = new ConcurrentHashMap<>();
    private static final ThreadLocal<State> state = new ThreadLocal<>();


    public static void initGeneralScope() {
        if (Boolean.TRUE.equals(initialized.get())) {
            return;
        }
        initialized.set(true);

        Initializer<ScopeDictionary<IoCDictionary<?>>> initializerScopeDictionary = new InitializerScopeDictionary();
        ScopeDictionary<IoCDictionary<?>> scopesDictionary = ScopeDictionaryImpl.getInstance();
        initializerScopeDictionary.initialize(scopesDictionary);

        registerAll();
    }

    private static void registerAll() {
        IoC.<Command>resolve(
                "IoC.Register",
                "GameState",
                (Function<Object[], Object>) (
                        args -> state
                )).execute();

        IoC.<Command>resolve(
                "IoC.Register",
                "NormalState",
                (Function<Object[], Object>) (
                        args -> new Normal()
                )).execute();

        IoC.<Command>resolve(
                "IoC.Register",
                "MoveToState",
                (Function<Object[], Object>) (
                        args -> new MoveTo()
                )).execute();

        IoC.<Command>resolve(
                "IoC.Register",
                "HardStopCommand",
                (Function<Object[], Object>) (
                        args -> new HardStopCommand()
                )).execute();

        IoC.<Command>resolve(
                "IoC.Register",
                "MoveToCommand",
                (Function<Object[], Object>) (
                        args -> new MoveToCommand()
                )).execute();

        IoC.<Command>resolve(
                "IoC.Register",
                "RunCommand",
                (Function<Object[], Object>) (
                        args -> new RunCommand()
                )).execute();

        IoC.<Command>resolve(
                "IoC.Register",
                "SaveCommand",
                (Function<Object[], Object>) (
                        args -> new SaveCommand((Queue<Command>) args[0])
                )).execute();

        IoC.<Command>resolve(
                "IoC.Register",
                "CommandsQueue",
                (Function<Object[], Object>) (
                        args -> {
                            Queue<Command> queue = new LinkedList<>();
                            Arrays.asList(args).forEach(a -> queue.add((Command) a));
                            return queue;
                        }
                )).execute();

        IoC.<Command>resolve(
                "IoC.Register",
                "GameStartCommand",
                (Function<Object[], Object>) (
                        args -> new GameStartCommand(
                                (Queue<Command>) args[0],
                                (AtomicBoolean) args[1])
                )).execute();

        IoC.<Command>resolve(
                "IoC.Register",
                "StashedCommandsStorage",
                (Function<Object[], Object>) (
                        args -> stashedCommands
                )).execute();
    }

}
