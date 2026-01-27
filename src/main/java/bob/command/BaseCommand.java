package bob.command;

import bob.exception.BobException;
import bob.tasklist.TaskList;
import bob.ui.Ui;
import bob.storage.Storage;

/**
 * Abstract base class for all commands in the Bob application.
 * Defines the contract that all command implementations must follow.
 */
public abstract class BaseCommand {
    /**
     * Executes the command with the given task list, user interface, and storage.
     * Subclasses must provide their specific implementation of the command logic.
     *
     * @param tasks the task list to operate on
     * @param ui the user interface for displaying messages
     * @param storage the storage manager for persisting data
     * @throws BobException if an error occurs during command execution
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws BobException;

    /**
     * Determines whether this command signals the application to exit.
     *
     * @return true if this command is an exit command, false otherwise
     */
    public boolean isExit() {
        return false;
    }
}
