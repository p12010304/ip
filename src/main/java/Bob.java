import java.io.IOException;
import java.nio.file.Paths;

public class Bob {
    private static final String FILE_PATH = Paths.get("data", "bob.txt").toString();
    
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Bob(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
            ui.showLoadingSuccess(tasks.getSize());
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                if (fullCommand.trim().isEmpty()) {
                    continue;
                }
                
                ui.showLine();
                BaseCommand command = Parser.parseCommand(fullCommand);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (BobException e) {
                ui.showError(e.getMessage());
            } finally {
                if (!isExit) {
                    ui.showLine();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Bob(FILE_PATH).run();
    }
}
