package org.javaWeb;


import com.google.gson.Gson;
import io.javalin.Javalin;
import org.javaWeb.controller.*;


public class App
{
    public static void main( String[] args )
    {
        Gson gson = new Gson();
        Javalin app  = Javalin.create().start(7000);

        HelloController.routes(app);
        StatusController.routes(app);
        EchoController.routes(app, gson);
        SaudacaoController.routes(app, gson);
        LivroController.routes(app, gson);

    }
}
