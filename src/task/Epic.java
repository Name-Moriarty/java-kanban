package task;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subTaskIds;

    public Epic(String taskName, String taskDescription) {
        super(taskName, taskDescription);
        subTaskIds = new ArrayList<>();
        status = "NEW";
    }

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void setSubTaskIds(ArrayList<Integer> subTaskIds) {
        this.subTaskIds = subTaskIds;
    }

    @Override
    public String toString() {
        return "Task.Epic{" +
                "subTaskIds=" + subTaskIds +
                ", task='" + task + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}

