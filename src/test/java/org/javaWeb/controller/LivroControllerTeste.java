package org.javaWeb.controller;

import com.google.gson.Gson;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.testtools.JavalinTest;
import org.javaWeb.App;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

public class LivroControllerTeste {
    private static Javalin app;
    private static final Gson gson = new Gson();
    private final String novoLivroJson = "{"+"\"titulo\": \"O Senhor dos Anéis\"," + "\"autor\": \"J.R.R. Tolkien\"," + "\"anoPublicado\": 1954," + "\"genero\": \"Fantasia\"," + "\"editora\": \"Martins Fontes\"" + "}";

    @BeforeAll
    static void setup() {
        app = App.createApp();
    }

    @Test
    void deveRetornar201AoCriarLivro() {
        JavalinTest.test(app,(server, client) -> {
            var response = client.post("/livros", novoLivroJson);

            assertEquals(201, response.code());
        });
    }
}
