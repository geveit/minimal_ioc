package com.geveit.minimal_ioc.ioc;

public interface ServiceRegistry {
    void register(Class<?> type, Class<?> implType, ServiceScope scope);
    ServiceEntry getEntry(Class<?> type);
}