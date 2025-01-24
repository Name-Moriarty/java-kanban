package manager;

import history.*;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.SubTask;
import task.Task;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    private TaskManager manager = Managers.getDefault();
    private HistoryManager historyManager = Managers.getDefaultHistory();
    private final Task task = new Task("Первая задача", "Это наш первый тест", "NEW");
    private final SubTask subTask1 = new SubTask("Первая задача", "Это наш первый тест", 2, "NEW");
    private final Epic epic1 = new Epic("Первая задача", "Это наш первый тест");

    @Test
    public void managers() {
        assertEquals(InMemoryTaskManager.class, manager.getClass());
    }

    @Test
    public void historyManager() {
        assertEquals(InMemoryHistoryManager.class, historyManager.getClass());
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
    public void testGetHistory() {
        manager.createTask(task);
        manager.createTask(epic1);
        manager.createTask(subTask1);
        manager.getTaskHashMap(1);
        manager.getEpicHashMap(2);
        manager.getSubTaskHashMap(3);
        assertEquals(3, manager.getHistory().size());
    }
}


