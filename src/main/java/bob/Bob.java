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
 * Bob task management application.
 */
public class Bob {
    private static final String FILE_PATH = Paths.get("data", "bob.txt").toString();

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Initializes Bob with specified file path.
     *
     * @param filePath the path for task storage
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
     * Default constructor.
     */
    public Bob() {
        this(FILE_PATH);
    }

    /**
     * Generates a response for user input.
     *
     * @param input the user's command
     * @return Bob's response
     */
    public String getResponse(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "Please enter a command!";
        }
        try {
            BaseCommand command = Parser.parseCommand(input);
            return command.executeForGui(tasks, storage);
        } catch (BobException e) {
            return e.getMessage();
        }
    }

    /**
     * Runs the application loop.
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
