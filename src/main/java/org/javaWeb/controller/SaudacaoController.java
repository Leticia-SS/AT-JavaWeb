package org.javaWeb.controller;

import com.google.gson.Gson;
import io.javalin.Javalin;

import java.util.Map;

public class SaudacaoController {
    public static void routes(Javalin app, Gson gson){
        app.get("/saudacao/{nome}", ctx -> {
            String nome = ctx.pathParam("nome");
            Map<String,String> resposta = Map.of("mensagem", "Ola, " + nome + "!");
            ctx.json(gson.toJson(resposta));
        });
    }
}
