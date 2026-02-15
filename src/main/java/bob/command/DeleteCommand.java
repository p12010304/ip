package bob.command;

import java.io.IOException;

import bob.exception.BobException;
import bob.parser.Parser;
import bob.storage.Storage;
import bob.tasklist.TaskList;
import bob.ui.Ui;

/**
 * Command to delete a task from the task list.
 * Parses the task index from user input and removes it from storage.
 */
public class DeleteCommand extends BaseCommand {
    private String userInput;

    /**
     * Constructs a DeleteCommand with the user input.
     * @param userInput the user input string containing the task index to delete
     */
    public DeleteCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        int idx = Parser.parseTaskIndex(userInput);
        bob.task.Task removedTask = tasks.deleteTask(idx);
        ui.showTaskDeleted(removedTask, tasks.getSize());
        saveTasks(storage, tasks, ui);
    }

    @Override
    public String executeForGui(TaskList tasks, Storage storage) throws BobException {
        int idx = Parser.parseTaskIndex(userInput);
        bob.task.Task removedTask = tasks.deleteTask(idx);
        saveTasksQuiet(storage, tasks);
        return "Noted. Removed this task:\n  " + removedTask.toString()
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
