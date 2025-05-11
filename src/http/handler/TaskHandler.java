package http.handler;

import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;
import task.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TaskHandler extends BaseTaskHttpHandler {

    private final TaskManager manager;

    public TaskHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    void getHandle(HttpExchange exchange) throws IOException {
        Integer id = getIdFromPath(exchange.getRequestURI().getPath());
        if (id == null) {
            String jsonString = gson.toJson(manager.getListTask());
            sendText(exchange, jsonString);
        } else {
            Task task = manager.getTask(id);
            if (task != null) {
                sendText(exchange, gson.toJson(task));
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
            boolean isUpdated = manager.createTask(gson.fromJson(response, Task.class));
            if (isUpdated) {
                exchange.sendResponseHeaders(200, 0);
                exchange.close();
            } else {
                sendHasInteractions(exchange);
            }
        } else {
            Task task = manager.getTask(id);
            if (task != null) {
                String response = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                boolean isUpdated = manager.taskUpdate(gson.fromJson(response, Task.class));
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
        if (id != null && manager.getTask(id) != null) {
            manager.taskDelete(id);
            exchange.sendResponseHeaders(200, 0);
            exchange.close();
        } else {
            sendNotFound(exchange);
        }
    }

}






