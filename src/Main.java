import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.SubTask;
import task.Task;
import task.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        Epic epic1 = new Epic("Помыть машину", "Это будет весело");
        Epic epic2 = new Epic("Выучить java", "учиться в Yandex");
        SubTask subtask = new SubTask("Мыть машину", "Налить воду в ведро ", 1, TaskStatus.NEW, LocalDateTime.of(2002, 1, 1, 1, 15, 1), Duration.ofMinutes(200));
        SubTask subtask2 = new SubTask("Протереть машину на сухо", "Взять специальную тряпку", 1, TaskStatus.NEW, LocalDateTime.of(2001, 1, 1, 1, 15, 1), Duration.ofMinutes(30));
        SubTask subtask3 = new SubTask("Оплатить учебу", "Старательно учить!!", 2, TaskStatus.NEW, LocalDateTime.of(2004, 1, 1, 1, 15, 1), Duration.ofMinutes(30));
        Task task1 = new Task("Задача", "Решить", TaskStatus.NEW, LocalDateTime.of(2003, 1, 1, 1, 15, 1), Duration.ofMinutes(200));
        manager.createTask(epic1);
        manager.createTask(epic2);
        manager.createTask(subtask);
        manager.createTask(subtask2);
        manager.createTask(subtask3);
        manager.createTask(task1);
        System.out.println(manager.getPrioritizedTasks());
        manager.getEpicHashMap(1);
        manager.getEpicHashMap(2);
        manager.getEpicHashMap(2);
        manager.getSubTaskHashMap(3);
        System.out.println("История просмотров" + manager.getHistory());
        System.out.println("Список всех созданных задач:");
        manager.getListTask();
        manager.getListEpic();
        manager.getListSubtask();
        manager.subTaskUpdate("Машина чистая", "радоваться!", 3, TaskStatus.NEW);
        manager.subTaskUpdate("Протереть машину на сухо", "Взять специальную тряпку", 4, TaskStatus.IN_PROGRESS);
        manager.subTaskUpdate("Оплатить учебу", "Старательно учить!!", 5, TaskStatus.DONE);
        manager.epicUpdate("Помыть машину", "Это будет весело выполнять", 1);
        manager.epicUpdate("Выучить java", "учиться в Yandex весьма не сложно", 2);
        manager.taskUpdate("Задача изменена", "Решена", TaskStatus.DONE, 6);
    }
}
