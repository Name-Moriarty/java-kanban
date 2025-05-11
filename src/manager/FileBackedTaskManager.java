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
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        super();
        this.file = file;
    }

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
            if (!getHistory().isEmpty()) {
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
            }
        } catch (IOException saveException) {
            throw new ManagerSaveException(saveException);
        }
    }

    public static FileBackedTaskManager loadFromFile() {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();
        if (fileBackedTaskManager.file.isFile()) {
            try (Reader reader = new FileReader(fileBackedTaskManager.file);
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
                            fileBackedTaskManager.listPrioritizedTasks.add(task);
                            fileBackedTaskManager.counterIncrease();
                        } else if (type.equals(TaskType.EPIC.toString())) {
                            Epic epic = new Epic(lineSplit[2], lineSplit[4]);
                            epic.setId(Integer.parseInt(lineSplit[0]));
                            fileBackedTaskManager.epicHashMap.put(Integer.parseInt(lineSplit[0]), epic);
                            fileBackedTaskManager.counterIncrease();
                        } else if (type.equals(TaskType.SUBTASK.toString())) {
                            SubTask subTask = new SubTask(lineSplit[2], lineSplit[4], Integer.parseInt(lineSplit[5]), TaskStatus.NEW, LocalDateTime.parse(lineSplit[6]), Duration.ofMinutes(Integer.parseInt(lineSplit[7])));
                            subTask.setId(Integer.parseInt(lineSplit[0]));
                            fileBackedTaskManager.subtaskHashMap.put(Integer.parseInt(lineSplit[0]), subTask);
                            List<Integer> epicSubtaskList = fileBackedTaskManager.epicHashMap.get(subTask.getEpicId()).getSubTaskIds();
                            epicSubtaskList.add(Integer.parseInt(lineSplit[0]));
                            fileBackedTaskManager.epicTimeEpdate(fileBackedTaskManager.epicHashMap.get((Integer.parseInt(lineSplit[5]))));
                            fileBackedTaskManager.listPrioritizedTasks.add(subTask);
                            fileBackedTaskManager.counterIncrease();
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
    public boolean createSubtask(SubTask subTask) {
        boolean createStatus = super.createSubtask(subTask);
        save();
        return createStatus;
    }

    @Override
    public Epic getEpic(int key) {
        Epic epic = super.getEpic(key);
        save();
        return epic;
    }

    @Override
    public Task getTask(int key) {
        Task task = super.getTask(key);
        save();
        return task;
    }

    @Override
    public SubTask getSubtask(int key) {
        SubTask subTask = super.getSubtask(key);
        save();
        return subTask;
    }

    @Override
    public boolean epicUpdate(Epic epic) {
        boolean isUpdate = super.epicUpdate(epic);
        save();
        return isUpdate;
    }

    @Override
    public boolean subTaskUpdate(SubTask subTask) {
        boolean isUpdate = super.subTaskUpdate(subTask);
        save();
        return isUpdate;
    }

    @Override
    public boolean taskUpdate(Task task) {
        boolean isUpdate = super.taskUpdate(task);
        save();
        return isUpdate;
    }
}
