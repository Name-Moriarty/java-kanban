package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Task;
import task.TaskStatus;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static java.io.File.createTempFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileBackedTaskManagerTest {

    private FileBackedTaskManager managerFiles;
    File file;
    private final Task task = new Task("Первая задача", "Это наш первый тест", TaskStatus.NEW, LocalDateTime.of(2002, 1, 1, 1, 15, 1), Duration.ofMinutes(200));

    @BeforeEach
    public void setData() throws IOException {
        file = createTempFile("Test.csv", null);
        managerFiles = new FileBackedTaskManager(file);
    }

    @Test
    public void saveFile() {
        managerFiles.createTask(task);
        managerFiles.save();
        assertTrue(file.length() > 0);
    }

    @Test
    public void saveEmptyFile() {
        managerFiles.save();
        assertTrue(file.length() == 0);
    }

    @Test
    public void readEmptyFile() {
        assertEquals(managerFiles.counterIncrease() - 1, 0);
    }


    @Test
    public void readFile() {
        managerFiles.createTask(task);
        managerFiles.getTask(task.getId());
        assertEquals(managerFiles.counterIncrease() - 1, 1);
    }
}
