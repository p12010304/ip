package bob.command;

import bob.exception.BobException;
import bob.parser.Parser;
import bob.tasklist.TaskList;
import bob.ui.Ui;
import bob.storage.Storage;
import java.io.IOException;

/**
 * Command to delete a task from the task list.
 * Parses the task index from input and removes it from storage.
 */
public class DeleteCommand extends BaseCommand {
    private String input;

    /**
     * Constructs a DeleteCommand with the user input.
     * @param input the user input string containing the task index to delete
     */
    public DeleteCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        int idx = Parser.parseTaskIndex(input);
        bob.task.Task removedTask = tasks.deleteTask(idx);
        ui.showTaskDeleted(removedTask, tasks.getSize());
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
