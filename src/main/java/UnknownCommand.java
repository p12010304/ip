public class UnknownCommand extends BaseCommand {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        throw new BobException("I'm sorry, but I don't know what that means :-(");
    }
}
