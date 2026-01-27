package bob.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import bob.command.AddDeadlineCommand;
import bob.command.AddEventCommand;
import bob.command.AddTodoCommand;
import bob.command.BaseCommand;
import bob.command.CommandType;
import bob.command.DeleteCommand;
import bob.command.ExitCommand;
import bob.command.FindCommand;
import bob.command.ListCommand;
import bob.command.MarkCommand;
import bob.command.UnknownCommand;
import bob.command.UnmarkCommand;
import bob.exception.BobException;
import bob.task.Task;
import bob.task.Todo;
import bob.task.Deadline;
import bob.task.Event;

/**
 * Parses user input strings into command objects and task objects.
 * Handles command routing and task creation with validation.
 */
public class Parser {

    /**
     * Parses a user input string into a command.
     * Identifies the command type and delegates to the appropriate command class.
     *
     * @param input the user input string
     * @return the corresponding command object
     * @throws BobException if the command is empty
     */
    public static BaseCommand parseCommand(String input) throws BobException {
        if (input.trim().isEmpty()) {
            throw new BobException("Command cannot be empty.");
        }
        
        String firstWord = input.split(" ")[0].toUpperCase();
        CommandType cmd;
        try {
            cmd = CommandType.valueOf(firstWord);
        } catch (IllegalArgumentException e) {
            cmd = CommandType.UNKNOWN;
        }

        switch (cmd) {
            case TODO:
                return new AddTodoCommand(input);
            case DEADLINE:
                return new AddDeadlineCommand(input);
            case EVENT:
                return new AddEventCommand(input);
            case LIST:
                return new ListCommand();
            case MARK:
                return new MarkCommand(input);
            case UNMARK:
                return new UnmarkCommand(input);
            case DELETE:
                return new DeleteCommand(input);
            case FIND:
                return new FindCommand(input);
            case BYE:
                return new ExitCommand();
            case UNKNOWN:
            default:
                return new UnknownCommand();
        }
    }

    /**
     * Parses a todo command input into a Todo task.
     *
     * @param input the todo command input string (e.g., "todo read book")
     * @return a new Todo task
     * @throws BobException if the description is empty
     */
    public static Task parseAddTodo(String input) throws BobException {
        if (input.trim().length() <= 4) {
            throw new BobException("The description of a todo cannot be empty.");
        }
        String description = input.substring(5).trim();
        return new Todo(description);
    }

    /**
     * Parses a deadline command input into a Deadline task.
     *
     * @param input the deadline command input string (e.g., "deadline return book /by 2019-12-01")
     * @return a new Deadline task
     * @throws BobException if the /by marker is missing, description is empty, or date format is invalid
     */
    public static Task parseAddDeadline(String input) throws BobException {
        if (!input.contains(" /by ")) {
            throw new BobException("A deadline must have a /by part.");
        }
        String[] parts = input.substring(9).split(" /by ");
        if (parts.length < 2 || parts[0].trim().isEmpty()) {
            throw new BobException("The description or date of a deadline cannot be empty.");
        }
        try {
            return new Deadline(parts[0].trim(), parts[1].trim());
        } catch (IllegalArgumentException e) {
            throw new BobException(e.getMessage());
        }
    }

    /**
     * Parses an event command input into an Event task.
     *
     * @param input the event command input string (e.g., "event project /from 2019-10-15 /to 2019-10-20")
     * @return a new Event task
     * @throws BobException if /from or /to marker is missing, description is empty, or date format is invalid
     */
    public static Task parseAddEvent(String input) throws BobException {
        if (!input.contains(" /from ") || !input.contains(" /to ")) {
            throw new BobException("An event must have /from and /to parts.");
        }
        String[] parts = input.substring(6).split(" /from | /to ");
        if (parts.length < 3 || parts[0].trim().isEmpty()) {
            throw new BobException("The description, from, or to of an event cannot be empty.");
        }
        try {
            return new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
        } catch (IllegalArgumentException e) {
            throw new BobException(e.getMessage());
        }
    }

    /**
     * Parses a task index from the input command.
     * Converts from 1-based user numbering to 0-based array indexing.
     *
     * @param input the command input containing the task number (e.g., "mark 1")
     * @return the 0-based index of the task
     * @throws BobException if the task number is missing or not a valid integer
     */
    public static int parseTaskIndex(String input) throws BobException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new BobException("Please specify a task number.");
        }
        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new BobException("Please provide a valid task number.");
        }
    }

    /**
     * Parses a date string into a LocalDate object.
     *
     * @param dateStr the date string in format yyyy-MM-dd
     * @return the parsed LocalDate
     * @throws BobException if the date format is invalid
     */
    public static LocalDate parseDate(String dateStr) throws BobException {
        DateTimeFormatter dateFormat = 
            DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(dateStr.trim(), dateFormat);
        } catch (DateTimeParseException e) {
            throw new BobException("Invalid date format. Please use yyyy-MM-dd (e.g., 2019-12-01)");
        }
    }
}
