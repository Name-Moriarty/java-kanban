package http.handler;

import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;

import java.io.IOException;

public class PrioritizedHandler extends BaseHistoryHttpHandler {
    private final TaskManager manager;

    public PrioritizedHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    void getHandle(HttpExchange exchange) throws IOException {
        String jsonString = gson.toJson(manager.getPrioritizedTasks());
        sendText(exchange, jsonString);
    }
}
