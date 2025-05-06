package manager;

import history.HistoryManager;
import task.Epic;
import task.SubTask;
import task.Task;
import task.TaskStatus;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    private int taskNumber = 0;

    protected final Map<Integer, Task> taskHashMap = new HashMap<>();
    protected final Map<Integer, Epic> epicHashMap = new HashMap<>();
    protected final Map<Integer, SubTask> subtaskHashMap = new HashMap<>();
    protected TreeSet<Task> listPrioritizedTasks = new TreeSet<>(Task::compareTo);
    protected final HistoryManager historyManager = Managers.getDefaultHistory();

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
    public boolean createTask(Epic epic) {
        int taskNumber = counterIncrease();
        epic.setId(taskNumber);
        epicHashMap.put(taskNumber, epic);
        return true;
    }

    @Override
    public boolean createSubtask(SubTask subTask) {
        if (checkTaskDatesInstruction(subTask)) {
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
            if (subTask.getStartTime() != null) {
                epicTimeEpdate(epicHashMap.get(subTask.getEpicId()));
                listPrioritizedTasks.add(subTask);
            }
            return true;
        }
        System.out.println("Даты не должны пересекаться, сабтакс не получилось создать.");
        return false;
    }

    @Override
    public boolean createTask(Task task) {
        if (checkTaskDatesInstruction(task)) {
            int taskNumber = counterIncrease();
            task.setId(taskNumber);
            taskHashMap.put(taskNumber, task);
            if (task.getStartTime() != null) {
                listPrioritizedTasks.add(task);
            }
            return true;
        }
        System.out.println("Даты не должны пересекаться, такс не получилось создать.");
        return false;
    }

    @Override
    public void epicUpdate(Epic epic) {
        if (epicHashMap.containsKey(epic.getId())) {
            epicHashMap.put(epic.getId(), epic);
        } else System.out.println("Задача с таким ид не найдена");
    }

    @Override
    public void subTaskUpdate(SubTask subTask) {
        if (subtaskHashMap.containsKey(subTask.getId()) && subtaskHashMap.get(subTask.getId()).checkingTheIdentityOfDates(subTask) || checkTaskDatesInstruction(subTask)) {
            listPrioritizedTasks.remove(subtaskHashMap.get(subTask.getId()));
            subtaskHashMap.put(subTask.getId(), subTask);
            updateEpicStatus(subTask.getEpicId());
            epicTimeEpdate(epicHashMap.get(subTask.getEpicId()));
            listPrioritizedTasks.add(subTask);
        } else System.out.println("Подзадача с таким ид не найдена или пересекается по времени с другими задачами");
    }

    @Override
    public void taskUpdate(Task task) {
        if (taskHashMap.containsKey(task.getId()) && checkTaskDatesInstruction(task)) {
            listPrioritizedTasks.remove(taskHashMap.get(task.getId()));
            taskHashMap.put(task.getId(), task);
            listPrioritizedTasks.add(task);
        } else System.out.println("Задача с таким ид не найдена или пересекается по времени с другими задачами");
    }

    @Override
    public void taskDelete(int key) {
        boolean fullTask = taskHashMap.containsKey(key);
        boolean fullEpic = epicHashMap.containsKey(key);
        boolean fullSubtask = subtaskHashMap.containsKey(key);
        if (fullTask) {
            historyManager.remove(key);
            listPrioritizedTasks.remove(taskHashMap.get(key));
            taskHashMap.remove(key);
            System.out.println("Объект  удален");
        } else if (fullSubtask) {
            int epicId = subtaskHashMap.get(key).getEpicId();
            epicHashMap.get(epicId).removeSubTask(key);
            historyManager.remove(key);
            listPrioritizedTasks.remove(subtaskHashMap.get(key));
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
    public Epic getEpic(int key) {
        if (epicHashMap.containsKey(key)) {
            historyManager.add(epicHashMap.get(key));
            return epicHashMap.get(key);
        } else {
            System.out.println("Эпика с таким идентификатором нет");
            return null;
        }
    }

    @Override
    public Task getTask(int key) {
        if (taskHashMap.containsKey(key)) {
            historyManager.add(taskHashMap.get(key));
            return taskHashMap.get(key);
        } else {
            System.out.println("Эпика с таким идентификатором нет");
            return null;
        }
    }

    @Override
    public SubTask getSubtask(int key) {
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

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return listPrioritizedTasks;
    }

    protected void epicTimeEpdate(Epic epic) {
        List<SubTask> subTasks = getListSubtask().stream()
                .filter((subTask) -> epic.getSubTaskIds().contains(subTask.getId()))
                .sorted(Task::compareTo)
                .collect(Collectors.toList());
        if (!subTasks.isEmpty()) {
            epic.setStartTime(subTasks.get(0).getStartTime());
            epic.setDuration(Duration.between(subTasks.get(0).getStartTime(), subTasks.get(subTasks.size() - 1).getEndTime()));
            epic.setEndTime(epic.getStartTime().plus(epic.getDuration()));
        }
    }

    private boolean checkTaskDatesInstruction(Task task) {
        boolean taskCheck = getListTask().stream().allMatch(task1 -> task1.taskCheckTime(task));
        Boolean subTaskCheck = getListSubtask().stream().allMatch(task1 -> task1.taskCheckTime(task));
        return taskCheck && subTaskCheck;
    }
}
