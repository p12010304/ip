public class ListCommand extends BaseCommand {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        ui.showTaskList(tasks.getAllTasks());
    }
}
