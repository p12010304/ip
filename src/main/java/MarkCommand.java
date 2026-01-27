public class MarkCommand extends BaseCommand {
    private String input;

    public MarkCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        int idx = Parser.parseTaskIndex(input);
        tasks.markTask(idx);
        ui.showTaskMarked(tasks.getTask(idx));
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
