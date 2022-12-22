package com.geveit.minimal_ioc.ioc;

import com.geveit.minimal_ioc.ioc.impl.MinimalIoCImpl;
import com.geveit.minimal_ioc.services.ConsoleWriter;
import com.geveit.minimal_ioc.services.Greeter;
import com.geveit.minimal_ioc.services.WorldGreeter;
import com.geveit.minimal_ioc.services.Writer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class MinimalIoCTests {
    private MinimalIoC minimalIoC;

    @BeforeEach
    public void init() {
        minimalIoC = new MinimalIoCImpl();
    }

    @Test
    public void shouldReturnCorrectMappedType() {
        minimalIoC.register(Writer.class, ConsoleWriter.class);

        Writer writer = minimalIoC.resolve(Writer.class);

        assertThat(writer, instanceOf(ConsoleWriter.class));
    }

    @Test
    public void shouldBuildServicesWithDependencies() {
        minimalIoC.register(Greeter.class, WorldGreeter.class);
        minimalIoC.register(Writer.class, ConsoleWriter.class);

        Greeter greeter = minimalIoC.resolve(Greeter.class);

        assertThat(greeter, instanceOf(WorldGreeter.class));
    }
}
