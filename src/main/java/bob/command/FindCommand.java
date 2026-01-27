package bob.command;

import java.time.LocalDate;
import java.util.List;

import bob.exception.BobException;
import bob.parser.Parser;
import bob.tasklist.TaskList;
import bob.ui.Ui;
import bob.storage.Storage;

/**
 * Command to find tasks on a specific date.
 * Searches for Deadline tasks with exact date match and Event tasks that contain the date.
 */
/**
 * Command to find tasks on a specific date.
 * Searches for Deadline tasks with exact date match and Event tasks that contain the date.
 */
public class FindCommand extends BaseCommand {
    private String input;

    /**
     * Constructs a FindCommand with the user input.
     * @param input the user input string containing the date to search for (format: yyyy-MM-dd)
     */
    public FindCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        String dateStr = input.substring(5).trim();
        LocalDate searchDate = Parser.parseDate(dateStr);
        List<bob.task.Task> matchingTasks = tasks.findTasksByDate(searchDate);
        ui.showTasksOnDate(matchingTasks, dateStr);
    }
}
