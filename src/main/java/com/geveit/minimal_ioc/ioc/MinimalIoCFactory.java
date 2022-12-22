package com.geveit.minimal_ioc.ioc;

import com.geveit.minimal_ioc.ioc.impl.MinimalIoCImpl;
import com.geveit.minimal_ioc.ioc.impl.ServiceRegistryImpl;
import com.geveit.minimal_ioc.ioc.impl.ServiceResolverImpl;

public abstract class MinimalIoCFactory {
    public static MinimalIoC build() {
        ServiceRegistry registry = new ServiceRegistryImpl();
        ServiceResolver resolver = new ServiceResolverImpl(registry);
        return new MinimalIoCImpl(registry, resolver);
    }
}
