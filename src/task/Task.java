package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Comparable<Task> {

    protected String task;

    protected String description;

    protected TaskStatus status;
    protected int id;
    protected TaskType type;
    protected Duration duration;
    protected LocalDateTime startTime;

    public Task(String task, String description, TaskStatus status, LocalDateTime startTime, Duration duration) {
        this.task = task;
        this.description = description;
        this.status = status;
        this.type = TaskType.TASK;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(String task, String description, TaskStatus status) {
        this.task = task;
        this.description = description;
        this.status = status;
        this.type = TaskType.TASK;
    }

    public Task(String task, String description) {
        this.task = task;
        this.description = description;
        this.type = TaskType.TASK;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration.getSeconds() / 60);
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public int compareTo(Task task) {
        return startTime.compareTo(task.startTime);
    }

    @Override
    public String toString() {
        return "" + id + ','
                + type + ','
                + task + ','
                + status + ','
                + description + ','
                + startTime + ","
                + duration.getSeconds() / 60;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

