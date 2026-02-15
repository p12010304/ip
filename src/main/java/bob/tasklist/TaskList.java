package bob.tasklist;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import bob.exception.BobException;
import bob.task.Deadline;
import bob.task.Event;
import bob.task.Task;

/**
 * Collection of tasks.
 */
public class TaskList {
    private List<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with the given tasks.
     *
     * @param tasks the initial list of tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds tasks to the list.
     *
     * @param tasks the task(s) to add
     */
    public void addTask(Task... tasks) {
        Arrays.stream(tasks).forEach(this.tasks::add);
    }

    /**
     * Deletes a task at the specified index.
     *
     * @param index the index of the task to delete
     * @return the deleted task
     * @throws BobException if index is invalid
     */
    public Task deleteTask(int index) throws BobException {
        if (index < 0 || index >= tasks.size()) {
            throw new BobException("That task number doesn't exist in your list. "
                    + "You have " + tasks.size() + " task(s).");
        }
        assert index >= 0 && index < tasks.size() : "Index must be valid before deletion";
        Task deletedTask = tasks.remove(index);
        assert deletedTask != null : "Deleted task should not be null";
        return deletedTask;
    }

    /**
     * Gets a task at the specified index.
     *
     * @param index the index of the task
     * @return the task
     * @throws BobException if index is invalid
     */
    public Task getTask(int index) throws BobException {
        if (index < 0 || index >= tasks.size()) {
            throw new BobException("That task number doesn't exist in your list. "
                    + "You have " + tasks.size() + " task(s).");
        }
        assert index >= 0 && index < tasks.size() : "Index must be valid";
        Task task = tasks.get(index);
        assert task != null : "Retrieved task should not be null";
        return task;
    }

    /**
     * Marks a task as done.
     *
     * @param index the index of the task
     * @throws BobException if index is invalid
     */
    public void markTask(int index) throws BobException {
        Task task = getTask(index);
        assert task != null : "Task must exist before marking";
        task.markAsDone();
        assert task.isDone() : "Task should be marked as done after markAsDone()";
    }

    /**
     * Marks a task as not done.
     *
     * @param index the index of the task
     * @throws BobException if index is invalid
     */
    public void unmarkTask(int index) throws BobException {
        Task task = getTask(index);
        assert task != null : "Task must exist before unmarking";
        task.unmarkAsDone();
        assert !task.isDone() : "Task should not be marked as done after unmarkAsDone()";
    }

    /**
     * Gets a copy of all tasks.
     *
     * @return a copy of the task list
     */
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Gets the number of tasks.
     *
     * @return the number of tasks
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Finds tasks matching the specified date.
     *
     * @param searchDate the date to search for
     * @return matching tasks
     */
    public List<Task> findTasksByDate(LocalDate searchDate) {
        return tasks.stream()
                .filter(t -> {
                    if (t instanceof Deadline) {
                        Deadline d = (Deadline) t;
                        return d.getDate().equals(searchDate);
                    } else if (t instanceof Event) {
                        Event e = (Event) t;
                        return !e.getFromDate().isAfter(searchDate) && !e.getToDate().isBefore(searchDate);
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    /**
     * Finds tasks containing the keyword.
     *
     * @param keyword the keyword to search for
     * @return matching tasks
     */
    public List<Task> findTasksByKeyword(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return tasks.stream()
                .filter(t -> t.getDescription().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    /**
     * Sorts tasks alphabetically.
     */
    public void sortTasks() {
        tasks.sort(Comparator.comparing(task -> task.getDescription().toLowerCase()));
    }
}
