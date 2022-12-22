package com.geveit.minimal_ioc.ioc.impl;

import com.geveit.minimal_ioc.ioc.MinimalIoC;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class MinimalIoCImpl implements MinimalIoC {
    private final Map<Class<?>, Class<?>> mappings;

    public MinimalIoCImpl() {
        mappings = new HashMap<>();
    }

    @Override
    public <T, I extends T> void register(Class<T> type, Class<I> implementationType) {
        mappings.put(type, implementationType);
    }

    @Override
    public <T> T resolve(Class<T> type) {
        try {
            Class<?> implementationType = mappings.get(type);
            return type.cast(instantiate(implementationType));
        }
        catch (Exception e) {
            return null;
        }
    }

    private <I> I instantiate(Class<I> type)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?>[] constructors = type.getConstructors();

        Constructor<?> constructor = constructors[0];

        Class<?>[] parameterTypes = constructor.getParameterTypes();

        List<Object> parameterInstances = new ArrayList<>();
        for (Class<?> parameterType : parameterTypes) {
            parameterInstances.add(instantiate(mappings.get(parameterType)));
        }

        return type.cast(constructor.newInstance(parameterInstances.toArray()));
    }
}