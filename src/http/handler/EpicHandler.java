package http.handler;

import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;

public class EpicHandler extends BaseTaskHttpHandler{

    private final TaskManager manager;

    public EpicHandler (TaskManager manager) {
        this.manager = manager;
    }
    @Override
    void getHandle(HttpExchange exchange) {

    }

    @Override
    void postHandle(HttpExchange exchange) {

    }

    @Override
    void deleteHandle(HttpExchange exchange) {

    }
}
