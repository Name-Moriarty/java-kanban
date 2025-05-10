package task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subTaskIds;
    private LocalDateTime endTime;

    public Epic(String taskName, String taskDescription) {
        super(taskName, taskDescription);
        this.type = TaskType.EPIC;
        subTaskIds = new ArrayList<>();
        status = TaskStatus.NEW;
    }

    public List<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void removeSubTask(int id) {
        subTaskIds.removeIf(integer -> integer == id);
    }

    public void setSubTaskIds(ArrayList<Integer> subTaskIds) {
        this.subTaskIds = subTaskIds;
    }


    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "" + id + ','
                + type + ','
                + task + ','
                + status + ','
                + description;

    }
}

