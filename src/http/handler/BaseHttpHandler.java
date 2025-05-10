package http.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


public  class BaseHttpHandler {

    protected void sendText(HttpExchange httpExchange, String text) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        httpExchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        httpExchange.sendResponseHeaders(200, resp.length);
        httpExchange.getResponseBody().write(resp);
        httpExchange.close();
    }

    protected void sendNotFound (HttpExchange httpExchange) throws IOException{
        httpExchange.sendResponseHeaders(404,0);
        httpExchange.close();
    }

    protected void sendMethodNotAllowed (HttpExchange httpExchange) throws IOException{
        httpExchange.sendResponseHeaders(405, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("Такой метод не поддерживается".getBytes());
        }
    }
}