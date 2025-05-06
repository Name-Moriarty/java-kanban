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
        Task task2 = new Task("Задача", "Выполнена", TaskStatus.DONE, LocalDateTime.of(200, 1, 1, 1, 15, 1), Duration.ofMinutes(200));
        SubTask subTask4 = new SubTask("Протереть машину на сухо", "Взять специальную тряпку", 1, TaskStatus.NEW, LocalDateTime.of(2000, 1, 1, 4, 15, 1), Duration.ofMinutes(30));
        subTask4.setId(4);;
        task2.setId(6);
        manager.taskUpdate(task2);
        manager.subTaskUpdate(subTask4);
        //manager.taskDelete(6);
        System.out.println(manager.getPrioritizedTasks());
        manager.getEpic(1);
        manager.getEpic(2);
        manager.getEpic(2);
        manager.getSubtask(3);
        System.out.println("История просмотров" + manager.getHistory());
        System.out.println("Список всех созданных задач:");
        manager.getListTask();
        manager.getListEpic();
        manager.getListSubtask();
    }
}
