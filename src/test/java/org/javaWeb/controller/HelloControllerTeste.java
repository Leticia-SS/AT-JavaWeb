package org.javaWeb.controller;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.javaWeb.App;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;


public class HelloControllerTeste {
    private static Javalin app;
    @BeforeAll
    static void setup() {
        app = App.createApp();
    }

    @Test
    void deveRetornarHelloJavalin() {
        JavalinTest.test(app,(server, client) -> {
            var response = client.get("/hello");
            assertEquals(200, response.code());
            assertEquals("Hello, Javalin!!!", response.body().string());
        });
    }
}
