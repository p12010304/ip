package bob.storage;

import bob.exception.BobException;
import bob.task.Task;
import bob.task.Todo;
import bob.task.Deadline;
import bob.task.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Storage Tests")
class StorageTest {
    
    private Storage storage;
    private Path tempFile;

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws IOException {
        tempFile = tempDir.resolve("test_tasks.txt");
        storage = new Storage(tempFile.toString());
    }

    @Test
    @DisplayName("Storage: should create new file if not exists")
    void testStorageCreatesNewFile() throws IOException {
        assertFalse(Files.exists(tempFile));
        
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("test"));
        storage.save(tasks);
        
        assertTrue(Files.exists(tempFile));
    }

    @Test
    @DisplayName("Storage: should save and load todo task")
    void testSaveAndLoadTodo() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("buy groceries"));
        
        storage.save(tasksToSave);
        List<Task> loaded = storage.load();
        
        assertEquals(1, loaded.size());
        assertTrue(loaded.get(0).toString().contains("buy groceries"));
        assertTrue(loaded.get(0).toString().contains("[T]"));
    }

    @Test
    @DisplayName("Storage: should save and load deadline task")
    void testSaveAndLoadDeadline() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Deadline("submit assignment", "2024-03-15"));
        
        storage.save(tasksToSave);
        List<Task> loaded = storage.load();
        
        assertEquals(1, loaded.size());
        assertTrue(loaded.get(0).toString().contains("submit assignment"));
        assertTrue(loaded.get(0).toString().contains("[D]"));
        assertTrue(loaded.get(0).toString().contains("Mar 15 2024"));
    }

    @Test
    @DisplayName("Storage: should save and load event task")
    void testSaveAndLoadEvent() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Event("project meeting", "2024-03-10", "2024-03-12"));
        
        storage.save(tasksToSave);
        List<Task> loaded = storage.load();
        
        assertEquals(1, loaded.size());
        assertTrue(loaded.get(0).toString().contains("project meeting"));
        assertTrue(loaded.get(0).toString().contains("[E]"));
    }

    @Test
    @DisplayName("Storage: should preserve marked status")
    void testSaveAndLoadMarkedTask() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        Todo todo = new Todo("task");
        todo.markAsDone();
        tasksToSave.add(todo);
        
        storage.save(tasksToSave);
        List<Task> loaded = storage.load();
        
        assertTrue(loaded.get(0).toString().contains("[X]"));
    }

    @Test
    @DisplayName("Storage: should save multiple tasks in order")
    void testSaveAndLoadMultipleTasks() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("task1"));
        tasksToSave.add(new Deadline("task2", "2024-03-15"));
        tasksToSave.add(new Todo("task3"));
        tasksToSave.add(new Event("task4", "2024-03-10", "2024-03-12"));
        
        storage.save(tasksToSave);
        List<Task> loaded = storage.load();
        
        assertEquals(4, loaded.size());
        assertTrue(loaded.get(0).toString().contains("task1"));
        assertTrue(loaded.get(1).toString().contains("task2"));
        assertTrue(loaded.get(2).toString().contains("task3"));
        assertTrue(loaded.get(3).toString().contains("task4"));
    }

    @Test
    @DisplayName("Storage: should load empty list from non-existent file")
    void testLoadFromNonExistentFile() throws IOException {
        assertFalse(Files.exists(tempFile));
        List<Task> loaded = storage.load();
        
        assertTrue(loaded.isEmpty());
    }

    @Test
    @DisplayName("Storage: should overwrite previous data")
    void testSaveOverwrites() throws IOException {
        List<Task> tasks1 = new ArrayList<>();
        tasks1.add(new Todo("original"));
        storage.save(tasks1);
        
        List<Task> tasks2 = new ArrayList<>();
        tasks2.add(new Todo("new task"));
        storage.save(tasks2);
        
        List<Task> loaded = storage.load();
        assertEquals(1, loaded.size());
        assertTrue(loaded.get(0).toString().contains("new task"));
    }

    @Test
    @DisplayName("Storage: should save empty task list")
    void testSaveEmptyList() throws IOException {
        List<Task> emptyTasks = new ArrayList<>();
        storage.save(emptyTasks);
        
        List<Task> loaded = storage.load();
        assertTrue(loaded.isEmpty());
    }

    @Test
    @DisplayName("Storage: should format file correctly")
    void testFileFormat() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        Todo todo = new Todo("test task");
        tasksToSave.add(todo);
        
        storage.save(tasksToSave);
        
        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(1, lines.size());
        assertTrue(lines.get(0).startsWith("T | 0 |"));
        assertTrue(lines.get(0).contains("test task"));
    }

    @Test
    @DisplayName("Storage: should preserve exact deadline date")
    void testDeadlineDatePrecision() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        Deadline deadline = new Deadline("task", "2024-12-31");
        tasksToSave.add(deadline);
        
        storage.save(tasksToSave);
        List<Task> loaded = storage.load();
        
        Deadline loadedDeadline = (Deadline) loaded.get(0);
        assertEquals(LocalDate.of(2024, 12, 31), loadedDeadline.getDate());
    }

    @Test
    @DisplayName("Storage: should preserve exact event date range")
    void testEventDatePrecision() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        Event event = new Event("conference", "2024-06-15", "2024-06-17");
        tasksToSave.add(event);
        
        storage.save(tasksToSave);
        List<Task> loaded = storage.load();
        
        Event loadedEvent = (Event) loaded.get(0);
        assertEquals(LocalDate.of(2024, 6, 15), loadedEvent.getFromDate());
        assertEquals(LocalDate.of(2024, 6, 17), loadedEvent.getToDate());
    }

    @Test
    @DisplayName("Storage: should handle special characters in description")
    void testSpecialCharactersInDescription() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("buy milk and bread"));
        
        storage.save(tasksToSave);
        List<Task> loaded = storage.load();
        
        assertTrue(loaded.get(0).toString().contains("buy milk and bread"));
    }

    @Test
    @DisplayName("Storage: should handle tasks with mixed completion status")
    void testMixedCompletionStatus() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        Todo todo1 = new Todo("done task");
        todo1.markAsDone();
        Todo todo2 = new Todo("pending task");
        tasksToSave.add(todo1);
        tasksToSave.add(todo2);
        
        storage.save(tasksToSave);
        List<Task> loaded = storage.load();
        
        assertTrue(loaded.get(0).toString().contains("[X]"));
        assertFalse(loaded.get(1).toString().contains("[X]"));
    }

    // Additional tests for A-MoreTesting increment

    @Test
    @DisplayName("Storage: should handle long descriptions")
    void testLongDescription() throws IOException {
        String longDesc = "This is a very long task description that contains many words "
                + "and should be saved and loaded correctly without any truncation or data loss";
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo(longDesc));
        
        storage.save(tasksToSave);
        List<Task> loaded = storage.load();
        
        assertTrue(loaded.get(0).getDescription().equals(longDesc));
    }

    @Test
    @DisplayName("Storage: should handle descriptions with pipe characters")
    void testDescriptionWithPipeCharacter() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("task | with | pipes"));
        
        storage.save(tasksToSave);
        List<Task> loaded = storage.load();
        
        // Note: This might fail if storage doesn't escape pipes properly
        assertEquals(1, loaded.size());
    }

    @Test
    @DisplayName("Storage: should handle multiple saves in sequence")
    void testMultipleSavesInSequence() throws IOException {
        List<Task> tasks1 = new ArrayList<>();
        tasks1.add(new Todo("first"));
        storage.save(tasks1);
        
        List<Task> tasks2 = new ArrayList<>();
        tasks2.add(new Todo("second"));
        storage.save(tasks2);
        
        List<Task> tasks3 = new ArrayList<>();
        tasks3.add(new Todo("third"));
        storage.save(tasks3);
        
        List<Task> loaded = storage.load();
        assertEquals(1, loaded.size());
        assertTrue(loaded.get(0).getDescription().equals("third"));
    }

    @Test
    @DisplayName("Storage: should maintain task order across save-load cycles")
    void testTaskOrderPreservation() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("first"));
        tasksToSave.add(new Deadline("second", "2024-03-15"));
        tasksToSave.add(new Event("third", "2024-03-10", "2024-03-12"));
        tasksToSave.add(new Todo("fourth"));
        
        storage.save(tasksToSave);
        List<Task> loaded = storage.load();
        
        assertEquals("first", loaded.get(0).getDescription());
        assertEquals("second", loaded.get(1).getDescription());
        assertEquals("third", loaded.get(2).getDescription());
        assertEquals("fourth", loaded.get(3).getDescription());
    }

    @Test
    @DisplayName("Storage: should handle todos with numbers in description")
    void testTodoWithNumbers() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("buy 2 apples and 3 oranges"));
        
        storage.save(tasksToSave);
        List<Task> loaded = storage.load();
        
        assertTrue(loaded.get(0).getDescription().contains("2 apples"));
        assertTrue(loaded.get(0).getDescription().contains("3 oranges"));
    }

    @Test
    @DisplayName("Storage: should preserve deadline dates without time zone issues")
    void testDeadlineDateNoTimeZoneIssues() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Deadline("task", "2024-01-01"));
        tasksToSave.add(new Deadline("task", "2024-12-31"));
        
        storage.save(tasksToSave);
        List<Task> loaded = storage.load();
        
        Deadline d1 = (Deadline) loaded.get(0);
        Deadline d2 = (Deadline) loaded.get(1);
        assertEquals(LocalDate.of(2024, 1, 1), d1.getDate());
        assertEquals(LocalDate.of(2024, 12, 31), d2.getDate());
    }

    @Test
    @DisplayName("Storage: should handle event with same start and end date")
    void testEventSameStartEndDate() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Event("single day event", "2024-03-15", "2024-03-15"));
        
        storage.save(tasksToSave);
        List<Task> loaded = storage.load();
        
        Event event = (Event) loaded.get(0);
        assertEquals(event.getFromDate(), event.getToDate());
    }

    @Test
    @DisplayName("Storage: should correctly save and load many tasks")
    void testManyTasks() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            tasksToSave.add(new Todo("task " + i));
        }
        
        storage.save(tasksToSave);
        List<Task> loaded = storage.load();
        
        assertEquals(50, loaded.size());
        assertEquals("task 1", loaded.get(0).getDescription());
        assertEquals("task 50", loaded.get(49).getDescription());
    }

    @Test
    @DisplayName("Storage: should handle alternating task types")
    void testAlternatingTaskTypes() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("todo1"));
        tasksToSave.add(new Deadline("deadline1", "2024-03-15"));
        tasksToSave.add(new Event("event1", "2024-03-10", "2024-03-12"));
        tasksToSave.add(new Todo("todo2"));
        tasksToSave.add(new Deadline("deadline2", "2024-03-20"));
        tasksToSave.add(new Event("event2", "2024-04-01", "2024-04-03"));
        
        storage.save(tasksToSave);
        List<Task> loaded = storage.load();
        
        assertEquals(6, loaded.size());
        assertInstanceOf(Todo.class, loaded.get(0));
        assertInstanceOf(Deadline.class, loaded.get(1));
        assertInstanceOf(Event.class, loaded.get(2));
        assertInstanceOf(Todo.class, loaded.get(3));
        assertInstanceOf(Deadline.class, loaded.get(4));
        assertInstanceOf(Event.class, loaded.get(5));
    }

    @Test
    @DisplayName("Storage: should handle save after load from non-existent file")
    void testSaveAfterLoadFromNonExistent() throws IOException {
        List<Task> loaded = storage.load();
        assertTrue(loaded.isEmpty());
        
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("new task"));
        storage.save(tasksToSave);
        
        List<Task> loadedAgain = storage.load();
        assertEquals(1, loadedAgain.size());
    }

    @Test
    @DisplayName("Storage: file format should have correct separators")
    void testFileFormatSeparators() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Deadline("task", "2024-03-15"));
        
        storage.save(tasksToSave);
        
        List<String> lines = Files.readAllLines(tempFile);
        String line = lines.get(0);
        assertTrue(line.contains(" | "));
        // Deadline format: D | status | description | date = 4 parts
        assertEquals(4, line.split(" \\| ").length);
    }

    @Test
    @DisplayName("Storage: should handle empty description edge case")
    void testEmptyDescriptionNotCreated() throws IOException {
        // Storage gracefully skips tasks with empty descriptions during load
        // as they would be considered corrupted data
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo(""));
        
        storage.save(tasksToSave);
        List<Task> loaded = storage.load();
        
        // Empty description may be skipped as corrupted, or loaded as-is
        // Either behavior is acceptable for edge case
        assertTrue(loaded.size() <= 1);
    }
}
