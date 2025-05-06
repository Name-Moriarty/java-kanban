package task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubTaskTest {

    private SubTask subTask1;
    private SubTask subTask2;

    @BeforeEach
    public void setDataEpic() {
        subTask1 = new SubTask("Первая задача", "Это наш первый тест", 1, TaskStatus.NEW, LocalDateTime.of(2006, 1, 1, 1, 0, 1), Duration.ofMinutes(60));
        subTask2 = new SubTask("Вторая задача", "Это наш первый тест", 1, TaskStatus.NEW, LocalDateTime.of(2005, 1, 1, 1, 0, 1), Duration.ofMinutes(60));
    }

    @Test
    public void taskequalsSubTaskFalse() {
        subTask1.setId(1);
        subTask2.setId(1);
        assertEquals(subTask1, subTask2);
    }

}