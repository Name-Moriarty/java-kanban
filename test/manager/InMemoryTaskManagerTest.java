package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.SubTask;
import task.Task;
import task.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {


    @BeforeEach
    public void setData() {
        manager = new InMemoryTaskManager();
        epic1 = new Epic("Сделать тесты", "Всего-то 90 штук");
        epic2 = new Epic("Выпить чай", "С булочкой");
        subtask1 = new SubTask("Создать абстрактный класс", "В папке менеджер", 1, TaskStatus.NEW,
                LocalDateTime.of(2022, 11, 24, 1, 1, 1), Duration.ofDays(30));
        subtask2 = new SubTask("Унаследовать данный класс", "Это легко", 1,
                TaskStatus.IN_PROGRESS, LocalDateTime.of(2022, 8, 24, 1, 40, 1), Duration.ofMinutes(30));
        subtask3 = new SubTask("Налить воды  кружку", "Горячей!", 2,
                TaskStatus.NEW, LocalDateTime.of(2022, 8, 24, 2, 55, 1), Duration.ofMinutes(30));
        task = new Task("Сходить в магазин", "Купить молоко", TaskStatus.NEW, LocalDateTime.of(2022, 8, 24, 5, 1, 1), Duration.ofMinutes(30));
        task2 = new Task("Приготовать ужин", "Сделать салат", TaskStatus.NEW, LocalDateTime.of(2022, 8, 24, 7, 1, 1), Duration.ofMinutes(30));
    }

    @Test
    public void testCheckTaskDatesInstructionFasle() {
        manager.createTask(task);
        assertFalse(manager.createTask(task));
    }

    @Test
    public void testCheckTaskDatesInstructionTrue() {
        assertTrue(manager.createTask(task));
    }

    @Test
    public void testCheckEpicTimeUpdate() {
        manager.createTask(epic1);
        manager.createSubtask(subtask2);
        assertEquals(epic1.getEndTime(), subtask2.getEndTime());
        manager.createSubtask(subtask1);
        assertEquals(epic1.getEndTime(), subtask1.getEndTime());
    }

    @Test
    public void testCheckPrioritizedTasks() {
        manager.createTask(epic1);
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        assertEquals(manager.listPrioritizedTasks.first(), subtask2);
    }
}


