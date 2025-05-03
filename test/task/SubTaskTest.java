package task;

import manager.InMemoryTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SubTaskTest {

    private TaskManager manager;
    private SubTask subTask1;
    private SubTask subTask2;
    private Epic epic1;

    @BeforeEach
    public void setDataEpic() {
        manager = new InMemoryTaskManager();
        epic1 = new Epic("Первая задача", "Это наш первый тест");
        subTask1 = new SubTask("Первая задача", "Это наш первый тест", 1, TaskStatus.NEW, LocalDateTime.of(2006, 1, 1, 1, 0, 1), Duration.ofMinutes(60));
        subTask2 = new SubTask("Вторая задача", "Это наш первый тест", 1, TaskStatus.NEW, LocalDateTime.of(2005, 1, 1, 1, 0, 1), Duration.ofMinutes(60));
    }

    @Test
    public void taskequalsSubTaskFalse() {
        subTask1.setId(1);
        subTask2.setId(1);
        assertEquals(subTask1, subTask2);
    }

    @Test
    public void createSubtaskEpic() {
        subTask1.setId(1);
        assertFalse(manager.createTask(subTask1));
    }
}