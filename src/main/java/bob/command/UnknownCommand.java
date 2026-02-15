package bob.command;

import bob.exception.BobException;
import bob.storage.Storage;
import bob.tasklist.TaskList;
import bob.ui.Ui;

/**
 * Command for unrecognized user input.
 * Throws an exception with an error message when an unknown command is received.
 */
public class UnknownCommand extends BaseCommand {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        throw new BobException("Unknown command.\n"
                + "Available commands: list, todo, deadline, event, mark, unmark, delete, find, sort, bye");
    }

    @Override
    public String executeForGui(TaskList tasks, Storage storage) throws BobException {
        throw new BobException("Unknown command.\n"
                + "Available commands: list, todo, deadline, event, mark, unmark, delete, find, sort, bye");
    }
}
