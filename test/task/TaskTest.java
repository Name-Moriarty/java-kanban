package task;

import manager.InMemoryTaskManager;
import manager.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private Task task;
    private Task task2;

    @BeforeEach
    public void setData() {
        task = new Task("Первая задача", "Это наш первый тест", "NEW");
        task2 = new Task("Вторая задача", "Это наш первый тест", "NEW");
    }

    @Test
    public void taskequalsTaskFalse() {
        task.setId(1);
        task2.setId(1);
        assertEquals(task, task2);
    }
}