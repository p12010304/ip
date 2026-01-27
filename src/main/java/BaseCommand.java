public abstract class BaseCommand {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws BobException;

    public boolean isExit() {
        return false;
    }
}
