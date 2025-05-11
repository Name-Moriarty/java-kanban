package http.handler;

import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;
import task.SubTask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SubTaskHandler extends BaseTaskHttpHandler {

    private final TaskManager manager;

    public SubTaskHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    void getHandle(HttpExchange exchange) throws IOException {
        Integer id = getIdFromPath(exchange.getRequestURI().getPath());
        if (id == null) {
            String jsonString = gson.toJson(manager.getListSubtask());
            sendText(exchange, jsonString);
        } else {
            SubTask subTask = manager.getSubtask(id);
            if (subTask != null) {
                sendText(exchange, gson.toJson(subTask));
            } else {
                sendNotFound(exchange);
            }
        }
    }

    @Override
    void postHandle(HttpExchange exchange) throws IOException {
        Integer id = getIdFromPath(exchange.getRequestURI().getPath());
        if (id == null) {
            String response = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            boolean isUpdated = manager.createSubtask(gson.fromJson(response, SubTask.class));
            if (isUpdated) {
                exchange.sendResponseHeaders(200, 0);
                exchange.close();
            } else {
                sendHasInteractions(exchange);
            }
        } else {
            SubTask subTask = manager.getSubtask(id);
            if (subTask != null) {
                String response = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                boolean isUpdated = manager.subTaskUpdate(gson.fromJson(response, SubTask.class));
                if (isUpdated) {
                    exchange.sendResponseHeaders(200, 0);
                    exchange.close();
                } else {
                    sendHasInteractions(exchange);
                }
            } else {
                sendNotFound(exchange);
            }
        }
    }

    @Override
    void deleteHandle(HttpExchange exchange) throws IOException {
        Integer id = getIdFromPath(exchange.getRequestURI().getPath());
        if (id != null && manager.getSubtask(id) != null) {
            manager.taskDelete(id);
            exchange.sendResponseHeaders(200, 0);
            exchange.close();
        } else {
            sendNotFound(exchange);
        }
    }
}
