package bob.command;

import java.io.IOException;

import bob.exception.BobException;
import bob.parser.Parser;
import bob.storage.Storage;
import bob.tasklist.TaskList;
import bob.ui.Ui;

/**
 * Command to mark a task as not done.
 * Parses the task index from user input and updates the task's completion status.
 */
public class UnmarkCommand extends BaseCommand {
    private String userInput;

    /**
     * Constructs an UnmarkCommand with the user input.
     * @param userInput the user input string containing the task index to mark as not done
     */
    public UnmarkCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        int idx = Parser.parseTaskIndex(userInput);
        tasks.unmarkTask(idx);
        ui.showTaskUnmarked(tasks.getTask(idx));
        saveTasks(storage, tasks, ui);
    }

    @Override
    public String executeForGui(TaskList tasks, Storage storage) throws BobException {
        int idx = Parser.parseTaskIndex(userInput);
        tasks.unmarkTask(idx);
        saveTasksQuiet(storage, tasks);
        return "Marked as not done:\n  " + tasks.getTask(idx).toString();
    }

    private void saveTasks(Storage storage, TaskList tasks, Ui ui) {
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            ui.showError("Could not save your task: " + e.getMessage());
        }
    }

    private void saveTasksQuiet(Storage storage, TaskList tasks) throws BobException {
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            throw new BobException("Could not save your task: " + e.getMessage());
        }
    }
}
