package com.spothero.api;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;

public class RateParkingResourceIT {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        server = Main.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testRateService() {

        Rates response = target.path("rate/2015-07-01T07:00:00Z/2015-07-01T12:00:00Z").request()
                .accept(MediaType.APPLICATION_XML).get(Rates.class);

        assertEquals(1, response.getRates().size());

        assertEquals("1500.0", String.valueOf(response.getRates().get(0).getPrice()));


        response = target.path("rate/2015-07-04T07:00:00Z/2015-07-04T12:00:00Z").request()
                .accept(MediaType.APPLICATION_XML).get(Rates.class);

        assertEquals(1, response.getRates().size());

        assertEquals("2000.0", String.valueOf(response.getRates().get(0).getPrice()));


        response = target.path("rate/2015-07-04T07:00:00Z/2015-07-04T20:00:00Z").request()
                .accept(MediaType.APPLICATION_XML).get(Rates.class);

        assertEquals(0, response.getRates().size());

    }
}
