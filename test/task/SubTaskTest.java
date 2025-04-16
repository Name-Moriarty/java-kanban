package task;

import manager.FileBackedTaskManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {

    private TaskManager manager;
    private SubTask subTask1;
    private SubTask subTask2;
    private Epic epic1;

    @BeforeEach
    public void setDataEpic() {
        manager = Managers.getDefault();
        epic1 = new Epic("Первая задача", "Это наш первый тест");
        subTask1 = new SubTask("Первая задача", "Это наш первый тест", 1, TaskStatus.NEW);
        subTask2 = new SubTask("Вторая задача", "Это наш первый тест", 1, TaskStatus.NEW);
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