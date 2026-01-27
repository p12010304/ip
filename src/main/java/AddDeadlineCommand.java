public class AddDeadlineCommand extends BaseCommand {
    private String input;

    public AddDeadlineCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        Task task = Parser.parseAddDeadline(input);
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getSize());
        saveTask(storage, tasks, ui);
    }

    private void saveTask(Storage storage, TaskList tasks, Ui ui) {
        try {
            storage.save(tasks.getAllTasks());
        } catch (java.io.IOException e) {
            ui.showError("Error saving task: " + e.getMessage());
        }
    }
}
