package http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

abstract class BaseHistoryHttpHandler extends BaseHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method) {
            case "GET":
                getHandle(exchange);
                break;
            default:
                sendMethodNotAllowed(exchange);
        }
    }

    abstract void getHandle(HttpExchange exchange) throws IOException;

}
