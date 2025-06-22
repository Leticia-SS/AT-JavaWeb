package org.javaWeb.controller;

import io.javalin.Javalin;

public class HelloController {
    public static void routes(Javalin app){
        app.get("/hello", ctx ->
                ctx.result("Hello, Javalin!!!"));
    }
}
