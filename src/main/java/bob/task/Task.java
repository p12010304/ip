package bob.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Abstract base class representing a task with a description and completion status.
 * Subclasses (Todo, Deadline, Event) extend this class to add specific time-related properties.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with the given description.
     * By default, the task is marked as not done.
     *
     * @param description the task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon representing whether the task is done.
     *
     * @return "X" if done, " " (space) if not done
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Converts the task to a file-storable format.
     * Used for persisting tasks to storage.
     *
     * @return a string representation suitable for file storage
     */
    public String toFileString() {
        return String.format("%s | %d | %s", 
            (this instanceof Todo ? "T" : this instanceof Deadline ? "D" : "E"),
            (isDone ? 1 : 0), 
            description);
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
