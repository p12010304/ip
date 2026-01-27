package bob.parser;

import bob.command.*;
import bob.exception.BobException;
import bob.task.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Parser Tests")
class ParserTest {

    @Test
    @DisplayName("parseCommand: should parse todo command")
    void testParseCommandTodo() throws BobException {
        BaseCommand cmd = Parser.parseCommand("todo buy groceries");
        assertInstanceOf(AddTodoCommand.class, cmd);
    }

    @Test
    @DisplayName("parseCommand: should parse deadline command")
    void testParseCommandDeadline() throws BobException {
        BaseCommand cmd = Parser.parseCommand("deadline submit /by 2024-03-15");
        assertInstanceOf(AddDeadlineCommand.class, cmd);
    }

    @Test
    @DisplayName("parseCommand: should parse event command")
    void testParseCommandEvent() throws BobException {
        BaseCommand cmd = Parser.parseCommand("event meeting /from 2024-03-10 /to 2024-03-12");
        assertInstanceOf(AddEventCommand.class, cmd);
    }

    @Test
    @DisplayName("parseCommand: should parse list command")
    void testParseCommandList() throws BobException {
        BaseCommand cmd = Parser.parseCommand("list");
        assertInstanceOf(ListCommand.class, cmd);
    }

    @Test
    @DisplayName("parseCommand: should parse mark command")
    void testParseCommandMark() throws BobException {
        BaseCommand cmd = Parser.parseCommand("mark 1");
        assertInstanceOf(MarkCommand.class, cmd);
    }

    @Test
    @DisplayName("parseCommand: should parse unmark command")
    void testParseCommandUnmark() throws BobException {
        BaseCommand cmd = Parser.parseCommand("unmark 1");
        assertInstanceOf(UnmarkCommand.class, cmd);
    }

    @Test
    @DisplayName("parseCommand: should parse delete command")
    void testParseCommandDelete() throws BobException {
        BaseCommand cmd = Parser.parseCommand("delete 1");
        assertInstanceOf(DeleteCommand.class, cmd);
    }

    @Test
    @DisplayName("parseCommand: should parse find command")
    void testParseCommandFind() throws BobException {
        BaseCommand cmd = Parser.parseCommand("find 2024-03-15");
        assertInstanceOf(FindCommand.class, cmd);
    }

    @Test
    @DisplayName("parseCommand: should parse bye command")
    void testParseCommandBye() throws BobException {
        BaseCommand cmd = Parser.parseCommand("bye");
        assertInstanceOf(ExitCommand.class, cmd);
    }

    @Test
    @DisplayName("parseCommand: should parse unknown command")
    void testParseCommandUnknown() throws BobException {
        BaseCommand cmd = Parser.parseCommand("unknown");
        assertInstanceOf(UnknownCommand.class, cmd);
    }

    @Test
    @DisplayName("parseCommand: should throw on empty command")
    void testParseCommandEmpty() {
        assertThrows(BobException.class, () -> {
            Parser.parseCommand("");
        });
    }

    @Test
    @DisplayName("parseCommand: should handle case-insensitivity")
    void testParseCommandCaseInsensitive() throws BobException {
        BaseCommand cmd1 = Parser.parseCommand("TODO buy groceries");
        BaseCommand cmd2 = Parser.parseCommand("todo buy groceries");
        
        assertInstanceOf(AddTodoCommand.class, cmd1);
        assertInstanceOf(AddTodoCommand.class, cmd2);
    }

    @Test
    @DisplayName("parseAddTodo: should create todo with description")
    void testParseAddTodo() throws BobException {
        Task todo = Parser.parseAddTodo("todo buy groceries");
        assertInstanceOf(Todo.class, todo);
        assertTrue(todo.toString().contains("buy groceries"));
    }

    @Test
    @DisplayName("parseAddTodo: should throw on empty description")
    void testParseAddTodoEmpty() {
        assertThrows(BobException.class, () -> {
            Parser.parseAddTodo("todo");
        });
    }

    @Test
    @DisplayName("parseAddDeadline: should create deadline with date")
    void testParseAddDeadline() throws BobException {
        Task deadline = Parser.parseAddDeadline("deadline submit /by 2024-03-15");
        assertInstanceOf(Deadline.class, deadline);
        assertTrue(deadline.toString().contains("submit"));
        assertTrue(deadline.toString().contains("Mar 15 2024"));
    }

    @Test
    @DisplayName("parseAddDeadline: should throw without /by marker")
    void testParseAddDeadlineMissingBy() {
        assertThrows(BobException.class, () -> {
            Parser.parseAddDeadline("deadline submit 2024-03-15");
        });
    }

    @Test
    @DisplayName("parseAddDeadline: should throw with empty description")
    void testParseAddDeadlineEmptyDescription() {
        assertThrows(BobException.class, () -> {
            Parser.parseAddDeadline("deadline /by 2024-03-15");
        });
    }

    @Test
    @DisplayName("parseAddEvent: should create event with date range")
    void testParseAddEvent() throws BobException {
        Task event = Parser.parseAddEvent("event meeting /from 2024-03-10 /to 2024-03-12");
        assertInstanceOf(Event.class, event);
        assertTrue(event.toString().contains("meeting"));
        assertTrue(event.toString().contains("Mar 10 2024"));
        assertTrue(event.toString().contains("Mar 12 2024"));
    }

    @Test
    @DisplayName("parseAddEvent: should throw without /from marker")
    void testParseAddEventMissingFrom() {
        assertThrows(BobException.class, () -> {
            Parser.parseAddEvent("event meeting 2024-03-10 /to 2024-03-12");
        });
    }

    @Test
    @DisplayName("parseAddEvent: should throw without /to marker")
    void testParseAddEventMissingTo() {
        assertThrows(BobException.class, () -> {
            Parser.parseAddEvent("event meeting /from 2024-03-10 2024-03-12");
        });
    }

    @Test
    @DisplayName("parseTaskIndex: should parse task index correctly")
    void testParseTaskIndex() throws BobException {
        int index = Parser.parseTaskIndex("mark 5");
        assertEquals(4, index);
    }

    @Test
    @DisplayName("parseTaskIndex: should throw when index missing")
    void testParseTaskIndexMissing() {
        assertThrows(BobException.class, () -> {
            Parser.parseTaskIndex("mark");
        });
    }

    @Test
    @DisplayName("parseTaskIndex: should throw on non-numeric index")
    void testParseTaskIndexNonNumeric() {
        assertThrows(BobException.class, () -> {
            Parser.parseTaskIndex("mark abc");
        });
    }

    @Test
    @DisplayName("parseDate: should parse valid date")
    void testParseDate() throws BobException {
        LocalDate date = Parser.parseDate("2024-03-15");
        assertEquals(LocalDate.of(2024, 3, 15), date);
    }

    @Test
    @DisplayName("parseDate: should throw on invalid format")
    void testParseDateInvalidFormat() {
        assertThrows(BobException.class, () -> {
            Parser.parseDate("15-03-2024");
        });
    }

    @Test
    @DisplayName("parseDate: should throw on invalid date values")
    void testParseDateInvalidValues() {
        assertThrows(BobException.class, () -> {
            Parser.parseDate("2024-13-45");
        });
    }

    @Test
    @DisplayName("parseDate: should handle whitespace")
    void testParseDateWithWhitespace() throws BobException {
        LocalDate date = Parser.parseDate("  2024-03-15  ");
        assertEquals(LocalDate.of(2024, 3, 15), date);
    }
}
