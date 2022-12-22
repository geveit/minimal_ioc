package com.geveit.minimal_ioc.ioc;

public class ServiceEntry {
    private Class<?> type;
    private Class<?> implType;
    private ServiceScope scope;

    public ServiceEntry(Class<?> type, Class<?> implType, ServiceScope scope) {
        this.type = type;
        this.implType = implType;
        this.scope = scope;
    }

    public Class<?> getType() {
        return type;
    }

    public Class<?> getImplType() {
        return implType;
    }

    public ServiceScope getScope() {
        return scope;
    }
}
