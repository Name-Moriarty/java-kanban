package http.handler;

import manager.TaskManager;

public class HistoryHandler extends BaseHistoryHttpHandler{

    private final TaskManager manager;

    public HistoryHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    void getHandle() {

    }
}
