package bob.command;

import java.time.LocalDate;
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
    private String userInput;

    /**
     * Constructs a FindCommand with the user input.
     * @param userInput the user input string containing the keyword to search for (e.g., "find book")
     */
    public FindCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        String keyword = userInput.substring(4).trim();
        if (keyword.isEmpty()) {
            throw new BobException("Please provide a keyword to search for.");
        }
        List<bob.task.Task> matchingTasks = tasks.findTasksByKeyword(keyword);
        ui.showTasksFound(matchingTasks, keyword);
    }

    @Override
    public String executeForGui(TaskList tasks, Storage storage) throws BobException {
        String keyword = userInput.substring(4).trim();
        if (keyword.isEmpty()) {
            throw new BobException("Please provide a keyword to search for.");
        }
        List<bob.task.Task> matchingTasks = tasks.findTasksByKeyword(keyword);
        if (matchingTasks.isEmpty()) {
            return "No matching tasks found.";
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            sb.append((i + 1)).append(".").append(matchingTasks.get(i).toString()).append("\n");
        }
        return sb.toString().trim();
    }
}
