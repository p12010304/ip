package bob.command;

import java.io.IOException;

import bob.exception.BobException;
import bob.parser.Parser;
import bob.storage.Storage;
import bob.task.Task;
import bob.tasklist.TaskList;
import bob.ui.Ui;

/**
 * Command to add a new Deadline task to the task list.
 * Parses the user input to create a Deadline task with a due date and saves it to storage.
 */
public class AddDeadlineCommand extends BaseCommand {
    private String userInput;

    /**
     * Constructs an AddDeadlineCommand with the user input.
     * @param userInput the full user input string containing task description and /by date
     */
    public AddDeadlineCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        Task task = Parser.parseAddDeadline(userInput);
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getSize());
        saveTasks(storage, tasks, ui);
    }

    @Override
    public String executeForGui(TaskList tasks, Storage storage) throws BobException {
        Task task = Parser.parseAddDeadline(userInput);
        tasks.addTask(task);
        saveTasksQuiet(storage, tasks);
        return "Got it! Added this task:\n  " + task.toString()
                + "\nYou now have " + tasks.getSize() + " task(s) in the list.";
    }

    /**
     * Saves the task list to storage and displays an error message if the save fails.
     *
     * @param storage the storage manager to save tasks to
     * @param tasks the task list to save
     * @param ui the user interface for displaying error messages
     */
    private void saveTasks(Storage storage, TaskList tasks, Ui ui) {
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            ui.showError("Could not save your task: " + e.getMessage());
        }
    }

    /**
     * Saves the task list to storage and throws an exception if the save fails.
     * Used by GUI mode to propagate errors.
     *
     * @param storage the storage manager to save tasks to
     * @param tasks the task list to save
     * @throws BobException if an I/O error occurs during save
     */
    private void saveTasksQuiet(Storage storage, TaskList tasks) throws BobException {
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            throw new BobException("Could not save your task: " + e.getMessage());
        }
    }
}
