package com.geveit.minimal_ioc.ioc;

public interface ServiceResolver {
    <T> T resolve(Class<T> type);
}
