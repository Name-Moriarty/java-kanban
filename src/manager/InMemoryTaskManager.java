package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import history.HistoryManager;
import task.Task;
import task.Epic;
import task.SubTask;

public class InMemoryTaskManager implements TaskManager {
    private int taskNumber = 0;

    private final Map<Integer, Task> taskHashMap;
    private final Map<Integer, Epic> epicHashMap;
    private final Map<Integer, SubTask> subtaskHashMap;

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    public InMemoryTaskManager() {
        taskHashMap = new HashMap<>();
        epicHashMap = new HashMap<>();
        subtaskHashMap = new HashMap<>();
    }

    @Override
    public int counterIncrease() {
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
    public boolean createTask(Epic epicNew) {
        Epic epic = epicNew;
        int taskNumber = counterIncrease();
        epicHashMap.put(taskNumber, epic);
        epic.setId(taskNumber);
        return true;
    }

    @Override
    public boolean createTask(SubTask subtaskNew) {
        SubTask subTask = subtaskNew;
        int taskNumber = counterIncrease();
        if (taskNumber == subTask.getEpicId()) {
            System.out.println("Подзадачу нельзя сделать свойм эпиком");
            return false;
        }
        subtaskHashMap.put(taskNumber, subTask);
        subTask.setId(taskNumber);
        List<Integer> epicSubtaskList = epicHashMap.get(subTask.getEpicId()).getSubTaskIds();
        epicSubtaskList.add(taskNumber);
        updateEpicStatus(subTask.getEpicId());
        return true;
    }

    @Override
    public boolean createTask(Task taskNew) {
        Task task = taskNew;
        int taskNumber = counterIncrease();
        taskHashMap.put(taskNumber, task);
        task.setId(taskNumber);
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
    public void subTaskUpdate(String task, String description, int subtaskId, String status) {
        if (subtaskHashMap.containsKey(subtaskId)) {
            subtaskHashMap.get(subtaskId).setTask(task);
            subtaskHashMap.get(subtaskId).setDescription(description);
            subtaskHashMap.get(subtaskId).setStatus(status);
            updateEpicStatus(subtaskHashMap.get(subtaskId).getEpicId());
        } else System.out.println("Задача с таким ид не найдена");
    }

    @Override
    public void taskUpdate(String task, String description, String status, int idTusk) {
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

    private void updateEpicStatus(int epicNumber) {
        Epic epic = epicHashMap.get(epicNumber);
        int doneSubtaskCalc = 0;
        int newSubtaskCalc = 0;
        for (Integer number : epic.getSubTaskIds()) {
            String epicSubtaskStatus = subtaskHashMap.get(number).getStatus();
            if (epicSubtaskStatus.equals("DONE")) {
                doneSubtaskCalc++;
            }
            if (epicSubtaskStatus.equals("NEW")) {
                newSubtaskCalc++;
            }
        }
        if (doneSubtaskCalc == epic.getSubTaskIds().size()) {
            epic.setStatus("DONE");
        } else if (newSubtaskCalc == epic.getSubTaskIds().size() || epic.getSubTaskIds().isEmpty()) {
            epic.setStatus("NEW");
        } else {
            epic.setStatus("IN_PROGRESS");
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
