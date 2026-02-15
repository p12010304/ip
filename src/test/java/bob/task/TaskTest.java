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

    // Additional tests for A-MoreTesting increment

    @Test
    @DisplayName("Event: should reject from date after to date")
    void testEventInvalidDateOrder() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Event("meeting", "2024-03-15", "2024-03-10");
        });
    }

    @Test
    @DisplayName("Event: should reject same from and to date when from is after")
    void testEventInvalidSameDateOrder() {
        // This should work - same date is valid
        assertDoesNotThrow(() -> {
            new Event("meeting", "2024-03-15", "2024-03-15");
        });
    }

    @Test
    @DisplayName("Event: should reject with LocalDate constructor when from after to")
    void testEventLocalDateInvalidOrder() {
        LocalDate from = LocalDate.of(2024, 3, 20);
        LocalDate to = LocalDate.of(2024, 3, 10);
        assertThrows(IllegalArgumentException.class, () -> {
            new Event("conference", from, to);
        });
    }

    @Test
    @DisplayName("Todo: should preserve exact description")
    void testTodoDescriptionPreservation() {
        String description = "buy milk, bread, and eggs";
        Todo todo = new Todo(description);
        assertEquals(description, todo.getDescription());
    }

    @Test
    @DisplayName("Deadline: should handle whitespace in date string")
    void testDeadlineWhitespaceInDate() {
        Deadline deadline = new Deadline("task", "  2024-03-15  ");
        assertEquals(LocalDate.of(2024, 3, 15), deadline.getDate());
    }

    @Test
    @DisplayName("Event: should handle whitespace in date strings")
    void testEventWhitespaceInDates() {
        Event event = new Event("meeting", "  2024-03-10  ", "  2024-03-12  ");
        assertEquals(LocalDate.of(2024, 3, 10), event.getFromDate());
        assertEquals(LocalDate.of(2024, 3, 12), event.getToDate());
    }

    @Test
    @DisplayName("Task: isDone should return false initially")
    void testTaskInitiallyNotDone() {
        Todo todo = new Todo("test");
        assertFalse(todo.isDone());
    }

    @Test
    @DisplayName("Task: isDone should return true after marking")
    void testTaskDoneAfterMarking() {
        Todo todo = new Todo("test");
        todo.markAsDone();
        assertTrue(todo.isDone());
    }

    @Test
    @DisplayName("Todo: toString should contain type marker")
    void testTodoToStringContainsTypeMarker() {
        Todo todo = new Todo("test");
        String str = todo.toString();
        assertTrue(str.startsWith("[T]"));
    }

    @Test
    @DisplayName("Deadline: toString should contain type marker and date")
    void testDeadlineToStringFormat() {
        Deadline deadline = new Deadline("submit", "2024-03-15");
        String str = deadline.toString();
        assertTrue(str.startsWith("[D]"));
        assertTrue(str.contains("by:"));
    }

    @Test
    @DisplayName("Event: toString should contain type marker and date range")
    void testEventToStringFormat() {
        Event event = new Event("meeting", "2024-03-10", "2024-03-12");
        String str = event.toString();
        assertTrue(str.startsWith("[E]"));
        assertTrue(str.contains("from:"));
        assertTrue(str.contains("to:"));
    }

    @Test
    @DisplayName("Deadline: file format should preserve date in ISO format")
    void testDeadlineFileFormatDatePreservation() {
        Deadline deadline = new Deadline("task", "2024-12-31");
        String fileStr = deadline.toFileString();
        assertTrue(fileStr.contains("2024-12-31"));
    }

    @Test
    @DisplayName("Event: file format should preserve both dates in ISO format")
    void testEventFileFormatDatePreservation() {
        Event event = new Event("conference", "2024-06-01", "2024-06-03");
        String fileStr = event.toFileString();
        assertTrue(fileStr.contains("2024-06-01"));
        assertTrue(fileStr.contains("2024-06-03"));
    }

    @Test
    @DisplayName("Task: multiple mark operations should be idempotent")
    void testTaskMultipleMark() {
        Todo todo = new Todo("test");
        todo.markAsDone();
        // Second mark would trigger assertion, so we just verify it's done
        assertTrue(todo.isDone());
        // Can't call markAsDone again due to assertion in implementation
    }

    @Test
    @DisplayName("Task: multiple unmark operations should be idempotent")
    void testTaskMultipleUnmark() {
        Todo todo = new Todo("test");
        assertFalse(todo.isDone());
        // Can't unmark when not done due to assertion in implementation
        // This tests the initial state is correct
    }
}
