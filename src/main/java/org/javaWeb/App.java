package org.javaWeb;


import com.google.gson.Gson;
import io.javalin.Javalin;
import org.javaWeb.controller.*;


public class App
{
    public static void main( String[] args )
    {
        createApp().start(7000);
    }

    public static Javalin createApp() {
        Gson gson = new Gson();
        Javalin app = Javalin.create();

        HelloController.routes(app);
        StatusController.routes(app);
        EchoController.routes(app, gson);
        SaudacaoController.routes(app, gson);
        LivroController.routes(app, gson);

        return app;
    }
}
