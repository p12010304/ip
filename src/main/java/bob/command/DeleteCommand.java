package bob.command;

import java.io.IOException;

import bob.exception.BobException;
import bob.parser.Parser;
import bob.tasklist.TaskList;
import bob.ui.Ui;
import bob.storage.Storage;

/**
 * Command to delete a task from the task list.
 * Parses the task index from input and removes it from storage.
 */
/**
 * Command to delete a task from the task list.
 * Parses the task index from input and removes it from storage.
 */
public class DeleteCommand extends BaseCommand {
    private String input;

    /**
     * Constructs a DeleteCommand with the user input.
     * @param input the user input string containing the task index to delete
     */
    public DeleteCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        int idx = Parser.parseTaskIndex(input);
        bob.task.Task removedTask = tasks.deleteTask(idx);
        ui.showTaskDeleted(removedTask, tasks.getSize());
        saveTask(storage, tasks, ui);
    }

    @Override
    public String executeForGui(TaskList tasks, Storage storage) throws BobException {
        int idx = Parser.parseTaskIndex(input);
        bob.task.Task removedTask = tasks.deleteTask(idx);
        saveTaskQuiet(storage, tasks);
        return "Noted. I've removed this task:\n  " + removedTask.toString()
                + "\nNow you have " + tasks.getSize() + " tasks in the list.";
    }

    private void saveTask(Storage storage, TaskList tasks, Ui ui) {
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            ui.showError("Error saving task: " + e.getMessage());
        }
    }

    private void saveTaskQuiet(Storage storage, TaskList tasks) throws BobException {
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            throw new BobException("Error saving task: " + e.getMessage());
        }
    }
}
