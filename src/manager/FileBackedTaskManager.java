package manager;

import task.*;

import java.io.*;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

    @Override
    public void save() {
        try {
            File file = new File("DataSave.csv");
            file.delete();
            Writer files = new FileWriter("DataSave.csv", true);
            for (Task task : taskHashMap.values()) {
                String name = task.toString();
                files.write(name + "\n");
            }

            for (Epic epic : epicHashMap.values()) {
                String name = epic.toString();
                files.write(name + "\n");
            }

            for (SubTask subTask : subtaskHashMap.values()) {
                String name = subTask.toString();
                files.write(name + "\n");
            }
            files.write(" \n");
            List<Task> copy = getHistory();
            int i = 0;
            if (copy != null) {
                for (Task task : copy) {
                    i++;
                    if (copy.size() == i) {
                        String str = String.valueOf(task.getId());
                        files.write(str);
                    } else {
                        String str = String.valueOf(task.getId());
                        files.write(str + ",");
                    }
                }
            }
            files.close();
        } catch (IOException exp) {
            throw new ManagerSaveException(exp);
        }
    }

    @Override
    public void reader() {
        File file = new File("C:\\Users\\dvoeg\\IdeaProjects\\java-kanban-7-sprint", "DataSave.csv");
        if (file.isFile()) {
            try {
                Reader reader = new FileReader("DataSave.csv");
                BufferedReader br = new BufferedReader(reader);
                while (br.ready()) {
                    String line = br.readLine();
                    String one [] = line.split(",");
                    if (one.length > 1) {
                        String type = one[1];
                        if (type.equals("TASK")) {
                            Task task = new Task(one[2], one[4], TaskStatus.NEW);
                            task.setId(Integer.parseInt(one[0]));
                            createTaskReadersFiles(task);
                        } else if (type.equals(TaskType.EPIC.toString())) {
                            Epic epic = new Epic(one[2], one[4]);
                            epic.setId(Integer.parseInt(one[0]));
                            createTaskReadersFiles(epic);
                        } else if (type.equals(TaskType.SUBTASK.toString())) {
                            SubTask subTask = new SubTask(one[2], one[4], Integer.parseInt(one[5]), TaskStatus.NEW);
                            subTask.setId(Integer.parseInt(one[0]));
                            createTaskReadersFiles(subTask);
                        } else {
                            if (one != null) {
                                for (int i = one.length; i > 0; i--) {
                                    if (epicHashMap.containsKey(i)) {
                                        getEpicHashMap(i);
                                    }
                                    if (taskHashMap.containsKey(i)) {
                                        getTaskHashMap(i);
                                    }
                                    if (subtaskHashMap.containsKey(i)) {
                                        getSubTaskHashMap(i);
                                    }
                                }
                            }
                        }
                    }
                }
                br.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else System.out.println("Записи в файл еще не было");
    }

    @Override
    public boolean createTask(Task task) {
        super.createTask(task);
        save();
        return true;
    }

    @Override
    public boolean createTask(Epic epic) {
        boolean result = super.createTask(epic);
        save();
        return result;
    }

    @Override
    public boolean createTask(SubTask subTask) {
        boolean createStatus = super.createTask(subTask);
        save();
        return createStatus;
    }

    public boolean createTaskReadersFiles(Task task) {
        taskHashMap.put(task.getId(), task);
        counterIncrease();
        return true;
    }

    public boolean createTaskReadersFiles(Epic epic) {
        epicHashMap.put(epic.getId(), epic);
        counterIncrease();
        return true;
    }

    public boolean createTaskReadersFiles(SubTask subtask) {
        subtaskHashMap.put(subtask.getId(), subtask);
        List<Integer> epicSubtaskList = epicHashMap.get(subtask.getEpicId()).getSubTaskIds();
        epicSubtaskList.add(subtask.getId());
        super.updateEpicStatus(subtask.getEpicId());
        return true;
    }

    @Override
    public Epic getEpicHashMap(int key) {
        Epic epic = super.getEpicHashMap(key);
        save();
        return epic;
    }

    @Override
    public Task getTaskHashMap(int key) {
        Task task = super.getTaskHashMap(key);
        save();
        return task;
    }

    @Override
    public SubTask getSubTaskHashMap(int key) {
        SubTask subTask = super.getSubTaskHashMap(key);
        save();
        return subTask;
    }

    @Override
    public void epicUpdate(String task, String taskDescription, int taskNumber) {
        super.epicUpdate(task, taskDescription, taskNumber);
        save();
    }

    @Override
    public void subTaskUpdate(String task, String description, int subtaskId, TaskStatus status) {
        super.subTaskUpdate(task, description, subtaskId, status);
        save();
    }

    @Override
    public void taskUpdate(String task, String description, TaskStatus status, int idTusk) {
        super.taskUpdate(task, description, status, idTusk);
        save();
    }
}
