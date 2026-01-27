package bob;

import java.io.IOException;
import java.nio.file.Paths;

import bob.command.BaseCommand;
import bob.exception.BobException;
import bob.parser.Parser;
import bob.storage.Storage;
import bob.tasklist.TaskList;
import bob.ui.Ui;

/**
 * Main entry point for the Bob task management application.
 * Coordinates the UI, storage, and task list to provide an interactive command-line interface.
 */
public class Bob {
    private static final String FILE_PATH = Paths.get("data", "bob.txt").toString();
    
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a Bob instance with the specified file path for persistent storage.
     *
     * @param filePath the path to the file where tasks will be stored
     */
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

    /**
     * Executes the main application loop.
     * Continuously reads and processes user commands until an exit command is received.
     */
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

    /**
     * Initializes and runs the Bob application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new Bob(FILE_PATH).run();
    }
}
