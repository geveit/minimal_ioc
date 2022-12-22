package com.geveit.minimal_ioc.ioc.impl;

import com.geveit.minimal_ioc.ioc.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class MinimalIoCImpl implements MinimalIoC {
    private final ServiceRegistry registry;
    private final ServiceResolver resolver;

    public MinimalIoCImpl(ServiceRegistry registry, ServiceResolver resolver) {
        this.registry = registry;
        this.resolver = resolver;
    }

    @Override
    public <T, I extends T> void registerTransient(Class<T> type, Class<I> implType) {
        registry.register(type, implType, ServiceScope.TRANSIENT);
    }

    @Override
    public <T, I extends T> void registerSingleton(Class<T> type, Class<I> implType) {
        registry.register(type, implType, ServiceScope.SINGLETON);
    }

    @Override
    public <T> T resolve(Class<T> type) {
        return resolver.resolve(type);
    }
}