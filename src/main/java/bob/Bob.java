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
 * Main entry point for the Bob task management chatbot application.
 * Manages the overall lifecycle of the application including initialization,
 * user interaction loop, and task persistence.
 */
public class Bob {
    private static final String FILE_PATH = Paths.get("data", "bob.txt").toString();
    
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Initializes Bob with a specified file path for task storage.
     * Loads existing tasks from storage or creates a new task list if storage fails.
     *
     * @param filePath the path where task data will be persisted
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
     * Default constructor that uses the default file path.
     */
    public Bob() {
        this(FILE_PATH);
    }

    /**
     * Generates a response for the user's input.
     * Used by the GUI to get Bob's response to user commands.
     *
     * @param input the user's command
     * @return Bob's response as a string
     */
    public String getResponse(String input) {
        try {
            BaseCommand command = Parser.parseCommand(input);
            return command.executeForGui(tasks, storage);
        } catch (BobException e) {
            return e.getMessage();
        }
    }

    /**
     * Runs the main application loop.
     * Continuously reads user commands, parses them, executes them, and handles errors
     * until the user exits the application.
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
     * Entry point for the application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        new Bob(FILE_PATH).run();
    }
}
