package bob.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Represents a base task in the Bob application.
 * This is an abstract base class for different types of tasks (Todo, Deadline, Event).
 * Handles common task properties like description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with the given description.
     * The task is initially marked as not done.
     *
     * @param description a brief description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Gets the status icon representing whether this task is done.
     * Returns "X" if the task is done, or a space " " if not done.
     *
     * @return the status icon
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks this task as done.
     */
    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    /**
     * Marks this task as not done.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Returns the file format string representation of this task.
     * Format: [Type] | [Status] | [Description]
     * Type: T=Todo, D=Deadline, E=Event
     * Status: 0=not done, 1=done
     *
     * @return the file format string
     */
    public String toFileString() {
        return String.format("%s | %d | %s", 
            (this instanceof Todo ? "T" : this instanceof Deadline ? "D" : "E"),
            (isDone ? 1 : 0), 
            description);
    }

    /**
     * Returns the description of this task.
     *
     * @return the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a string representation of this task for display.
     * Format: [Status] Description
     *
     * @return the display string
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
