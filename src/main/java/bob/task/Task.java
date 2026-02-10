package bob.task;

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
     * Checks if this task is marked as done.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        assert !this.isDone : "Task should not already be marked as done";
        this.isDone = true;
        assert this.isDone : "Task must be marked as done after markAsDone()";
    }

    /**
     * Marks this task as not done.
     */
    public void unmarkAsDone() {
        assert this.isDone : "Task should be marked as done before unmarking";
        this.isDone = false;
        assert !this.isDone : "Task must not be marked as done after unmarkAsDone()";
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
        String type = this instanceof Todo ? "T" : this instanceof Deadline ? "D" : "E";
        int status = isDone ? 1 : 0;
        return String.format("%s | %d | %s", type, status, description);
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
