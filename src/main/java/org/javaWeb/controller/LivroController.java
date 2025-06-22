package org.javaWeb.controller;

import com.google.gson.Gson;
import io.javalin.Javalin;
import org.javaWeb.dto.LivroDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LivroController {
    public static void routes(Javalin app, Gson gson){
        List<LivroDTO> listaLivro = new ArrayList<>();

        app.post("/livros", ctx -> {
            LivroDTO livro = gson.fromJson(ctx.body(), LivroDTO.class);

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
            List<LivroDTO> livro = listaLivro.stream()
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
