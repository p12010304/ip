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
 * Handles task operations such as adding, deleting, marking, and searching tasks.
 */
public class TaskList {
    private List<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
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
     * Adds a task to the list.
     *
     * @param task the task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
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
        return tasks.remove(index);
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
        return tasks.get(index);
    }

    /**
     * Marks a task as done.
     *
     * @param index the 0-based index of the task
     * @throws BobException if the index is out of bounds
     */
    public void markTask(int index) throws BobException {
        Task task = getTask(index);
        task.markAsDone();
    }

    /**
     * Marks a task as not done.
     *
     * @param index the 0-based index of the task
     * @throws BobException if the index is out of bounds
     */
    public void unmarkTask(int index) throws BobException {
        Task task = getTask(index);
        task.unmarkAsDone();
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
