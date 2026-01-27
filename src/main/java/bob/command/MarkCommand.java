package bob.command;

import bob.exception.BobException;
import bob.parser.Parser;
import bob.tasklist.TaskList;
import bob.ui.Ui;
import bob.storage.Storage;
import java.io.IOException;

/**
 * Command to mark a task as done.
 * Parses the task index from input and updates the task's completion status.
 */
public class MarkCommand extends BaseCommand {
    private String input;

    /**
     * Constructs a MarkCommand with the user input.
     * @param input the user input string containing the task index to mark as done
     */
    public MarkCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        int idx = Parser.parseTaskIndex(input);
        tasks.markTask(idx);
        ui.showTaskMarked(tasks.getTask(idx));
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
