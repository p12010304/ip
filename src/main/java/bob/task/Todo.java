package bob.task;

/**
 * Represents a simple todo task with just a description.
 * A todo is a basic task without any associated date or time.
 */
public class Todo extends Task {
    /**
     * Constructs a new Todo with the given description.
     *
     * @param description a brief description of the todo
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of this todo.
     * Format: [T][Status] Description
     *
     * @return the todo string representation
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
