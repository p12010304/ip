package bob.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import bob.task.Task;
import bob.task.Todo;
import bob.task.Deadline;
import bob.task.Event;

/**
 * Handles persistence of tasks to and from a file.
 * Manages saving tasks to disk and loading them back into memory.
 */
public class Storage {
    private String filePath;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
        Path path = Paths.get(filePath);
        File f = path.toFile();
        
        // Create parent directories if they don't exist
        if (f.getParentFile() != null && !f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        
        // Write tasks to file
        FileWriter fw = new FileWriter(f);
        try {
            for (Task t : tasks) {
                fw.write(t.toFileString() + System.lineSeparator());
            }
        } finally {
            fw.close();
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
        
        // If file doesn't exist, return empty list
        if (!f.exists()) {
            return loadedTasks;
        }

        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                try {
                    // Skip empty lines
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    
                    String[] p = line.split(" \\| ");
                    
                    // Validate minimum fields
                    if (p.length < 3) {
                        System.out.println("Warning: Skipping corrupted line (insufficient fields): " + line);
                        continue;
                    }
                    
                    Task t;
                    try {
                        switch (p[0].trim()) {
                            case "T":
                                t = new bob.task.Todo(p[2]);
                                break;
                            case "D":
                                if (p.length < 4) {
                                    System.out.println("Warning: Skipping corrupted deadline (missing deadline): " + line);
                                    continue;
                                }
                                try {
                                    LocalDate deadlineDate = LocalDate.parse(p[3].trim(), DATE_FORMAT);
                                    t = new bob.task.Deadline(p[2], deadlineDate);
                                } catch (DateTimeParseException e) {
                                    System.out.println("Warning: Skipping deadline with invalid date format: " + line);
                                    continue;
                                }
                                break;
                            case "E":
                                if (p.length < 5) {
                                    System.out.println("Warning: Skipping corrupted event (missing from/to): " + line);
                                    continue;
                                }
                                try {
                                    LocalDate eventFrom = LocalDate.parse(p[3].trim(), DATE_FORMAT);
                                    LocalDate eventTo = LocalDate.parse(p[4].trim(), DATE_FORMAT);
                                    t = new bob.task.Event(p[2], eventFrom, eventTo);
                                } catch (DateTimeParseException e) {
                                    System.out.println("Warning: Skipping event with invalid date format: " + line);
                                    continue;
                                }
                                break;
                            default:
                                System.out.println("Warning: Skipping line with unknown task type: " + line);
                                continue;
                        }
                        
                        // Mark as done if status is 1
                        try {
                            if (p[1].trim().equals("1")) {
                                t.markAsDone();
                            }
                        } catch (Exception e) {
                            System.out.println("Warning: Invalid status field, treating as not done: " + line);
                        }
                        
                        loadedTasks.add(t);
                    } catch (Exception e) {
                        System.out.println("Warning: Error parsing task, skipping line: " + line);
                        continue;
                    }
                } catch (Exception e) {
                    System.out.println("Warning: Error processing line: " + line);
                    continue;
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: Error reading file, starting with empty list: " + e.getMessage());
            return loadedTasks;
        }
        
        return loadedTasks;
    }
}

