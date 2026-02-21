package bob.storage;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import bob.task.Task;

/**
 * Handles persistence of tasks to and from a file.
 * Manages saving tasks to disk and loading them back into memory.
 */
public class Storage {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String filePath;

    /**
     * Constructs a Storage instance with the specified file path.
     *
     * @param filePath the path to the file for storing tasks
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the list of tasks to the file.
     * Creates parent directories if they don't exist.
     *
     * @param tasks the list of tasks to save
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public void save(List<Task> tasks) throws IOException {
        // Assert that tasks list is not null and all tasks are valid
        assert tasks != null : "Tasks list must not be null";
        for (Task task : tasks) {
            assert task != null : "All tasks must be non-null before saving";
        }
        
        Path path = Paths.get(filePath);
        File f = path.toFile();

        // Create parent directories if they don't exist
        if (f.getParentFile() != null && !f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }

        // Write tasks to file using UTF-8 encoding for cross-platform compatibility
        try (Writer fw = new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8)) {
            for (Task task : tasks) {
                fw.write(task.toFileString() + "\n");
            }
        }
    }

    /**
     * Loads tasks from the file.
     * Returns an empty list if the file doesn't exist.
     * Skips corrupted or invalid lines with warnings.
     *
     * @return the list of loaded tasks
     * @throws IOException if an I/O error occurs while reading the file
     */
    public List<Task> load() throws IOException {
        List<Task> loadedTasks = new ArrayList<>();

        Path path = Paths.get(filePath);
        File f = path.toFile();

        if (!f.exists()) {
            return loadedTasks;
        }

        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String line : lines) {
                Task task = parseTaskFromLine(line);
                if (task != null) {
                    loadedTasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: Error reading file, starting with empty list: " + e.getMessage());
            return loadedTasks;
        }

        validateLoadedTasks(loadedTasks);
        return loadedTasks;
    }

    /**
     * Parses a single line from the storage file and creates a Task.
     * Returns null if the line is invalid or cannot be parsed.
     *
     * @param line the line to parse
     * @return the parsed Task, or null if parsing fails
     */
    private Task parseTaskFromLine(String line) {
        if (line.trim().isEmpty()) {
            return null;
        }

        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            System.out.println("Warning: Skipping corrupted line (insufficient fields): " + line);
            return null;
        }

        Task task = createTaskByType(parts, line);
        if (task != null) {
            applyCompletionStatus(task, parts, line);
        }
        return task;
    }

    /**
     * Creates a task based on the task type indicator.
     *
     * @param parts the split components of the storage line
     * @param line the original line for error reporting
     * @return the created Task, or null if creation fails
     */
    private Task createTaskByType(String[] parts, String line) {
        String taskType = parts[0].trim();
        switch (taskType) {
        case "T":
            return createTodoTask(parts);
        case "D":
            return createDeadlineTask(parts, line);
        case "E":
            return createEventTask(parts, line);
        default:
            System.out.println("Warning: Skipping line with unknown task type: " + line);
            return null;
        }
    }

    /**
     * Creates a Todo task from the parsed components.
     *
     * @param parts the split components of the storage line
     * @return the created Todo task
     */
    private Task createTodoTask(String[] parts) {
        return new bob.task.Todo(parts[2].trim());
    }

    /**
     * Creates a Deadline task from the parsed components.
     *
     * @param parts the split components of the storage line
     * @param line the original line for error reporting
     * @return the created Deadline task, or null if creation fails
     */
    private Task createDeadlineTask(String[] parts, String line) {
        if (parts.length < 4) {
            System.out.println("Warning: Skipping corrupted deadline (missing deadline): " + line);
            return null;
        }
        try {
            LocalDate deadlineDate = LocalDate.parse(parts[3].trim(), DATE_FORMAT);
            return new bob.task.Deadline(parts[2], deadlineDate);
        } catch (DateTimeParseException e) {
            System.out.println("Warning: Skipping deadline with invalid date format: " + line);
            return null;
        }
    }

    /**
     * Creates an Event task from the parsed components.
     *
     * @param parts the split components of the storage line
     * @param line the original line for error reporting
     * @return the created Event task, or null if creation fails
     */
    private Task createEventTask(String[] parts, String line) {
        if (parts.length < 5) {
            System.out.println("Warning: Skipping corrupted event (missing from/to): " + line);
            return null;
        }
        try {
            LocalDate eventFrom = LocalDate.parse(parts[3].trim(), DATE_FORMAT);
            LocalDate eventTo = LocalDate.parse(parts[4].trim(), DATE_FORMAT);
            return new bob.task.Event(parts[2], eventFrom, eventTo);
        } catch (DateTimeParseException e) {
            System.out.println("Warning: Skipping event with invalid date format: " + line);
            return null;
        }
    }

    /**
     * Applies the completion status to a task based on the storage data.
     *
     * @param task the task to update
     * @param parts the split components of the storage line
     * @param line the original line for error reporting
     */
    private void applyCompletionStatus(Task task, String[] parts, String line) {
        try {
            if (parts[1].trim().equals("1")) {
                task.markAsDone();
            }
        } catch (Exception e) {
            System.out.println("Warning: Invalid status field, treating as not done: " + line);
        }
    }

    /**
     * Validates that all loaded tasks are non-null.
     *
     * @param loadedTasks the list of tasks to validate
     */
    private void validateLoadedTasks(List<Task> loadedTasks) {
        assert loadedTasks != null : "Loaded tasks list should not be null";
        for (Task task : loadedTasks) {
            assert task != null : "All tasks in loaded list should be non-null";
        }
    }
}

