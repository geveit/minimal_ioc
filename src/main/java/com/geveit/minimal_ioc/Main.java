package com.geveit.minimal_ioc;

import com.geveit.minimal_ioc.ioc.MinimalIoC;
import com.geveit.minimal_ioc.ioc.MinimalIoCFactory;
import com.geveit.minimal_ioc.ioc.impl.MinimalIoCImpl;
import com.geveit.minimal_ioc.services.ConsoleWriter;
import com.geveit.minimal_ioc.services.Greeter;
import com.geveit.minimal_ioc.services.WorldGreeter;
import com.geveit.minimal_ioc.services.Writer;

public class Main {
    public static void main(String[] args) {
        MinimalIoC ioc = MinimalIoCFactory.build();
        ioc.registerSingleton(Greeter.class, WorldGreeter.class);
        ioc.registerTransient(Writer.class, ConsoleWriter.class);

        Greeter greeter = ioc.resolve(Greeter.class);

        greeter.greet();
    }
}