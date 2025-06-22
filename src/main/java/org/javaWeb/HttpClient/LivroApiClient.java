package org.javaWeb.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LivroApiClient {
    private static final String BASE_URL = "http://localhost:7000";

    public static void main(String[] args) {
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
            criarLivroComPost();
            listarLivros();
            buscarPorGenero("Aventura");
            verificarStatus();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void criarLivroComPost() throws IOException {
        URL url = new URL(BASE_URL + "/livros");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        String livroJson = "{"+"\"titulo\": \"O Senhor dos Anéis\"," + "\"autor\": \"J.R.R. Tolkien\"," + "\"anoPublicado\": 1954," + "\"genero\": \"Aventura\"," + "\"editora\": \"Martins Fontes\"" + "}";

        try(OutputStream os = connection.getOutputStream()) {
            os.write(livroJson.getBytes(StandardCharsets.UTF_8));
        }

        int statusCode = connection.getResponseCode();
        System.out.println("POST /livros - status:"+ statusCode);

        try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("Resposta: " + line);
            }
        }
        System.out.println("=====================================\n\n");
        connection.disconnect();
    }

    private static void listarLivros() throws IOException {
        URL url = new URL(BASE_URL + "/livros");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try{
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                String jsonFormatado = response.toString().replace("},{", "},\n{")
                        .replace("[", "[\n")
                        .replace("]", "\n]");

                System.out.println("Livros cadastrados:\n" + jsonFormatado);
            }
        } catch (IOException ex){
            System.err.println("Erro" +ex.getMessage());
        }

        System.out.println("=====================================\n\n");
        connection.disconnect();
    }

    private static void buscarPorGenero(String genero) throws IOException {
        URL url = new URL(BASE_URL + "/livros/" + genero);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try {
            int statusCode = connection.getResponseCode();
            System.out.println("Codigo HTTP = " + statusCode);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                String jsonFormatado = response.toString().replace("},{", "},\n{")
                        .replace("[", "[\n")
                        .replace("]", "\n]");

                System.out.println("Resposta:\n" + jsonFormatado);
                }

            } catch (IOException ex) {
                System.err.println("Erro" + ex.getMessage());
            }

        System.out.println("=====================================\n\n");
        connection.disconnect();
    }

    public static void verificarStatus() throws IOException {
        URL url = new URL(BASE_URL + "/status");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try {
            int statusCode = connection.getResponseCode();
            System.out.println("Código HTTP: " + statusCode);

            if (statusCode == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }

                    String jsonFormatado = response.toString().replace("},{", "},\n{")
                            .replace("[", "[\n")
                            .replace("]", "\n]");

                    System.out.println("Resposta do servidor");
                    System.out.println(jsonFormatado);
                }

            } else {
                System.err.println("Erro inesperado: " + statusCode);
            }

        } catch (IOException ex) {
            System.err.println("Erro" + ex.getMessage());
        }
        System.out.println("=====================================\n\n");
        connection.disconnect();
    }
}
