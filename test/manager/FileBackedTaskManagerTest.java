package manager;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.SubTask;
import task.Task;
import task.TaskStatus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileBackedTaskManagerTest {

    private final FileBackedTaskManager managerFiles = new FileBackedTaskManager();
    private final Task task = new Task("Первая задача", "Это наш первый тест", TaskStatus.NEW);
    private final Epic epic1 = new Epic("Первый эпик", "Это наш первый тест");
    private final SubTask subTask1 = new SubTask("Первая подзадача", "Это наш первый тест", 2, TaskStatus.NEW);
    private final Task task1 = new Task("Вторая задача", "Это наш первый тест", TaskStatus.NEW);
    private final Epic epic2 = new Epic("Второй эпик", "Это наш первый тест");
    private final SubTask subTask2 = new SubTask("Вторая подзадача", "Это наш первый тест", 5, TaskStatus.NEW);

   /* @Test
    public void saveFile() {
        try {
            File newFile = File.createTempFile("Test.csv", null);
            Writer writer = new FileWriter(newFile, false);
            writer.write(task.toString());
            writer.close();
            assertTrue(newFile.length() > 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    @Test
    public void readEmptyFile() {
        try {
            File newFile = File.createTempFile("Test.csv", null);
            FileBackedTaskManager manager = managerFiles.loadFromFile(newFile);
            assertEquals(manager.counterIncrease() - 1, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

   /* @Test
    public void readFile() {
        try {
            File newFile = File.createTempFile("Test.csv", null);
            Writer writer = new FileWriter(newFile, false);
            writer.write(task.toString());
            writer.close();
            FileBackedTaskManager manager = managerFiles.loadFromFile(newFile);
            assertEquals(manager.counterIncrease() - 1, 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/
}
