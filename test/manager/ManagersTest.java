package manager;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.SubTask;
import task.Task;
import task.TaskStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ManagersTest {
    private final TaskManager manager = new InMemoryTaskManager();
    private final TaskManager managerFiles = Managers.getDefault();
    private final Task task = new Task("Первая задача", "Это наш первый тест", TaskStatus.NEW);
    private final Epic epic1 = new Epic("Первый эпик", "Это наш первый тест");
    private final SubTask subTask1 = new SubTask("Первая подзадача", "Это наш первый тест", 2, TaskStatus.NEW);
    private final Task task1 = new Task("Вторая задача", "Это наш первый тест", TaskStatus.NEW);
    private final Epic epic2 = new Epic("Второй эпик", "Это наш первый тест");
    private final SubTask subTask2 = new SubTask("Вторая подзадача", "Это наш первый тест", 5, TaskStatus.NEW);

    @Test
    public void managers() {
        assertEquals(FileBackedTaskManager.class, managerFiles.getClass());
    }

    @Test
    public void createTask() {
        assertTrue(manager.createTask(task));
        assertEquals(task, manager.getTaskHashMap(1));
    }

    @Test
    void createEpic() {
        assertTrue(manager.createTask(epic1));
        assertEquals(epic1, manager.getEpicHashMap(1));
    }

    @Test
    void createSubtask() {
        assertTrue(manager.createTask(task));
        assertTrue(manager.createTask(epic1));
        assertTrue(manager.createTask(subTask1));
        assertEquals(subTask1, manager.getSubTaskHashMap(3));
    }

    @Test
    public void testCounterIncrease() {
        assertEquals(1, manager.counterIncrease());
    }

    @Test
    public void testGetTask() {
        manager.createTask(task);
        assertEquals(task, manager.getTaskHashMap(1));
    }

    @Test
    public void testGetEpic() {
        manager.createTask(epic1);
        assertEquals(epic1, manager.getEpicHashMap(1));
    }

    @Test
    public void testGetSubtask() {
        manager.createTask(task);
        manager.createTask(epic1);
        manager.createTask(subTask1);
        assertEquals(subTask1, manager.getSubTaskHashMap(3));
    }

    @Test
    public void testDeleteTask() {
        manager.createTask(task);
        manager.taskDelete(1);
        assertNull(manager.getTaskHashMap(1));
    }

    @Test
    public void testDeleteEpic() {
        manager.createTask(epic1);
        manager.createTask(subTask1);
        manager.taskDelete(1);
        assertNull(manager.getEpicHashMap(1));
    }

    @Test
    public void testDeleteSubtask() {
        manager.createTask(task);
        manager.createTask(epic1);
        manager.createTask(subTask1);
        manager.taskDelete(3);
        assertNull(manager.getSubTaskHashMap(3));
    }

    @Test
    public void testDeleteAllTask() {
        manager.createTask(task);
        manager.createTask(task1);
        manager.deleteAllTask();
        assertTrue(manager.getListTask().isEmpty());
    }

    @Test
    public void testDeleteAllEpic() {
        manager.createTask(epic1);
        manager.createTask(epic2);
        manager.deleteAllEpic();
        assertTrue(manager.getListEpic().isEmpty());
    }

    @Test
    public void testDeleteAllSubtask() {
        manager.createTask(task);
        manager.createTask(epic1);
        manager.createTask(subTask1);
        manager.createTask(task1);
        manager.createTask(epic2);
        manager.createTask(subTask2);
        manager.deleteAllSubtask();
        assertTrue(manager.getListSubtask().isEmpty());
    }

    @Test
    public void testStatusTask() {
        manager.createTask(task);
        manager.createTask(epic1);
        manager.createTask(subTask1);
        manager.createTask(task1);
        manager.createTask(epic2);
        manager.createTask(subTask2);
        manager.taskUpdate("Первая задача Выполнена", "Это наш первый тест", TaskStatus.DONE, 1);
        assertEquals(TaskStatus.DONE, task.getStatus());
        manager.subTaskUpdate("Первая подзадача", "Это наш первый тест", 3, TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, subTask1.getStatus());
        assertEquals(TaskStatus.DONE, epic1.getStatus());
        manager.epicUpdate("Второй эпик выполнен", "Это наш первый тест", 5);
        assertEquals("Второй эпик выполнен", epic2.getTask());
    }

    @Test
    public void testGetTaskById() {
        manager.createTask(task);
        manager.createTask(epic1);
        manager.createTask(subTask1);
        assertEquals(task, manager.getTaskHashMap(task.getId()));
        assertEquals(epic1, manager.getEpicHashMap(epic1.getId()));
        assertEquals(subTask1, manager.getSubTaskHashMap(subTask1.getId()));
    }
}


