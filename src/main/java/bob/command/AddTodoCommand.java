package bob.command;

import java.io.IOException;

import bob.exception.BobException;
import bob.parser.Parser;
import bob.storage.Storage;
import bob.task.Task;
import bob.tasklist.TaskList;
import bob.ui.Ui;

/**
 * Command to add a new Todo task to the task list.
 * Parses the user input to create a Todo task and saves it to storage.
 */
public class AddTodoCommand extends BaseCommand {
    private String userInput;

    /**
     * Constructs an AddTodoCommand with the user input.
     * @param userInput the full user input string containing the task description
     */
    public AddTodoCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        Task task = Parser.parseAddTodo(userInput);
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getSize());
        saveTasks(storage, tasks, ui);
    }

    @Override
    public String executeForGui(TaskList tasks, Storage storage) throws BobException {
        Task task = Parser.parseAddTodo(userInput);
        tasks.addTask(task);
        saveTasksQuiet(storage, tasks);
        return "✨ Perfect! Added this to your list:\n  " + task.toString()
                + "\n📊 You now have " + tasks.getSize() + " task(s). Let's crush them! 💪";
    }

    private void saveTasks(Storage storage, TaskList tasks, Ui ui) {
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            ui.showError("Error saving task: " + e.getMessage());
        }
    }

    private void saveTasksQuiet(Storage storage, TaskList tasks) throws BobException {
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            throw new BobException("Error saving task: " + e.getMessage());
        }
    }
}
