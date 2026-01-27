package bob.command;

import bob.exception.BobException;
import bob.parser.Parser;
import bob.task.Task;
import bob.tasklist.TaskList;
import bob.ui.Ui;
import bob.storage.Storage;
import java.io.IOException;

/**
 * Command to add a new Deadline task to the task list.
 * Parses the input to create a Deadline task with a due date and saves it to storage.
 */
public class AddDeadlineCommand extends BaseCommand {
    private String input;

    /**
     * Constructs an AddDeadlineCommand with the user input.
     * @param input the full user input string containing task description and /by date
     */
    public AddDeadlineCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        Task task = Parser.parseAddDeadline(input);
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getSize());
        saveTask(storage, tasks, ui);
    }

    private void saveTask(Storage storage, TaskList tasks, Ui ui) {
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            ui.showError("Error saving task: " + e.getMessage());
        }
    }
}
