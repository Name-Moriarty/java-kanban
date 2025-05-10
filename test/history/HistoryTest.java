package history;

import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.SubTask;
import task.Task;
import task.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HistoryTest {

    private final TaskManager manager = new InMemoryTaskManager();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private final Task task = new Task("Первая задача", "Это наш первый тест", TaskStatus.NEW, LocalDateTime.of(2003, 1, 1, 1, 15, 1), Duration.ofMinutes(200));
    private final Task task1 = new Task("Первая задача", "Это наш первый тест", TaskStatus.NEW, LocalDateTime.of(2004, 1, 1, 1, 15, 1), Duration.ofMinutes(200));
    private final SubTask subTask1 = new SubTask("Первая задача", "Это наш первый тест", 3, TaskStatus.NEW, LocalDateTime.of(2002, 1, 1, 1, 15, 1), Duration.ofMinutes(200));
    private final Epic epic1 = new Epic("Первая задача", "Это наш первый тест");

    @BeforeEach
    public void createTask() {
        manager.createTask(task);
        manager.createTask(task1);
        manager.createTask(epic1);
        manager.createSubtask(subTask1);
    }

    @Test
    public void historyManager() {
        assertEquals(InMemoryHistoryManager.class, historyManager.getClass());
    }

    @Test
    public void testAddHistory() {
        assertEquals(0, manager.getHistory().size());
        manager.getTask(1);
        manager.getEpic(3);
        manager.getSubtask(4);
        assertEquals(3, manager.getHistory().size());
    }

    @Test
    public void removeTaskHistory() {
        manager.getTask(1);
        manager.getTask(2);
        manager.getEpic(3);
        manager.getSubtask(4);
        manager.getSubtask(4);
        manager.taskDelete(4);
        manager.createTask(subTask1);
        assertEquals(3, manager.getHistory().size());
    }

    @Test
    public void testGetHistory() {
        manager.getTask(1);
        manager.getTask(2);
        manager.getEpic(3);
        assertNotNull(manager.getHistory());
    }

    @Test
    public void testDubleHistoryTask() {
        manager.getTask(1);
        manager.getTask(1);
        assertEquals(1, manager.getHistory().size());
    }
}
