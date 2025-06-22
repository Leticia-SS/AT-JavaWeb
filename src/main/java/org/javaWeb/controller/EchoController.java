package org.javaWeb.controller;

import com.google.gson.Gson;
import io.javalin.Javalin;

import java.util.Map;

public class EchoController {
    public static void routes(Javalin app, Gson gson) {
        app.post("/echo", ctx -> {
            Map<String,String> requestBody = gson.fromJson(ctx.body(), Map.class);
            if(!requestBody.containsKey("mensagem")) {
                ctx.status(400).result("{\"erro\": \"campo 'mensagem' é obrigatório\"}");
                return;
            }
            ctx.json(gson.toJson(requestBody));
        });
    }
}
