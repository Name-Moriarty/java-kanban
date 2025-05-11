package manager;

import task.Epic;
import task.SubTask;
import task.Task;

import java.util.List;
import java.util.TreeSet;

public interface TaskManager {

    int counterIncrease();

    List<Task> getListTask();

    List<Epic> getListEpic();

    List<SubTask> getListSubtask();

    void deleteAllEpic();

    void deleteAllTask();

    void deleteAllSubtask();

    boolean createTask(Epic epic);

    boolean createSubtask(SubTask subtask);

    boolean createTask(Task task);

    boolean epicUpdate(Epic epic);

    boolean subTaskUpdate(SubTask subTask);

    boolean taskUpdate(Task task);

    void taskDelete(int key);

    List<SubTask> getEpicSubTasksList(int key);

    Epic getEpic(int key);

    Task getTask(int key);

    SubTask getSubtask(int key);

    List<Task> getHistory();

    TreeSet<Task> getPrioritizedTasks();
}

