package task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpicTest {

    private Epic epic1;
    private Epic epic2;

    @BeforeEach
    public void setDataEpic() {
        epic1 = new Epic("Первая задача", "Это наш первый тест");
        epic2 = new Epic("Вторая задача", "Это наш первый тест номер 2 ");
    }

    @Test
    public void taskEualsEpicFalse() {
        epic1.setId(1);
        epic2.setId(1);
        assertEquals(epic1, epic2);
    }
}
