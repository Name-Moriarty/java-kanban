package manager;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.SubTask;
import task.Task;
import task.TaskStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T manager;

    protected Task task;
    protected Task task2;

    protected Epic epic1;
    protected Epic epic2;

    protected SubTask subtask1;
    protected SubTask subtask2;
    protected SubTask subtask3;

    @Test
    public void testCounterIncrease() {
        assertEquals(1, manager.counterIncrease());
    }

    @Test
    public void createTask() {
        assertTrue(manager.createTask(task));
        assertEquals(task, manager.getTask(1));
    }

    @Test
    public void createEpic() {
        assertTrue(manager.createTask(epic1));
        assertEquals(epic1, manager.getEpic(epic1.getId()));
    }

    @Test
    public void createSubtask() {
        manager.createTask(epic1);
        assertTrue(manager.createSubtask(subtask1));
        assertEquals(subtask1, manager.getSubtask(subtask1.getId()));
    }

    @Test
    public void testGetTask() {
        manager.createTask(task);
        assertEquals(task, manager.getTask(1));
    }

    @Test
    public void testGetEpic() {
        manager.createTask(epic1);
        assertEquals(epic1, manager.getEpic(1));
    }

    @Test
    public void testGetSubtask() {
        manager.createTask(epic1);
        manager.createSubtask(subtask1);
        assertEquals(subtask1, manager.getSubtask(2));
    }

    @Test
    public void testGetAllTask() {
        manager.createTask(task);
        manager.createTask(task2);
        assertEquals(manager.getListTask().size(), 2);
    }

    @Test
    public void testGetAllEpic() {
        manager.createTask(epic1);
        manager.createTask(epic2);
        assertEquals(manager.getListEpic().size(), 2);
    }

    @Test
    public void testGetAllSubtask() {
        manager.createTask(epic1);
        manager.createSubtask(subtask2);
        manager.createSubtask(subtask1);
        assertEquals(manager.getListSubtask().size(), 2);
    }

    @Test
    public void testGetEpicSubTasksList() {
        manager.createTask(epic1);
        manager.createSubtask(subtask2);
        manager.createSubtask(subtask1);
        assertEquals(manager.getEpic(epic1.getId()).getSubTaskIds().size(), 2);
    }

    @Test
    public void testUpdateTask() {
        manager.createTask(task);
        task2.setId(1);
        manager.taskUpdate(task2);
        assertEquals(manager.getTask(1), task2);
    }

    @Test
    public void testUpdateEpic() {
        manager.createTask(epic1);
        epic2.setId(1);
        manager.epicUpdate(epic2);
        assertEquals(manager.getEpic(1), epic2);
    }

    @Test
    public void testUpdateSubtask() {
        manager.createTask(epic2);
        manager.createSubtask(subtask1);
        subtask2.setId(2);
        manager.subTaskUpdate(subtask2);
        assertEquals(manager.getSubtask(2), subtask2);
    }

    @Test
    public void testDeleteTask() {
        manager.createTask(task);
        manager.taskDelete(1);
        assertNull(manager.getTask(1));
    }

    @Test
    public void testDeleteEpic() {
        manager.createTask(epic1);
        manager.createTask(subtask1);
        manager.taskDelete(1);
        assertNull(manager.getEpic(1));
    }

    @Test
    public void testDeleteSubtask() {
        manager.createTask(task);
        manager.createTask(epic1);
        manager.createTask(subtask1);
        manager.taskDelete(3);
        assertNull(manager.getSubtask(3));
    }

    @Test
    public void testDeleteAllTask() {
        manager.createTask(task);
        manager.createTask(task2);
        assertEquals(manager.getListTask().size(), 2);
        manager.deleteAllTask();
        assertTrue(manager.getListTask().isEmpty());
    }

    @Test
    public void testDeleteAllEpic() {
        manager.createTask(epic1);
        manager.createTask(epic2);
        assertEquals(manager.getListEpic().size(), 2);
        manager.deleteAllEpic();
        assertTrue(manager.getListEpic().isEmpty());
    }

    @Test
    public void testDeleteAllSubtask() {
        manager.createTask(epic1);
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        assertEquals(manager.getListSubtask().size(), 2);
        manager.deleteAllSubtask();
        assertTrue(manager.getListSubtask().isEmpty());
    }

    @Test
    public void testStatusTaskNew() {
        manager.createTask(task);
        assertEquals(TaskStatus.NEW, manager.getTask(1).getStatus());
    }

    @Test
    public void testStatusTaskInProgress() {
        manager.createTask(task);
        task.setStatus(TaskStatus.IN_PROGRESS);
        manager.taskUpdate(task);
        assertEquals(TaskStatus.IN_PROGRESS, manager.getTask(1).getStatus());
    }

    @Test
    public void testStatusTaskDone() {
        manager.createTask(task);
        task.setStatus(TaskStatus.DONE);
        manager.taskUpdate(task);
        assertEquals(TaskStatus.DONE, manager.getTask(1).getStatus());
    }

    @Test
    public void testStatusEpicNew() {
        manager.createTask(epic1);
        assertEquals(TaskStatus.NEW, manager.getEpic(1).getStatus());
    }

    @Test
    public void testStatusEpicInProgress() {
        manager.createTask(epic1);
        manager.createSubtask(subtask2);
        assertEquals(TaskStatus.IN_PROGRESS, manager.getEpic(1).getStatus());
    }

    @Test
    public void testStatusEpicDone() {
        manager.createTask(epic1);
        manager.createSubtask(subtask2);
        subtask2.setStatus(TaskStatus.DONE);
        manager.subTaskUpdate(subtask2);
        assertEquals(TaskStatus.DONE, manager.getEpic(1).getStatus());
    }
}