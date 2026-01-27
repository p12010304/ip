package bob.command;

import bob.exception.BobException;
import bob.parser.Parser;
import bob.tasklist.TaskList;
import bob.ui.Ui;
import bob.storage.Storage;
import java.io.IOException;

public class UnmarkCommand extends BaseCommand {
    private String input;

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
