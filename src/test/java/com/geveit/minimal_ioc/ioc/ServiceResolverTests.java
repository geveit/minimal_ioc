package com.geveit.minimal_ioc.ioc;

import com.geveit.minimal_ioc.ioc.exceptions.ServiceNotRegisteredException;
import com.geveit.minimal_ioc.ioc.impl.ServiceResolverImpl;
import com.geveit.minimal_ioc.services.ConsoleWriter;
import com.geveit.minimal_ioc.services.Greeter;
import com.geveit.minimal_ioc.services.WorldGreeter;
import com.geveit.minimal_ioc.services.Writer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;

public class ServiceResolverTests {
    private ServiceResolver resolver;
    private ServiceRegistry registry;

    @BeforeEach
    public void init() {
        registry = mock(ServiceRegistry.class);
        resolver = new ServiceResolverImpl(registry);
    }

    @Test
    public void shouldReturnCorrectImplementationWithoutDependencies() {
        ServiceEntry entry = new ServiceEntry(Writer.class, ConsoleWriter.class, ServiceScope.TRANSIENT);
        when(registry.getEntry(Writer.class)).thenReturn(entry);

        Writer result = resolver.resolve(Writer.class);

        assertThat(result, instanceOf(ConsoleWriter.class));
    }

    @Test
    public void shouldReturnCorrectImplementationWithDependencies() {
        ServiceEntry writerEntry = new ServiceEntry(Writer.class, ConsoleWriter.class, ServiceScope.TRANSIENT);
        ServiceEntry greeterEntry = new ServiceEntry(Greeter.class, WorldGreeter.class, ServiceScope.TRANSIENT);

        when(registry.getEntry(Writer.class)).thenReturn(writerEntry);
        when(registry.getEntry(Greeter.class)).thenReturn(greeterEntry);

        Greeter result = resolver.resolve(Greeter.class);

        assertThat(result, instanceOf(WorldGreeter.class));
    }

    @Test
    public void resolvingNonRegisteredServiceShoudThrowException() {
        when(registry.getEntry(Writer.class)).thenThrow(new ServiceNotRegisteredException());

        assertThrows(ServiceNotRegisteredException.class, () -> {
            resolver.resolve(Writer.class);
        });
    }

    @Test
    public void transientTypesShouldAlwaysReturnDifferentInstance() {
        ServiceEntry writerEntry = new ServiceEntry(Writer.class, ConsoleWriter.class, ServiceScope.TRANSIENT);

        when(registry.getEntry(Writer.class)).thenReturn(writerEntry);

        Writer result1 = resolver.resolve(Writer.class);
        Writer result2 = resolver.resolve(Writer.class);

        assertThat(result1, not(sameInstance(result2)));
    }

    @Test
    public void singletonTypesShoundAlwaysReturnSameInstance() {
        ServiceEntry writerEntry = new ServiceEntry(Writer.class, ConsoleWriter.class, ServiceScope.SINGLETON);

        when(registry.getEntry(Writer.class)).thenReturn(writerEntry);

        Writer result1 = resolver.resolve(Writer.class);
        Writer result2 = resolver.resolve(Writer.class);

        assertThat(result1, sameInstance(result2));
    }

    @Test
    public void shouldReturnNestedSingletonServices() {
        ServiceEntry writerEntry = new ServiceEntry(Writer.class, ConsoleWriter.class, ServiceScope.SINGLETON);
        ServiceEntry greeterEntry = new ServiceEntry(Greeter.class, WorldGreeter.class, ServiceScope.SINGLETON);

        when(registry.getEntry(Writer.class)).thenReturn(writerEntry);
        when(registry.getEntry(Greeter.class)).thenReturn(greeterEntry);

        Greeter greeter1 = resolver.resolve(Greeter.class);
        Greeter greeter2 = resolver.resolve(Greeter.class);

        assertThat(greeter1, sameInstance(greeter2));
    }
}
