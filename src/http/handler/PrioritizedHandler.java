package http.handler;

import manager.TaskManager;

public class PrioritizedHandler extends BaseHistoryHttpHandler {
    private final TaskManager manager;

    public PrioritizedHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    void getHandle() {

    }
}
