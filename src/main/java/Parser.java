import java.time.LocalDate;

public class Parser {
    
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

    public static Task parseAddTodo(String input) throws BobException {
        if (input.trim().length() <= 4) {
            throw new BobException("The description of a todo cannot be empty.");
        }
        String description = input.substring(5).trim();
        return new Todo(description);
    }

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

    public static LocalDate parseDate(String dateStr) throws BobException {
        java.time.format.DateTimeFormatter dateFormat = 
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(dateStr.trim(), dateFormat);
        } catch (java.time.format.DateTimeParseException e) {
            throw new BobException("Invalid date format. Please use yyyy-MM-dd (e.g., 2019-12-01)");
        }
    }

    private enum CommandType {
        TODO, DEADLINE, EVENT, LIST, MARK, UNMARK, DELETE, BYE, FIND, UNKNOWN
    }
}
