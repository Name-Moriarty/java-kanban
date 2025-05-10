package manager;

import history.HistoryManager;
import history.InMemoryHistoryManager;

public class Managers {

    private Managers() {

    }

    public static TaskManager getDefault() {
        return FileBackedTaskManager.loadFromFile();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getHttpDefault() {
        return new InMemoryTaskManager();
    }
}
