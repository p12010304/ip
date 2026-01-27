package bob.task;

/**
 * Represents a simple Todo task with only a description and completion status.
 * No time attributes are associated with a Todo.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo with the given description.
     *
     * @param description the todo description
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
