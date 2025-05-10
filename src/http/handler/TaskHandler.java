package http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;
import task.Task;

import java.io.IOException;

public class TaskHandler extends BaseTaskHttpHandler {

    private final TaskManager manager;

    public TaskHandler(TaskManager manager) {
        this.manager = manager;
    }


    @Override
    void getHandle(HttpExchange exchange) throws IOException {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        if (pathParts.length == 2 && pathParts[1].equals("tasks")) {
            if (manager.getListTask().size() > 0) {
                Gson gson = new Gson();
                Task task = new Task("Задача", "Решить");
                String jsonString = gson.toJson(task);
                sendText(exchange, jsonString);
            }
        }
    }

    @Override
    void postHandle(HttpExchange exchange) {

    }

    @Override
    void deleteHandle(HttpExchange exchange) {

    }
}
