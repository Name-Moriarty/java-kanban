package history;

import task.Task;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private Node<Task> head;
    private Node<Task> tail;
    private final Map<Integer, Node<Task>> taskHistory = new LinkedHashMap<>();

    @Override
    public void add(Task task) {
        if (taskHistory.isEmpty()) {
            tail = new Node<>(null, task, null);
            taskHistory.put(task.getId(), tail);
            return;
        }
        if (taskHistory.containsKey(task.getId())) {
            remove(task.getId());
        }
        Node<Task> oldTail = tail;
        Node<Task> newNode = new Node<>(oldTail, task, null);
        tail = newNode;
        taskHistory.put(task.getId(), tail);
        if (oldTail == null)
            head = newNode;
        else
            oldTail.setNext(newNode);
    }

    @Override
    public List<Task> getHistory() {
        ArrayList<Task> newList = new ArrayList<>();
        for (Node<Task> taskNode : taskHistory.values()) {
            newList.add(taskNode.getData());
        }
        return newList;
    }

    @Override
    public void remove(int id) {
        if (taskHistory.containsKey(id)) {
            removeNode(taskHistory.get(id));
            taskHistory.remove(id);
        }
    }

    public void removeNode(Node<Task> node) {
        if (node.getPrev() != null) {
            node.getPrev().setNext(node.getNext());
        } else {
            head = node.getNext();
        }

        if (node.getNext() != null) {
            node.getNext().setPrev(node.getPrev());
        } else {
            tail = node.getPrev();
        }
    }

}