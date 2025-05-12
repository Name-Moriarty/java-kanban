package http.handler;

import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHistoryHttpHandler {

    private final TaskManager manager;

    public HistoryHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    void getHandle(HttpExchange exchange) throws IOException {
        String jsonString = gson.toJson(manager.getHistory());
        sendText(exchange, jsonString);
    }
}
