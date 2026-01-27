package bob.tasklist;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bob.exception.BobException;
import bob.task.Task;
import bob.task.Deadline;
import bob.task.Event;

public class TaskList {
    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int index) throws BobException {
        if (index < 0 || index >= tasks.size()) {
            throw new BobException("That task number doesn't exist in your list.");
        }
        return tasks.remove(index);
    }

    public Task getTask(int index) throws BobException {
        if (index < 0 || index >= tasks.size()) {
            throw new BobException("That task number doesn't exist in your list.");
        }
        return tasks.get(index);
    }

    public void markTask(int index) throws BobException {
        Task task = getTask(index);
        task.markAsDone();
    }

    public void unmarkTask(int index) throws BobException {
        Task task = getTask(index);
        task.unmarkAsDone();
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public int getSize() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public List<Task> findTasksByDate(LocalDate searchDate) {
        List<Task> matchingTasks = new ArrayList<>();
        for (Task t : tasks) {
            if (t instanceof Deadline) {
                Deadline d = (Deadline) t;
                if (d.getDate().equals(searchDate)) {
                    matchingTasks.add(t);
                }
            } else if (t instanceof Event) {
                Event e = (Event) t;
                if (!e.getFromDate().isAfter(searchDate) && !e.getToDate().isBefore(searchDate)) {
                    matchingTasks.add(t);
                }
            }
        }
        return matchingTasks;
    }

    /**
     * Finds all tasks that contain the given keyword in their description.
     *
     * @param keyword the keyword to search for
     * @return a list of tasks matching the keyword
     */
    public List<Task> findTasksByKeyword(String keyword) {
        List<Task> matchingTasks = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(lowerKeyword)) {
                matchingTasks.add(t);
            }
        }
        return matchingTasks;
    }
}
