package bob.command;

import bob.exception.BobException;
import bob.storage.Storage;
import bob.tasklist.TaskList;
import bob.ui.Ui;

/**
 * Command to exit the Bob application.
 * Displays an exit message and signals the application to terminate.
 */
public class ExitCommand extends BaseCommand {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        ui.showExit();
    }

    @Override
    public String executeForGui(TaskList tasks, Storage storage) throws BobException {
        return "Goodbye! Hope to see you again soon.";
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
