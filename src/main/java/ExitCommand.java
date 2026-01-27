public class ExitCommand extends BaseCommand {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        ui.showExit();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
