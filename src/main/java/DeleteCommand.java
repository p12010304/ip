public class DeleteCommand extends BaseCommand {
    private String input;

    public DeleteCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        int idx = Parser.parseTaskIndex(input);
        Task removedTask = tasks.deleteTask(idx);
        ui.showTaskDeleted(removedTask, tasks.getSize());
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
