package bob.command;

import bob.exception.BobException;
import bob.tasklist.TaskList;
import bob.ui.Ui;
import bob.storage.Storage;

public abstract class BaseCommand {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws BobException;

    public boolean isExit() {
        return false;
    }
}
