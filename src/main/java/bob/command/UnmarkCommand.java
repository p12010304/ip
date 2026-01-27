package bob.command;

import java.io.IOException;

import bob.exception.BobException;
import bob.parser.Parser;
import bob.tasklist.TaskList;
import bob.ui.Ui;
import bob.storage.Storage;

/**
 * Command to mark a task as not done.
 * Parses the task index from input and updates the task's completion status.
 */
public class UnmarkCommand extends BaseCommand {
    private String input;

    /**
     * Constructs an UnmarkCommand with the user input.
     *
     * @param input the user input string containing the task index to mark as not done
     */
    public UnmarkCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        int idx = Parser.parseTaskIndex(input);
        tasks.unmarkTask(idx);
        ui.showTaskUnmarked(tasks.getTask(idx));
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
