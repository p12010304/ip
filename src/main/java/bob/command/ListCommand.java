package bob.command;

import bob.exception.BobException;
import bob.storage.Storage;
import bob.tasklist.TaskList;
import bob.ui.Ui;

/**
 * Command to display all tasks in the task list.
 * Shows the entire list of tasks to the user.
 */
public class ListCommand extends BaseCommand {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        ui.showTaskList(tasks.getAllTasks());
    }

    @Override
    public String executeForGui(TaskList tasks, Storage storage) throws BobException {
        if (tasks.getSize() == 0) {
            return "🎉 Woohoo! Your task list is empty!\nTime to relax or add some new goals! 😎";
        }
        StringBuilder sb = new StringBuilder("📝 Here's what's on your plate:\n");
        for (int i = 0; i < tasks.getSize(); i++) {
            sb.append((i + 1)).append(".").append(tasks.getTask(i).toString()).append("\n");
        }
        return sb.toString().trim();
    }
}
