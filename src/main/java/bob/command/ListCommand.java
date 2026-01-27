package bob.command;

import bob.exception.BobException;
import bob.tasklist.TaskList;
import bob.ui.Ui;
import bob.storage.Storage;

/**
 * Command to display all tasks in the task list.
 * Shows the entire list of tasks to the user.
 */
public class ListCommand extends BaseCommand {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        ui.showTaskList(tasks.getAllTasks());
    }
}
