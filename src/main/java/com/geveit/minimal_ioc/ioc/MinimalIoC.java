package com.geveit.minimal_ioc.ioc;

public interface MinimalIoC {
    <T, I extends T> void registerTransient(Class<T> type, Class<I> implType);
    <T, I extends T> void registerSingleton(Class<T> type, Class<I> implType);
    <T> T resolve(Class<T> type);
}
