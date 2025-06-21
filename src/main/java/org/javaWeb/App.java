package org.javaWeb;


import com.google.gson.Gson;
import io.javalin.Javalin;
import org.javaWeb.Controllers.Livro;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class App
{
    public static void main( String[] args )
    {
        Javalin app  = Javalin.create().start(7000);

        app.get("/hello", ctx -> ctx.result("Hello, Javalin!!!"));

        Gson gson = new Gson();

        app.get("/status", ctx -> {
            Map<String, Object> resposta = new HashMap<>();
            resposta.put("Status", "ok");
            resposta.put("Timestampp", Instant.now().toString());
            ctx.json(gson.toJson(resposta));
        });


        app.post("/echo", ctx -> {
            Map<String,String> requestBody = gson.fromJson(ctx.body(), Map.class);
            if(!requestBody.containsKey("mensagem")) {
                ctx.status(400).result("{\"erro\": \"campo 'mensagem' é obrigatório\"}");
                return;
            }
            ctx.json(gson.toJson(requestBody));
        });


        app.get("/saudacao/{nome}", ctx -> {
            String nome = ctx.pathParam("nome");
            Map<String,String> resposta = Map.of("mensagem", "Ola, " + nome + "!");
            ctx.json(gson.toJson(resposta));
        });


        List<Livro> listaLivro = new ArrayList<>();
        app.post("/livros", ctx -> {
            Livro livro = gson.fromJson(ctx.body(), Livro.class);

            if (livro.getTitulo() == null || livro.getAutor() == null || livro.getAnoPublicado() == 0 || livro.getGenero() == null || livro.getEditora() == null) {
                ctx.status(400).result("{\"erro\": \"campo é obrigatório\"}");
                return;
            }

            listaLivro.add(livro);
            ctx.status(201).json(gson.toJson(listaLivro));;
        });


        app.get("/livros", ctx -> {
            ctx.json(gson.toJson(listaLivro));
        });

        app.get("/livros/{genero}", ctx->{
            String buscarPorGenero = ctx.pathParam("genero");
            List<Livro> livro = listaLivro.stream()
                    .filter(l -> l.getGenero().equalsIgnoreCase(buscarPorGenero))
                    .collect(Collectors.toList());
            if (!livro.isEmpty()) {
                ctx.result(gson.toJson(livro));
            } else {
                ctx.status(404).result("{\"erro\": \"nenhum livro encontrado\"}");
            }

        });











    }
}
