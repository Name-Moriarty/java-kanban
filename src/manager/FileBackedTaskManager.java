package manager;

import task.Epic;
import task.SubTask;
import task.Task;
import task.TaskStatus;
import task.TaskType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTaskManager() {
        super();
        this.file = new File("DataSave.csv");
    }

    public void save() {
        file.delete();
        try (Writer files = new FileWriter(file, true)) {
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
        } catch (IOException saveException) {
            throw new ManagerSaveException(saveException);
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();
        if (file.isFile()) {
            try (Reader reader = new FileReader(file);
                 BufferedReader br = new BufferedReader(reader)) {
                while (br.ready()) {
                    String line = br.readLine();
                    String[] lineSplit = line.split(",");
                    if (lineSplit.length > 1) {
                        String type = lineSplit[1];
                        if (type.equals(TaskType.TASK.toString())) {
                            Task task = new Task(lineSplit[2], lineSplit[4], TaskStatus.NEW, LocalDateTime.parse(lineSplit[5]), Duration.ofMinutes(Integer.parseInt(lineSplit[6])));
                            task.setId(Integer.parseInt(lineSplit[0]));
                            fileBackedTaskManager.taskHashMap.put(Integer.parseInt(lineSplit[0]), task);
                        } else if (type.equals(TaskType.EPIC.toString())) {
                            Epic epic = new Epic(lineSplit[2], lineSplit[4]);
                            epic.setId(Integer.parseInt(lineSplit[0]));
                            fileBackedTaskManager.epicHashMap.put(Integer.parseInt(lineSplit[0]), epic);
                        } else if (type.equals(TaskType.SUBTASK.toString())) {
                            SubTask subTask = new SubTask(lineSplit[2], lineSplit[4], Integer.parseInt(lineSplit[5]), TaskStatus.NEW, LocalDateTime.parse(lineSplit[6]), Duration.ofMinutes(Integer.parseInt(lineSplit[7])));
                            subTask.setId(Integer.parseInt(lineSplit[0]));
                            fileBackedTaskManager.subtaskHashMap.put(Integer.parseInt(lineSplit[0]), subTask);
                            List<Integer> epicSubtaskList = fileBackedTaskManager.epicHashMap.get(subTask.getEpicId()).getSubTaskIds();
                            epicSubtaskList.add(Integer.parseInt(lineSplit[0]));
                            fileBackedTaskManager.epicTimeEpdate(fileBackedTaskManager.epicHashMap.get((Integer.parseInt(lineSplit[5]))));
                        } else {
                            if (lineSplit != null) {
                                for (int i = lineSplit.length; i > 0; i--) {
                                    if (fileBackedTaskManager.epicHashMap.containsKey(i)) {
                                        fileBackedTaskManager.historyManager.add(fileBackedTaskManager.epicHashMap.get(i));
                                    }
                                    if (fileBackedTaskManager.taskHashMap.containsKey(i)) {
                                        fileBackedTaskManager.historyManager.add(fileBackedTaskManager.taskHashMap.get(i));
                                    }
                                    if (fileBackedTaskManager.subtaskHashMap.containsKey(i)) {
                                        fileBackedTaskManager.historyManager.add(fileBackedTaskManager.subtaskHashMap.get(i));
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else System.out.println("Записи в файл еще не было");
        return fileBackedTaskManager;
    }

    @Override
    public Optional<ArrayList<Task>> getPrioritizedTasks() {
        ArrayList<Task> task = new ArrayList<>(taskHashMap.values());
        ArrayList<Task> empty = new ArrayList<>();
        for (Epic epic : epicHashMap.values()) {
            if (epic.getStartTime() == null) {
                empty.add(epic);
            } else {
                task.add(epic);
            }
        }

        for (SubTask subTask : subtaskHashMap.values()) {
            task.add(subTask);
        }
        task.sort(Task::compareTo);
        for (Task task1 : empty) {
            task.add(task1);
        }
        return Optional.of(task);
    }


    @Override
    public boolean createTask(Task task) {
        boolean createStatus = super.createTask(task);
        save();
        return createStatus;
    }

    @Override
    public boolean createTask(Epic epic) {
        boolean createStatus = super.createTask(epic);
        save();
        return createStatus;
    }

    @Override
    public boolean createTask(SubTask subTask) {
        boolean createStatus = super.createTask(subTask);
        save();
        return createStatus;
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
