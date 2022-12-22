package com.geveit.minimal_ioc.ioc;

public interface MinimalIoC {
    <T, I extends T> void register(Class<T> type, Class<I> implementationType);
    <T> T resolve(Class<T> type);
}
