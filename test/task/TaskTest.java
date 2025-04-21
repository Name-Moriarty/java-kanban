package task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    private Task task;
    private Task task2;

    @BeforeEach
    public void setData() {
        task = new Task("Первая задача", "Это наш первый тест", TaskStatus.NEW);
        task2 = new Task("Вторая задача", "Это наш первый тест", TaskStatus.NEW);
    }

    @Test
    public void taskequalsTaskFalse() {
        task.setId(1);
        task2.setId(1);
        assertEquals(task, task2);
    }
}