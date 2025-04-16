package manager;


import task.Epic;

import task.SubTask;

import task.Task;
import task.TaskStatus;


import java.util.List;


public interface TaskManager {

    int counterIncrease();

    List<Task> getListTask();

    List<Epic> getListEpic();

    List<SubTask> getListSubtask();

    void deleteAllEpic();

    void deleteAllTask();

    void deleteAllSubtask();

    boolean createTask(Epic epic);

    boolean createTask(SubTask subtask);

    boolean createTask(Task task);

    void epicUpdate(String task, String description, int idTusk);

    void subTaskUpdate(String name, String description, int epicId, TaskStatus status);

    void taskUpdate(String task, String description, TaskStatus status, int idTusk);

    void taskDelete(int key);

    List<SubTask> getEpicSubTasksList(int key);

    Epic getEpicHashMap(int key);

    Task getTaskHashMap(int key);

    SubTask getSubTaskHashMap(int key);

    List<Task> getHistory();

    public void save();

    public void reader();
}

