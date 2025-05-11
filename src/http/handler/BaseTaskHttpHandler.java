package http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

abstract class BaseTaskHttpHandler extends BaseHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method) {
            case "GET":
                getHandle(exchange);
                break;
            case "POST":
                postHandle(exchange);
                break;
            case "DELETE":
                deleteHandle(exchange);
                break;
            default:
                sendMethodNotAllowed(exchange);
        }
    }

    abstract void getHandle(HttpExchange exchange) throws IOException;

    abstract void postHandle(HttpExchange exchange) throws IOException;

    abstract void deleteHandle(HttpExchange exchange) throws IOException;
}
