package bob.command;

import java.io.IOException;

import bob.exception.BobException;
import bob.storage.Storage;
import bob.tasklist.TaskList;
import bob.ui.Ui;

/**
 * Command to sort tasks in the task list.
 * Sorts tasks alphabetically by description.
 */
public class SortCommand extends BaseCommand {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        tasks.sortTasks();
        ui.showSortedMessage();
        ui.showTaskList(tasks.getAllTasks());
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            throw new BobException("Error saving sorted tasks: " + e.getMessage());
        }
    }

    @Override
    public String executeForGui(TaskList tasks, Storage storage) throws BobException {
        tasks.sortTasks();
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            throw new BobException("Error saving sorted tasks: " + e.getMessage());
        }
        if (tasks.getSize() == 0) {
            return "🤷 You have no tasks to sort yet!\nAdd some first? 😊";
        }
        StringBuilder sb = new StringBuilder("🎯 Done! Your tasks are now sorted:\n");
        for (int i = 0; i < tasks.getSize(); i++) {
            sb.append((i + 1)).append(".").append(tasks.getTask(i).toString()).append("\n");
        }
        sb.append("\nMuch more organized now! 📚✨");
        return sb.toString().trim();
    }
}
