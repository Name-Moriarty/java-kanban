package task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    private Task task;
    private Task task2;

    @BeforeEach
    public void setData() {
        task = new Task("Первая задача", "Это наш первый тест", TaskStatus.NEW, LocalDateTime.of(2006, 1, 1, 1, 0, 1), Duration.ofMinutes(60));
        task2 = new Task("Вторая задача", "Это наш первый тест", TaskStatus.NEW, LocalDateTime.of(2005, 1, 1, 1, 0, 1), Duration.ofMinutes(60));
    }

    @Test
    public void taskequalsTaskFalse() {
        task.setId(1);
        task2.setId(1);
        assertEquals(task, task2);
    }
}