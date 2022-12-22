package com.geveit.minimal_ioc.services;

public class WorldGreeter implements Greeter {
    private final Writer writer;

    public WorldGreeter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void greet() {
        writer.write("Hello, World!");
    }
}
