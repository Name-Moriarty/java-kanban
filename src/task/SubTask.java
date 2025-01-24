package task;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String name, String description, int epicId, String status) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Task.Subtask{" +
                "epicId=" + epicId +
                ", task='" + task + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
