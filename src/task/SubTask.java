package task;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {
    final private int epicId;

    public SubTask(String name, String description, int epicId, TaskStatus status, LocalDateTime startTieme, Duration duration) {
        super(name, description, status);
        this.type = TaskType.SUBTASK;
        this.epicId = epicId;
        this.startTime = startTieme;
        this.duration = duration;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "" + id + ','
                + type + ','
                + task + ','
                + status + ','
                + description + ','
                + epicId + ','
                + startTime + ","
                + duration.getSeconds() / 60;
    }
}
