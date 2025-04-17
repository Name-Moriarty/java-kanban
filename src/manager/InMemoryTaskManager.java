package manager;

import history.HistoryManager;
import task.Epic;
import task.SubTask;
import task.Task;
import task.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int taskNumber = 0;

    protected final Map<Integer, Task> taskHashMap = new HashMap<>();
    ;
    protected final Map<Integer, Epic> epicHashMap = new HashMap<>();
    ;
    protected final Map<Integer, SubTask> subtaskHashMap = new HashMap<>();
    ;

    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public int counterIncrease() {
        taskNumber = taskHashMap.size() + epicHashMap.size() + subtaskHashMap.size();
        return ++taskNumber;
    }

    @Override
    public List<Task> getListTask() {
        return new ArrayList<>(taskHashMap.values());
    }

    @Override
    public List<Epic> getListEpic() {
        return new ArrayList<>(epicHashMap.values());
    }

    @Override
    public List<SubTask> getListSubtask() {
        return new ArrayList<>(subtaskHashMap.values());
    }

    @Override
    public void deleteAllEpic() {
        epicHashMap.clear();
        subtaskHashMap.clear();
    }

    @Override
    public void deleteAllTask() {
        taskHashMap.clear();
    }

    @Override
    public void deleteAllSubtask() {
        subtaskHashMap.clear();
        for (Epic epic : epicHashMap.values()) {
            epic.getSubTaskIds().clear();
            updateEpicStatus(epic.getId());
        }
    }

    @Override
    public boolean createTask(Epic epic) {
        int taskNumber = counterIncrease();
        epic.setId(taskNumber);
        epicHashMap.put(taskNumber, epic);
        return true;
    }

    @Override
    public boolean createTask(SubTask subTask) {
        int taskNumber = counterIncrease();
        if (taskNumber == subTask.getEpicId()) {
            System.out.println("Подзадачу нельзя сделать свойм эпиком");
            return false;
        }
        subTask.setId(taskNumber);
        subtaskHashMap.put(taskNumber, subTask);
        List<Integer> epicSubtaskList = epicHashMap.get(subTask.getEpicId()).getSubTaskIds();
        epicSubtaskList.add(taskNumber);
        updateEpicStatus(subTask.getEpicId());
        return true;
    }

    @Override
    public boolean createTask(Task task) {
        int taskNumber = counterIncrease();
        task.setId(taskNumber);
        taskHashMap.put(taskNumber, task);
        return true;
    }

    @Override
    public void epicUpdate(String task, String taskDescription, int taskNumber) {
        if (epicHashMap.containsKey(taskNumber)) {
            epicHashMap.get(taskNumber).setTask(task);
            epicHashMap.get(taskNumber).setDescription(taskDescription);
        } else System.out.println("Задача с таким ид не найдена");
    }

    @Override
    public void subTaskUpdate(String task, String description, int subtaskId, TaskStatus status) {
        if (subtaskHashMap.containsKey(subtaskId)) {
            subtaskHashMap.get(subtaskId).setTask(task);
            subtaskHashMap.get(subtaskId).setDescription(description);
            subtaskHashMap.get(subtaskId).setStatus(status);
            updateEpicStatus(subtaskHashMap.get(subtaskId).getEpicId());
        } else System.out.println("Задача с таким ид не найдена");
    }

    @Override
    public void taskUpdate(String task, String description, TaskStatus status, int idTusk) {
        if (taskHashMap.containsKey(idTusk)) {
            taskHashMap.get(idTusk).setTask(task);
            taskHashMap.get(idTusk).setDescription(description);
            taskHashMap.get(idTusk).setStatus(status);
        } else System.out.println("Задача с таким ид не найдена");
    }

    @Override
    public void taskDelete(int key) {
        boolean fullTask = taskHashMap.containsKey(key);
        boolean fullEpic = epicHashMap.containsKey(key);
        boolean fullSubtask = subtaskHashMap.containsKey(key);
        if (fullTask) {
            historyManager.remove(key);
            taskHashMap.remove(key);
            System.out.println("Объект  удален");
        } else if (fullSubtask) {
            int epicId = subtaskHashMap.get(key).getEpicId();
            epicHashMap.get(epicId).removeSubTask(key);
            historyManager.remove(key);
            subtaskHashMap.remove(key);
            updateEpicStatus(epicId);
            System.out.println("Объект  удален");
        } else if (fullEpic) {
            ArrayList<Integer> epicSubtask = new ArrayList<>(epicHashMap.get(key).getSubTaskIds());
            for (int id : epicSubtask) {
                subtaskHashMap.remove(id);
                historyManager.remove(id);
            }
            epicHashMap.remove(key);
            historyManager.remove(key);
        } else {
            System.out.println("Объект с таким идентификатором нет");
        }
    }

    protected void updateEpicStatus(int epicNumber) {
        Epic epic = epicHashMap.get(epicNumber);
        int doneSubtaskCalc = 0;
        int newSubtaskCalc = 0;
        for (Integer number : epic.getSubTaskIds()) {
            TaskStatus epicSubtaskStatus = subtaskHashMap.get(number).getStatus();
            if (epicSubtaskStatus.equals(TaskStatus.DONE)) {
                doneSubtaskCalc++;
            }
            if (epicSubtaskStatus.equals(TaskStatus.NEW)) {
                newSubtaskCalc++;
            }
        }
        if (doneSubtaskCalc == epic.getSubTaskIds().size()) {
            epic.setStatus(TaskStatus.DONE);
        } else if (newSubtaskCalc == epic.getSubTaskIds().size() || epic.getSubTaskIds().isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    @Override
    public List<SubTask> getEpicSubTasksList(int key) {
        List<SubTask> subTasks = new ArrayList<>();
        if (epicHashMap.containsKey(key)) {
            for (SubTask sub : subtaskHashMap.values()) {
                if (sub.getEpicId() == key) {
                    subTasks.add(sub);
                }
            }
            return subTasks;
        } else {
            System.out.println("Эпика с таким идентификатором нет,возвращен пустой список.");
            return subTasks;
        }
    }

    @Override
    public Epic getEpicHashMap(int key) {
        if (epicHashMap.containsKey(key)) {
            historyManager.add(epicHashMap.get(key));
            return epicHashMap.get(key);
        } else {
            System.out.println("Эпика с таким идентификатором нет");
            return null;
        }
    }

    @Override
    public Task getTaskHashMap(int key) {
        if (taskHashMap.containsKey(key)) {
            historyManager.add(taskHashMap.get(key));
            return taskHashMap.get(key);
        } else {
            System.out.println("Эпика с таким идентификатором нет");
            return null;
        }
    }

    @Override
    public SubTask getSubTaskHashMap(int key) {
        if (subtaskHashMap.containsKey(key)) {
            historyManager.add(subtaskHashMap.get(key));
            return subtaskHashMap.get(key);

        } else {
            System.out.println("Эпика с таким идентификатором нет");
            return null;
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
