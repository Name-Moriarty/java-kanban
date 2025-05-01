package task;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subTaskIds;
    private LocalDateTime endTime;

    public Epic(String taskName, String taskDescription) {
        super(taskName, taskDescription);
        this.type = TaskType.EPIC;
        subTaskIds = new ArrayList<>();
        status = TaskStatus.NEW;
    }

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void removeSubTask(int id) {
        for (int i = 0; i < subTaskIds.size(); i++) {
            if (id == subTaskIds.get(i)) {
                subTaskIds.remove(i);
            }
        }
    }

    public void setSubTaskIds(ArrayList<Integer> subTaskIds) {
        this.subTaskIds = subTaskIds;
    }

    @Override
    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration.getSeconds() / 60);
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
                + description
                ;

    }
}

