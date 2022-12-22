package com.geveit.minimal_ioc.ioc;

import com.geveit.minimal_ioc.ioc.exceptions.DuplicateRegistryException;
import com.geveit.minimal_ioc.ioc.exceptions.ServiceNotRegisteredException;
import com.geveit.minimal_ioc.ioc.impl.ServiceRegistryImpl;
import com.geveit.minimal_ioc.services.ConsoleWriter;
import com.geveit.minimal_ioc.services.Writer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceRegistryTests {
    private ServiceRegistry serviceRegistry;

    @BeforeEach
    public void init() {
        serviceRegistry = new ServiceRegistryImpl();
    }

    @Test
    public void shouldRegisterCorrectEntry() {
        ServiceEntry entry = new ServiceEntry(Writer.class, ConsoleWriter.class, ServiceScope.TRANSIENT);

        serviceRegistry.register(entry);

        ServiceEntry result = serviceRegistry.getEntry(Writer.class);

        assertThat(result.getType(), equalTo(entry.getType()));
        assertThat(result.getImplType(), equalTo(entry.getImplType()));
        assertThat(result.getScope(), equalTo(entry.getScope()));
    }

    @Test
    public void registerDuplicateServiceThrowsException() {
        assertThrows(DuplicateRegistryException.class, () -> {
            ServiceEntry entry = new ServiceEntry(Writer.class, ConsoleWriter.class, ServiceScope.TRANSIENT);
            ServiceEntry entry2 = new ServiceEntry(Writer.class, ConsoleWriter.class, ServiceScope.SINGLETON);
            serviceRegistry.register(entry);
            serviceRegistry.register(entry2);
        });
    }

    @Test
    public void getNotRegisteredEntryThrowsExcpeption() {
        assertThrows(ServiceNotRegisteredException.class, () -> {
            serviceRegistry.getEntry(Writer.class);
        });
    }
}
