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
        serviceRegistry.register(Writer.class, ConsoleWriter.class, ServiceScope.TRANSIENT);

        ServiceEntry result = serviceRegistry.getEntry(Writer.class);

        assertThat(result.getType(), equalTo(Writer.class));
        assertThat(result.getImplType(), equalTo(ConsoleWriter.class));
        assertThat(result.getScope(), equalTo(ServiceScope.TRANSIENT));
    }

    @Test
    public void registerDuplicateServiceThrowsException() {
        assertThrows(DuplicateRegistryException.class, () -> {
            serviceRegistry.register(Writer.class, ConsoleWriter.class, ServiceScope.TRANSIENT);
            serviceRegistry.register(Writer.class, ConsoleWriter.class, ServiceScope.SINGLETON);
        });
    }

    @Test
    public void getNotRegisteredEntryThrowsExcpeption() {
        assertThrows(ServiceNotRegisteredException.class, () -> {
            serviceRegistry.getEntry(Writer.class);
        });
    }
}
