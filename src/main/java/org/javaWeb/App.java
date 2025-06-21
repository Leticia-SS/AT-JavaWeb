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

//        app.get("/hello", ctx -> ctx.result("Hello, Javalin!!!"));

        Gson gson = new Gson();

        app.get("/status", ctx -> {
            Map<String, Object> resposta = new HashMap<>();
            resposta.put("Status", "ok");
            resposta.put("Timestampp", Instant.now().toString());
            ctx.json(gson.toJson(resposta));
        });





    }
}
