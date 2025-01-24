import task.*;
import manager.*;

public class Main {
    public static void main(String[] args) {
        //Убрал конструктор с ид. Все замечание постарался исправить. Спасибо большое за правки, приятно с тобой работать)
        TaskManager manager = Managers.getDefault();
        Epic epic1 = new Epic("Помыть машину", "Это будет весело");
        Epic epic2 = new Epic("Выучить java", "учиться в Yandex");
        SubTask subtask = new SubTask("Мыть машину", "Налить воду в ведро ", 1, "NEW");
        SubTask subtask2 = new SubTask("Протереть машину на сухо", "Взять специальную тряпку", 1, "NEW");
        SubTask subtask3 = new SubTask("Оплатить учебу", "Старательно учить!!", 2, "NEW");
        Task task1 = new Task("Задача", "Решить", "NEW");

        manager.createTask(epic1);
        manager.createTask(epic2);
        manager.createTask(subtask);
        manager.createTask(subtask2);
        manager.createTask(subtask3);
        manager.createTask(task1);
        manager.getEpicHashMap(1);
        manager.getEpicHashMap(2);
        manager.getSubTaskHashMap(3);
        manager.getTaskHashMap(6);
        System.out.println("История просмотров" + manager.getHistory());

        System.out.println("Список всех созданных задач:");
        manager.getListTask();
        manager.getListEpic();
        manager.getListSubtask();
        manager.subTaskUpdate("Машина чистая", "радоваться!", 3, "NEW");
        manager.subTaskUpdate("Протереть машину на сухо", "Взять специальную тряпку", 4, "IN_PROGRESS");
        manager.subTaskUpdate("Оплатить учебу", "Старательно учить!!", 5, "DONE");
        manager.epicUpdate("Помыть машину", "Это будет весело выполнять", 1);
        manager.epicUpdate("Выучить java", "учиться в Yandex весьма не сложно", 2);
        manager.taskUpdate("Задача изменена", "Решена", "DONE", 6);
    }
}
