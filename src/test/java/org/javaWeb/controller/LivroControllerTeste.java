package org.javaWeb.controller;

import com.google.gson.Gson;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.javaWeb.App;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LivroControllerTeste {
    private static Javalin app;
    private static final Gson gson = new Gson();
    private final String novoLivroJson = "{"+"\"titulo\": \"O Senhor dos Anéis\"," + "\"autor\": \"J.R.R. Tolkien\"," + "\"anoPublicado\": 1954," + "\"genero\": \"Aventura\"," + "\"editora\": \"Martins Fontes\"" + "}";

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

    @Test
    void deveBuscarLivrosPorGenero() {
        JavalinTest.test(app, (server, client) -> {
            client.post("/livros", novoLivroJson);
            var response = client.get("/livros/Aventura");

            assertEquals(200, response.code());
            String responseBody = response.body().string();
            assertTrue(responseBody.contains("O Senhor dos Anéis"));
            assertTrue(responseBody.contains("Aventura"));
        });
    }

    @Test
    void deveRetornarArrayNaoVazioAoCriarLivro() {
        JavalinTest.test(app, (server, client) -> {
            client.post("/livros", novoLivroJson);

            var response = client.get("/livros");

            assertEquals(200, response.code());

            String responseBody = response.body().string();
            assertTrue("Deve retornar um array json", responseBody.startsWith("[") && responseBody.endsWith("]"));

            assertFalse(responseBody.equals("[]"),"O array não pode estar vazio");
            assertTrue("O livro deve estar na listagem", responseBody.contains("O Senhor dos Anéis"));
        });
    }
}
