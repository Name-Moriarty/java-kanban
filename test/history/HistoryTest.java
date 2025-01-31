package history;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.SubTask;
import task.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HistoryTest {

    private final TaskManager manager = Managers.getDefault();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private final Task task = new Task("Первая задача", "Это наш первый тест", "NEW");
    private final Task task1 = new Task("Первая задача", "Это наш первый тест", "NEW");
    private final SubTask subTask1 = new SubTask("Первая задача", "Это наш первый тест", 3, "NEW");
    private final Epic epic1 = new Epic("Первая задача", "Это наш первый тест");

    @BeforeEach
    public void createTask() {
        manager.createTask(task);
        manager.createTask(task1);
        manager.createTask(epic1);
        manager.createTask(subTask1);
    }

    @Test
    public void historyManager() {
        assertEquals(InMemoryHistoryManager.class, historyManager.getClass());
    }

    @Test
    public void testAddHistory() {
        assertEquals(0, manager.getHistory().size());
        manager.getTaskHashMap(1);
        manager.getEpicHashMap(3);
        manager.getSubTaskHashMap(4);
        manager.getSubTaskHashMap(4);
        assertEquals(3, manager.getHistory().size());
    }

    @Test
    public void removeTaskHistory() {
        manager.getTaskHashMap(1);
        manager.getTaskHashMap(2);
        manager.getEpicHashMap(3);
        manager.getSubTaskHashMap(4);
        manager.getSubTaskHashMap(4);
        manager.taskDelete(4);
        manager.createTask(subTask1);
        assertEquals(3, manager.getHistory().size());
    }

    @Test
    public void testGetHistory() {
        manager.getTaskHashMap(1);
        manager.getTaskHashMap(2);
        manager.getEpicHashMap(3);
        assertNotNull(manager.getHistory());
    }
}
