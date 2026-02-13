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
 * Manages a collection of tasks.
 * Handles task operations such as adding, deleting, marking, and searching tasks.
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
     * Constructs a TaskList initialized with the given list of tasks.
     * Creates a copy of the input list to prevent external modifications.
     *
     * @param tasks the initial list of tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds one or more tasks to the list.
     *
     * @param tasks the task(s) to add
     */
    public void addTask(Task... tasks) {
        Arrays.stream(tasks).forEach(this.tasks::add);
    }

    /**
     * Deletes a task at the specified index.
     *
     * @param index the 0-based index of the task to delete
     * @return the deleted task
     * @throws BobException if the index is out of bounds
     */
    public Task deleteTask(int index) throws BobException {
        if (index < 0 || index >= tasks.size()) {
            throw new BobException("That task number doesn't exist in your list.");
        }
        assert index >= 0 && index < tasks.size() : "Index must be valid before deletion";
        Task deletedTask = tasks.remove(index);
        assert deletedTask != null : "Deleted task should not be null";
        return deletedTask;
    }

    /**
     * Retrieves a task at the specified index.
     *
     * @param index the 0-based index of the task
     * @return the task at the specified index
     * @throws BobException if the index is out of bounds
     */
    public Task getTask(int index) throws BobException {
        if (index < 0 || index >= tasks.size()) {
            throw new BobException("That task number doesn't exist in your list.");
        }
        assert index >= 0 && index < tasks.size() : "Index must be valid";
        Task task = tasks.get(index);
        assert task != null : "Retrieved task should not be null";
        return task;
    }

    /**
     * Marks a task as done.
     *
     * @param index the 0-based index of the task
     * @throws BobException if the index is out of bounds
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
     * @param index the 0-based index of the task
     * @throws BobException if the index is out of bounds
     */
    public void unmarkTask(int index) throws BobException {
        Task task = getTask(index);
        assert task != null : "Task must exist before unmarking";
        task.unmarkAsDone();
        assert !task.isDone() : "Task should not be marked as done after unmarkAsDone()";
    }

    /**
     * Gets a copy of all tasks in the list.
     *
     * @return a copy of the task list
     */
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Gets the number of tasks in the list.
     *
     * @return the number of tasks
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if the list contains no tasks, false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Finds tasks matching the specified date.
     * For Deadline tasks, matches tasks with the exact deadline date.
     * For Event tasks, matches tasks whose date range includes the specified date.
     * Todo tasks are not included in the results.
     *
     * @param searchDate the date to search for
     * @return a list of tasks matching the date
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
     * Finds all tasks that contain the given keyword in their description.
     *
     * @param keyword the keyword to search for
     * @return a list of tasks matching the keyword
     */
    public List<Task> findTasksByKeyword(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return tasks.stream()
                .filter(t -> t.getDescription().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    /**
     * Sorts tasks alphabetically by their description.
     * The sort is case-insensitive.
     */
    public void sortTasks() {
        tasks.sort(Comparator.comparing(task -> task.getDescription().toLowerCase()));
    }
}
