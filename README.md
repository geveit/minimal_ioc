# Minimal IoC Container

This project provides a minimal implementation of an Inversion of Control (IoC) container in Java. The IoC container allows for registration and resolution of service dependencies. It's simple project for the p

**Note: This is a simple project for the purpose of study only and is not intended for production use.**

## Features

- Registration of services with different scopes: Singleton and Transient.
- Service resolution based on registered types.
- Supports constructor injection for service instantiation.

## Usage

### 1. Registering Services

To register services with the IoC container, you need to use the `MinimalIoC` interface provided by the `MinimalIoCImpl` class.

```java
MinimalIoC ioc = new MinimalIoCImpl(new ServiceRegistryImpl(), new ServiceResolverImpl());

// Register a singleton service
ioc.registerSingleton(ServiceInterface.class, ServiceImplementation.class);

// Register a transient service
ioc.registerTransient(ServiceInterface.class, ServiceImplementation.class);
```


## Resolving Services

To resolve a service, you can use the `resolve` method provided by the `MinimalIoC` interface.

```java
ServiceInterface service = ioc.resolve(ServiceInterface.class);
```

The IoC container will resolve the service based on the registered types and scopes. If the service has been registered as a singleton, the same instance will be returned on subsequent resolutions. If the service has been registered as transient, a new instance will be created for each resolution.

### 3. Scopes

The IoC container supports two different scopes for registered services:

- Singleton: A single instance of the service is created and reused for all resolutions.
- Transient: A new instance of the service is created for each resolution.

You can specify the desired scope when registering a service using the `registerSingleton` or `registerTransient` methods of the `MinimalIoC` interface.

## Author

- [Gabriel Veit](https://github.com/geveit)