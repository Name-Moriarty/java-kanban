package http.handler;

import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;
import task.Epic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EpicHandler extends BaseTaskHttpHandler {

    private final TaskManager manager;

    public EpicHandler(TaskManager manager) {
        this.manager = manager;
    }

    void getHandle(HttpExchange exchange) throws IOException {
        Integer id = getIdFromPath(exchange.getRequestURI().getPath());
        if (id == null) {
            String jsonString = gson.toJson(manager.getListEpic());
            sendText(exchange, jsonString);
        } else {
            Epic epic = manager.getEpic(id);
            if (epic != null) {
                sendText(exchange, gson.toJson(epic));
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
            Epic epic = gson.fromJson(response, Epic.class);
            manager.createTask(epic);
            exchange.sendResponseHeaders(200, 0);
            exchange.close();
        } else {
            Epic epic = manager.getEpic(id);
            if (epic != null) {
                String response = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                boolean isUpdated = manager.epicUpdate(gson.fromJson(response, Epic.class));
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
        if (id != null && manager.getEpic(id) != null) {
            manager.taskDelete(id);
            exchange.sendResponseHeaders(200, 0);
            exchange.close();
        } else {
            sendNotFound(exchange);
        }
    }
}
