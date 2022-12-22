package com.geveit.minimal_ioc.ioc.impl;

import com.geveit.minimal_ioc.ioc.ServiceEntry;
import com.geveit.minimal_ioc.ioc.ServiceRegistry;
import com.geveit.minimal_ioc.ioc.ServiceResolver;
import com.geveit.minimal_ioc.ioc.ServiceScope;
import com.geveit.minimal_ioc.ioc.exceptions.ResolverException;
import com.geveit.minimal_ioc.ioc.exceptions.ServiceNotRegisteredException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceResolverImpl implements ServiceResolver {
    private final ServiceRegistry registry;
    private final Map<Class<?>, Object> singletonInstances;

    public ServiceResolverImpl(ServiceRegistry registry) {
        this.registry = registry;
        this.singletonInstances = new HashMap<>();
    }

    @Override
    public <T> T resolve(Class<T> type) {
        try {
            ServiceEntry entry = registry.getEntry(type);

            if (entry.getScope() == ServiceScope.SINGLETON) {
                return type.cast(getSingletonInstance(entry));
            }

            return type.cast(instantiate(entry));
        }
        catch (ServiceNotRegisteredException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ResolverException(e.getMessage());
        }
    }

    private Object instantiate(ServiceEntry entry)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?>[] constructors = entry.getImplType().getConstructors();

        Constructor<?> constructor = constructors[0];

        Class<?>[] parameterTypes = constructor.getParameterTypes();

        List<Object> parameterInstances = new ArrayList<>();
        for (Class<?> parameterType : parameterTypes) {
            parameterInstances.add(resolve(parameterType));
        }

        return constructor.newInstance(parameterInstances.toArray());
    }

    private Object getSingletonInstance(ServiceEntry entry)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Object instance = singletonInstances.get(entry.getType());

        if (instance == null) {
            instance = instantiate(entry);
            singletonInstances.put(entry.getType(), instance);
        }

        return instance;
    }
}
