package bob.command;

import bob.exception.BobException;
import bob.storage.Storage;
import bob.tasklist.TaskList;
import bob.ui.Ui;

/**
 * Abstract base class for all commands in the Bob application.
 * Defines the contract that all command implementations must follow.
 */
public abstract class BaseCommand {
    /**
     * Executes the command with the given task list, user interface, and storage.
     * Subclasses must provide their specific implementation of the command logic.
     * @param tasks the task list to operate on
     * @param ui the user interface for displaying messages
     * @param storage the storage manager for persisting data
     * @throws BobException if an error occurs during command execution
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws BobException;

    /**
     * Executes the command for GUI mode and returns the response as a string.
     * Default implementation delegates to execute() and returns a generic message.
     * @param tasks the task list to operate on
     * @param storage the storage manager for persisting data
     * @return the response message to display in the GUI
     * @throws BobException if an error occurs during command execution
     */
    public String executeForGui(TaskList tasks, Storage storage) throws BobException {
        return "Command executed successfully.";
    }

    /**
     * Determines whether this command signals the application to exit.
     * @return true if this command is an exit command, false otherwise
     */
    public boolean isExit() {
        return false;
    }
}
