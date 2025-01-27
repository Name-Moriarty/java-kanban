package manager;

import history.*;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.SubTask;
import task.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    private TaskManager manager = Managers.getDefault();
    private HistoryManager historyManager = Managers.getDefaultHistory();
    private final Task task = new Task("Первая задача", "Это наш первый тест", "NEW");
    private final Epic epic1 = new Epic("Первый эпик", "Это наш первый тест");
    private final SubTask subTask1 = new SubTask("Первая подзадача", "Это наш первый тест", 2, "NEW");
    private final Task task1 = new Task("Вторая задача", "Это наш первый тест", "NEW");
    private final Epic epic2 = new Epic("Второй эпик", "Это наш первый тест");
    private final SubTask subTask2 = new SubTask("Вторая подзадача", "Это наш первый тест", 5, "NEW");

    @Test
    public void managers() {
        assertEquals(InMemoryTaskManager.class, manager.getClass());
    }

    @Test
    public void createTask() {
        assertTrue(manager.createTask(task));
        assertTrue(manager.createTask(epic1));
        assertTrue(manager.createTask(subTask1));
        assertEquals(task, manager.getTaskHashMap(1));
        assertEquals(subTask1, manager.getSubTaskHashMap(3));
        assertEquals(epic1, manager.getEpicHashMap(2));
    }

    @Test
    public void testCounterIncrease() {
        assertEquals(1, manager.counterIncrease());
    }

    @Test
    public void testGetListAllTask() {
        manager.createTask(task);
        manager.createTask(epic1);
        manager.createTask(subTask1);
        manager.createTask(task1);
        manager.createTask(epic2);
        manager.createTask(subTask2);
        ArrayList<Task> listTask = new ArrayList<>(manager.getListTask());
        ArrayList<Epic> listEpic = new ArrayList<>(manager.getListEpic());
        ArrayList<SubTask> listSubtask = new ArrayList<>(manager.getListSubtask());
        for (Task task2 : listTask) {
            assertEquals(task2, manager.getTaskHashMap(task2.getId()));
        }
        for (Epic epic2 : listEpic) {
            assertEquals(epic2, manager.getEpicHashMap(epic2.getId()));
        }
        for (SubTask subTask2 : listSubtask) {
            assertEquals(subTask2, manager.getSubTaskHashMap(subTask2.getId()));
        }
    }

    @Test
    public void testDeleteTask() {
        manager.createTask(task);
        manager.createTask(task1);
        manager.taskDelete(1);
        assertEquals(1, manager.getListTask().size());
        manager.createTask(task);
        manager.deleteAllTask();
        assertEquals(0, manager.getListTask().size());
    }

    @Test
    public void testDeleteEpic() {
        manager.createTask(task);
        manager.createTask(epic1);
        manager.createTask(subTask1);
        manager.createTask(task1);
        manager.createTask(epic2);
        manager.createTask(subTask2);
        manager.taskDelete(2);
        assertEquals(1, manager.getListEpic().size());
        assertEquals(1, manager.getListSubtask().size());
        manager.deleteAllEpic();
        assertEquals(0, manager.getListEpic().size());
        assertEquals(0, manager.getListSubtask().size());
    }

    @Test
    public void testDeleteSubtask() {
        manager.createTask(task);
        manager.createTask(epic1);
        manager.createTask(subTask1);
        manager.createTask(task1);
        manager.createTask(epic2);
        manager.createTask(subTask2);
        manager.taskDelete(3);
        assertEquals(1, manager.getListSubtask().size());
        assertEquals(0, manager.getEpicHashMap(2).getSubTaskIds().size());
        manager.deleteAllSubtask();
        assertEquals(0, manager.getListSubtask().size());
        assertEquals(0, manager.getEpicHashMap(5).getSubTaskIds().size());
    }

    @Test
    public void testUpdateAllTask() {
        manager.createTask(task);
        manager.createTask(epic1);
        manager.createTask(subTask1);
        manager.createTask(task1);
        manager.createTask(epic2);
        manager.createTask(subTask2);
        manager.taskUpdate("Первая задача Выполнена", "Это наш первый тест", "DONE", 1);
        assertEquals("DONE", task.getStatus());
        manager.subTaskUpdate("Первая подзадача", "Это наш первый тест", 3, "DONE");
        assertEquals("DONE", subTask1.getStatus());
        assertEquals("DONE", epic1.getStatus());
        manager.epicUpdate("Второй эпик выполнен", "Это наш первый тест", 5);
        assertEquals("Второй эпик выполнен", epic2.getTask());
    }

    @Test
    public void testGetOneTask() {
        manager.createTask(task);
        manager.createTask(epic1);
        manager.createTask(subTask1);
        assertEquals(task, manager.getTaskHashMap(task.getId()));
        assertEquals(epic1, manager.getEpicHashMap(epic1.getId()));
        assertEquals(subTask1, manager.getSubTaskHashMap(subTask1.getId()));
    }
}


