package com.spothero.gateway;

import org.glassfish.jersey.server.ResourceConfig;
import com.codahale.metrics.jersey2.InstrumentedResourceMethodApplicationListener;

public class Application extends ResourceConfig {
    public Application(String myPackage) {
        super();

        packages(myPackage);

        register(new InstrumentedResourceMethodApplicationListener(Main.METRIC_REGISTRY));
    }
}