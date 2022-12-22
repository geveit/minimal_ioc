package com.geveit.minimal_ioc.ioc;

public interface ServiceRegistry {
    void register(ServiceEntry entry);
    ServiceEntry getEntry(Class<?> type);
}