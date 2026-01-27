package bob.tasklist;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bob.exception.BobException;
import bob.task.Task;
import bob.task.Deadline;
import bob.task.Event;

/**
 * Manages a collection of tasks.
 * Provides operations to add, delete, mark, and search tasks.
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
     * Constructs a TaskList initialized with the given tasks.
     *
     * @param tasks the initial list of tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the task list.
     *
     * @param task the task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the task list by its index.
     *
     * @param index the 0-based index of the task to delete
     * @return the deleted task
     * @throws BobException if the index is out of bounds
     */
    public Task deleteTask(int index) throws BobException {
        if (index < 0 || index >= tasks.size()) {
            throw new BobException("That task number doesn't exist in your list.");
        }
        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the task list by its index.
     *
     * @param index the 0-based index of the task
     * @return the task at the specified index
     * @throws BobException if the index is out of bounds
     */
    public Task getTask(int index) throws BobException {
        if (index < 0 || index >= tasks.size()) {
            throw new BobException("That task number doesn't exist in your list.");
        }
        return tasks.get(index);
    }

    /**
     * Marks a task as done by its index.
     *
     * @param index the 0-based index of the task
     * @throws BobException if the index is out of bounds
     */
    public void markTask(int index) throws BobException {
        Task task = getTask(index);
        task.markAsDone();
    }

    /**
     * Marks a task as not done by its index.
     *
     * @param index the 0-based index of the task
     * @throws BobException if the index is out of bounds
     */
    public void unmarkTask(int index) throws BobException {
        Task task = getTask(index);
        task.unmarkAsDone();
    }

    /**
     * Returns a copy of all tasks in the list.
     *
     * @return a new list containing all tasks
     */
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Returns the number of tasks in the list.
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
     * Finds tasks on or within a specific date.
     * For Deadline tasks, exact date match is required.
     * For Event tasks, the date must fall within the event's date range.
     *
     * @param searchDate the date to search for
     * @return a list of matching tasks
     */
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
}
