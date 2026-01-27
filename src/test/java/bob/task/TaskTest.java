package bob.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Task Tests")
class TaskTest {

    @Test
    @DisplayName("Todo: should mark as done")
    void testTodoMarkAsDone() {
        Todo todo = new Todo("buy groceries");
        assertFalse(todo.toString().contains("[X]"));
        
        todo.markAsDone();
        assertTrue(todo.toString().contains("[X]"));
    }

    @Test
    @DisplayName("Todo: should unmark as done")
    void testTodoUnmarkAsDone() {
        Todo todo = new Todo("buy groceries");
        todo.markAsDone();
        assertTrue(todo.toString().contains("[X]"));
        
        todo.unmarkAsDone();
        assertFalse(todo.toString().contains("[X]"));
    }

    @Test
    @DisplayName("Todo: should return correct format string")
    void testTodoToString() {
        Todo todo = new Todo("read book");
        String result = todo.toString();
        assertTrue(result.contains("[T]"));
        assertTrue(result.contains("read book"));
        assertTrue(result.contains("[ ]"));
    }

    @Test
    @DisplayName("Todo: should return correct file format")
    void testTodoToFileString() {
        Todo todo = new Todo("read book");
        String result = todo.toFileString();
        assertTrue(result.startsWith("T | 0"));
        assertTrue(result.contains("read book"));
    }

    @Test
    @DisplayName("Todo: file format should reflect marked status")
    void testTodoToFileStringMarked() {
        Todo todo = new Todo("read book");
        todo.markAsDone();
        String result = todo.toFileString();
        assertTrue(result.startsWith("T | 1"));
    }

    @Test
    @DisplayName("Deadline: should parse and store date correctly")
    void testDeadlineParseDate() {
        Deadline deadline = new Deadline("submit assignment", "2024-03-15");
        assertEquals(LocalDate.of(2024, 3, 15), deadline.getDate());
    }

    @Test
    @DisplayName("Deadline: should throw on invalid date format")
    void testDeadlineInvalidDateFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Deadline("submit assignment", "15/03/2024");
        });
    }

    @Test
    @DisplayName("Deadline: should format date in output")
    void testDeadlineToString() {
        Deadline deadline = new Deadline("submit assignment", "2024-03-15");
        String result = deadline.toString();
        assertTrue(result.contains("[D]"));
        assertTrue(result.contains("submit assignment"));
        assertTrue(result.contains("Mar 15 2024"));
    }

    @Test
    @DisplayName("Deadline: should store date in file format as yyyy-MM-dd")
    void testDeadlineToFileString() {
        Deadline deadline = new Deadline("submit assignment", "2024-03-15");
        String result = deadline.toFileString();
        assertTrue(result.contains("2024-03-15"));
        assertTrue(result.startsWith("D | 0"));
    }

    @Test
    @DisplayName("Event: should parse and store date range correctly")
    void testEventParseDateRange() {
        Event event = new Event("project meeting", "2024-03-10", "2024-03-12");
        assertEquals(LocalDate.of(2024, 3, 10), event.getFromDate());
        assertEquals(LocalDate.of(2024, 3, 12), event.getToDate());
    }

    @Test
    @DisplayName("Event: should throw on invalid date format")
    void testEventInvalidDateFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Event("meeting", "10/03/2024", "12/03/2024");
        });
    }

    @Test
    @DisplayName("Event: should format date range in output")
    void testEventToString() {
        Event event = new Event("project meeting", "2024-03-10", "2024-03-12");
        String result = event.toString();
        assertTrue(result.contains("[E]"));
        assertTrue(result.contains("project meeting"));
        assertTrue(result.contains("Mar 10 2024"));
        assertTrue(result.contains("Mar 12 2024"));
    }

    @Test
    @DisplayName("Event: should store dates in file format")
    void testEventToFileString() {
        Event event = new Event("project meeting", "2024-03-10", "2024-03-12");
        String result = event.toFileString();
        assertTrue(result.contains("2024-03-10"));
        assertTrue(result.contains("2024-03-12"));
        assertTrue(result.startsWith("E | 0"));
    }

    @Test
    @DisplayName("Task: status icon should show X when done")
    void testTaskStatusIconDone() {
        Todo todo = new Todo("test");
        assertEquals(" ", todo.getStatusIcon());
        
        todo.markAsDone();
        assertEquals("X", todo.getStatusIcon());
    }

    @Test
    @DisplayName("Event: constructor with LocalDate parameters")
    void testEventConstructorWithLocalDate() {
        LocalDate from = LocalDate.of(2024, 3, 10);
        LocalDate to = LocalDate.of(2024, 3, 12);
        Event event = new Event("meeting", from, to);
        
        assertEquals(from, event.getFromDate());
        assertEquals(to, event.getToDate());
    }

    @Test
    @DisplayName("Deadline: constructor with LocalDate parameter")
    void testDeadlineConstructorWithLocalDate() {
        LocalDate date = LocalDate.of(2024, 3, 15);
        Deadline deadline = new Deadline("task", date);
        
        assertEquals(date, deadline.getDate());
    }
}
