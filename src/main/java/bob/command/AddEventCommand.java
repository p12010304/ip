package bob.command;

import bob.exception.BobException;
import bob.parser.Parser;
import bob.task.Task;
import bob.tasklist.TaskList;
import bob.ui.Ui;
import bob.storage.Storage;
import java.io.IOException;

/**
 * Command to add a new Event task to the task list.
 * Parses the input to create an Event task with a date range and saves it to storage.
 */
public class AddEventCommand extends BaseCommand {
    private String input;

    /**
     * Constructs an AddEventCommand with the user input.
     * @param input the full user input string containing task description, /from date, and /to date
     */
    public AddEventCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        Task task = Parser.parseAddEvent(input);
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
