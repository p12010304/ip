public class FindCommand extends BaseCommand {
    private String input;

    public FindCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BobException {
        String dateStr = input.substring(5).trim();
        java.time.LocalDate searchDate = Parser.parseDate(dateStr);
        java.util.List<Task> matchingTasks = tasks.findTasksByDate(searchDate);
        ui.showTasksOnDate(matchingTasks, dateStr);
    }
}
