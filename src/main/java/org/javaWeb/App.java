package org.javaWeb;


import com.google.gson.Gson;
import io.javalin.Javalin;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

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









    }
}
