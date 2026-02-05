package bob.command;

import java.io.IOException;

import bob.exception.BobException;
import bob.parser.Parser;
import bob.task.Task;
import bob.tasklist.TaskList;
import bob.ui.Ui;
import bob.storage.Storage;

/**
 * Command to add a new Todo task to the task list.
 * Parses the input to create a Todo task and saves it to storage.
 */
/**
 * Command to add a new Todo task to the task list.
 * Parses the input to create a Todo task and saves it to storage.
 */
public class AddTodoCommand extends BaseCommand {
    private String input;

    /**
     * Constructs an AddTodoCommand with the user input.
     * @param input the full user input string containing the task description
     */
    public AddTodoCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        Task task = Parser.parseAddTodo(input);
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getSize());
        saveTask(storage, tasks, ui);
    }

    @Override
    public String executeForGui(TaskList tasks, Storage storage) throws BobException {
        Task task = Parser.parseAddTodo(input);
        tasks.addTask(task);
        saveTaskQuiet(storage, tasks);
        return "Got it. I've added this task:\n  " + task.toString()
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
