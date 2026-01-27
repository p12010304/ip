package bob.storage;

import bob.exception.BobException;
import bob.task.*;
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
}
