package bob.command;

import bob.exception.BobException;
import bob.parser.Parser;
import bob.tasklist.TaskList;
import bob.ui.Ui;
import bob.storage.Storage;
import java.time.LocalDate;
import java.util.List;

public class FindCommand extends BaseCommand {
    private String input;

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
