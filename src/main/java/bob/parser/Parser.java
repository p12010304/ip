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
import bob.task.Deadline;
import bob.task.Event;
import bob.task.Task;
import bob.task.Todo;

/**
 * Parses user input and creates appropriate command and task objects.
 * Handles command parsing, task creation, and date parsing with validation.
 */
public class Parser {

    /**
     * Parses a user input string and returns the corresponding command.
     *
     * @param input the raw user input string
     * @return the parsed BaseCommand
     * @throws BobException if the input is empty or invalid
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
     * Parses a todo command and creates a Todo task.
     *
     * @param input the todo command string
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
     * Parses a deadline command and creates a Deadline task.
     *
     * @param input the deadline command string (format: deadline &lt;desc&gt; /by &lt;date&gt;)
     * @return a new Deadline task
     * @throws BobException if the /by part is missing or description/date is empty
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
     * Parses an event command and creates an Event task.
     *
     * @param input the event command string (format: event &lt;desc&gt; /from &lt;date&gt; /to &lt;date&gt;)
     * @return a new Event task
     * @throws BobException if /from or /to parts are missing or description/dates are empty
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
     * Parses a task index from a command string.
     * Converts from 1-based (user input) to 0-based (internal) indexing.
     *
     * @param input the command string containing the task index
     * @return the 0-based task index
     * @throws BobException if the index is missing or not a valid number
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
     * Parses a date string in yyyy-MM-dd format to a LocalDate.
     *
     * @param dateStr the date string to parse
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
