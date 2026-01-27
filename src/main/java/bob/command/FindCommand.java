package bob.command;

import java.util.List;

import bob.exception.BobException;
import bob.parser.Parser;
import bob.tasklist.TaskList;
import bob.ui.Ui;
import bob.storage.Storage;

/**
 * Command to find tasks by keyword search.
 * Searches for tasks that contain the keyword in their description.
 */
public class FindCommand extends BaseCommand {
    private String input;

    /**
     * Constructs a FindCommand with the user input.
     *
     * @param input the user input string containing the keyword to search for (e.g., "find book")
     */
    public FindCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        String keyword = input.substring(4).trim();
        if (keyword.isEmpty()) {
            throw new BobException("Please provide a keyword to search for.");
        }
        List<bob.task.Task> matchingTasks = tasks.findTasksByKeyword(keyword);
        ui.showTasksFound(matchingTasks, keyword);
    }
}
