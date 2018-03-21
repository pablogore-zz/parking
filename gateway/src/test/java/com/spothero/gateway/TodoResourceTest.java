package com.spothero.gateway;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TodoResourceTest {

  private HttpServer server;
  private WebTarget target;

  @Before
  public void setUp() throws Exception {
    // start the server
    server = Main.startServer();
    // create the client
    Client c = ClientBuilder.newClient();

    // uncomment the following line if you want to enable
    // support for JSON in the client (you also have to uncomment
    // dependency on jersey-media-json module in pom.xml and Main.startServer())
    // --
    // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

    target = c.target(Main.BASE_URI);
  }

  @After
  public void tearDown() throws Exception {
    server.stop();
  }

  /**
   * Test to see that the message "Got it!" is sent in the response.
   */
  @Test
  public void testGetIt() {
    String responseMsg = target.path("todo").request().get(String.class);

    // Get XML
    String xmlResponse = target.path("todo").request()
        .accept(MediaType.TEXT_XML).get(String.class);
    // Get XML for application
    String xmlAppResponse = target.path("todo").request()
        .accept(MediaType.APPLICATION_XML).get(String.class);

    // Get JSON for application
    Todo jsonResponse = target.path("todo").request()
        .accept(MediaType.APPLICATION_JSON).get(Todo.class);

    System.out.println(xmlResponse);
    System.out.println(xmlAppResponse);
    System.out.println(jsonResponse);

    assertEquals("Application JSON Todo Summary", jsonResponse.getSummary());
  }
}
