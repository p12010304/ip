package bob.command;

import bob.exception.BobException;
import bob.tasklist.TaskList;
import bob.ui.Ui;
import bob.storage.Storage;

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
    public boolean isExit() {
        return true;
    }
}
