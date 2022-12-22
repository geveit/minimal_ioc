package com.geveit.minimal_ioc.ioc;

import com.geveit.minimal_ioc.ioc.impl.MinimalIoCImpl;
import com.geveit.minimal_ioc.services.ConsoleWriter;
import com.geveit.minimal_ioc.services.Greeter;
import com.geveit.minimal_ioc.services.WorldGreeter;
import com.geveit.minimal_ioc.services.Writer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MinimalIoCIntegrationTests {
    private MinimalIoC minimalIoC;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void init() {
        minimalIoC = MinimalIoCFactory.build();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void shouldPrintCorrectMessage() {
        minimalIoC.registerTransient(Writer.class, ConsoleWriter.class);
        minimalIoC.registerSingleton(Greeter.class, WorldGreeter.class);

        Greeter greeter = minimalIoC.resolve(Greeter.class);

        greeter.greet();

        assertThat("Hello, World!", equalTo(outputStream.toString().trim()));
    }
}
