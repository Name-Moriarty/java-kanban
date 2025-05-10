package manager;

import com.sun.net.httpserver.HttpServer;
import http.handler.EpicHandler;
import http.handler.HistoryHandler;
import http.handler.PrioritizedHandler;
import http.handler.SubTaskHandler;
import http.handler.TaskHandler;
import task.Task;
import task.TaskStatus;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;


public class HttpTaskServer {
    private final HttpServer httpServer;


    public HttpTaskServer(TaskManager manager) throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        this.httpServer.createContext("/tasks", new TaskHandler(manager));
        this.httpServer.createContext("/epics", new EpicHandler(manager));
        this.httpServer.createContext("/subtasks", new SubTaskHandler(manager));
        this.httpServer.createContext("/history", new HistoryHandler(manager));
        this.httpServer.createContext("/prioritized", new PrioritizedHandler(manager));
    }

    public void serverStart() {
        httpServer.start();
    }

    public void serverStop() {
        httpServer.stop(1);
    }

    public static void main(String[] args) throws IOException {
        TaskManager manager = Managers.getHttpDefault();
        manager.createTask(new Task("Задача", "Решить"));
        HttpTaskServer httpTaskServer = new HttpTaskServer(manager);
        httpTaskServer.serverStart();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down server...");
            httpTaskServer.serverStop();
            System.out.println("Server stopped.");
        }));
    }
}
