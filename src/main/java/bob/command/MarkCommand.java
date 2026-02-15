package bob.command;

import java.io.IOException;

import bob.exception.BobException;
import bob.parser.Parser;
import bob.storage.Storage;
import bob.tasklist.TaskList;
import bob.ui.Ui;

/**
 * Command to mark a task as done.
 * Parses the task index from user input and updates the task's completion status.
 */
public class MarkCommand extends BaseCommand {
    private String userInput;

    /**
     * Constructs a MarkCommand with the user input.
     * @param userInput the user input string containing the task index to mark as done
     */
    public MarkCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        int idx = Parser.parseTaskIndex(userInput);
        tasks.markTask(idx);
        ui.showTaskMarked(tasks.getTask(idx));
        saveTasks(storage, tasks, ui);
    }

    @Override
    public String executeForGui(TaskList tasks, Storage storage) throws BobException {
        int idx = Parser.parseTaskIndex(userInput);
        tasks.markTask(idx);
        saveTasksQuiet(storage, tasks);
        return "Nice! Marked this task as done:\n  " + tasks.getTask(idx).toString();
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
