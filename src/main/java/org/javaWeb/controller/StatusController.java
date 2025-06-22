package org.javaWeb.controller;

import com.google.gson.Gson;
import io.javalin.Javalin;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class StatusController {
    public static void routes(Javalin app, Gson gson){
        app.get("/status", ctx -> {
            Map<String, Object> resposta = new HashMap<>();
            resposta.put("Status", "ok");
            resposta.put("Timestampp", Instant.now().toString());
            ctx.json(gson.toJson(resposta));
        });
    }
}
