package manager;

import history.HistoryManager;
import history.InMemoryHistoryManager;

import java.io.File;

public abstract class Managers {
    public static TaskManager getDefault() {
        return FileBackedTaskManager.loadFromFile(new File("DataSave.csv"));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
