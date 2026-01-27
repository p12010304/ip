package bob.command;

import bob.exception.BobException;
import bob.tasklist.TaskList;
import bob.ui.Ui;
import bob.storage.Storage;

public class ListCommand extends BaseCommand {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        ui.showTaskList(tasks.getAllTasks());
    }
}
