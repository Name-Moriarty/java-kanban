package manager;

import com.sun.net.httpserver.HttpServer;
import http.handler.EpicHandler;
import http.handler.HistoryHandler;
import http.handler.PrioritizedHandler;
import http.handler.SubTaskHandler;
import http.handler.TaskHandler;
import task.Epic;
import task.SubTask;
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
        Epic epic1 = new Epic("Помыть машину", "Это будет весело");
        Epic epic2 = new Epic("Выучить java", "учиться в Yandex");
        SubTask subtask = new SubTask("Мыть машину", "Налить воду в ведро ", 1, TaskStatus.NEW, LocalDateTime.of(2002, 1, 1, 1, 15, 1), Duration.ofMinutes(100));
        SubTask subtask2 = new SubTask("Протереть машину на сухо", "Взять специальную тряпку", 1, TaskStatus.NEW, LocalDateTime.of(2001, 1, 1, 4, 15, 1), Duration.ofMinutes(30));
        SubTask subtask3 = new SubTask("Оплатить учебу", "Старательно учить!!", 2, TaskStatus.NEW, LocalDateTime.of(2006, 1, 1, 1, 15, 1), Duration.ofMinutes(30));
        Task task1 = new Task("Задача", "Решить", TaskStatus.NEW, LocalDateTime.of(2003, 1, 1, 1, 15, 1), Duration.ofMinutes(200));
        manager.createTask(epic1);
        manager.createTask(epic2);
        manager.createSubtask(subtask);
        manager.createSubtask(subtask2);
        manager.createSubtask(subtask3);
        manager.createTask(task1);
        HttpTaskServer httpTaskServer = new HttpTaskServer(manager);
        httpTaskServer.serverStart();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down server...");
            httpTaskServer.serverStop();
            System.out.println("Server stopped.");
        }));
    }
}
