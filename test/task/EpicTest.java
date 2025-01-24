package task;

import static org.junit.jupiter.api.Assertions.*;

import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EpicTest {

    private TaskManager manager;
    private Epic epic1;
    private Epic epic2;

    @BeforeEach
    public void setDataEpic() {
        manager = Managers.getDefault();
        epic1 = new Epic("Первая задача", "Это наш первый тест");
        epic2 = new Epic("Вторая задача", "Это наш первый тест номер 2 ");
    }

    // У меня нет возможности эпик сделать своей подзадачей так-как подзадача привязывается при её созданий
    // и передается там объект самой подзадачи.
    @Test
    public void taskEualsEpicFalse() {
        epic1.setId(1);
        epic2.setId(1);
        assertEquals(epic1, epic2);
    }
}
