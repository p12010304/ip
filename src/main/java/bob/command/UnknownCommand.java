package bob.command;

import bob.exception.BobException;
import bob.tasklist.TaskList;
import bob.ui.Ui;
import bob.storage.Storage;

/**
 * Command for unrecognized user input.
 * Throws an exception with a friendly error message when an unknown command is received.
 */
public class UnknownCommand extends BaseCommand {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        throw new BobException("I'm sorry, but I don't know what that means :-(");
    }
}
