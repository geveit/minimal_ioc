package com.geveit.minimal_ioc.ioc.impl;

import com.geveit.minimal_ioc.ioc.ServiceEntry;
import com.geveit.minimal_ioc.ioc.ServiceRegistry;
import com.geveit.minimal_ioc.ioc.ServiceScope;
import com.geveit.minimal_ioc.ioc.exceptions.DuplicateRegistryException;
import com.geveit.minimal_ioc.ioc.exceptions.ServiceNotRegisteredException;

import java.util.HashMap;
import java.util.Map;

public class ServiceRegistryImpl implements ServiceRegistry {
    private final Map<Class<?>, ServiceEntry> mappings;

    public ServiceRegistryImpl() {
        mappings = new HashMap<>();
    }

    @Override
    public void register(Class<?> type, Class<?> implType, ServiceScope scope) {
        if (isTypeRegistered(type)) {
            throw new DuplicateRegistryException();
        }

        ServiceEntry entry = new ServiceEntry(type, implType, scope);
        mappings.put(entry.getType(), entry);
    }

    @Override
    public ServiceEntry getEntry(Class<?> type) {
        if (!isTypeRegistered(type)) {
            throw new ServiceNotRegisteredException();
        }

        return mappings.get(type);
    }

    private <T> boolean isTypeRegistered(Class<T> type) {
        return mappings.get(type) != null;
    }
}
