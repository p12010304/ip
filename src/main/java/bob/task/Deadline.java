package bob.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Represents a task with a deadline.
 * A deadline has a description and a due date (the 'by' date).
 */
public class Deadline extends Task {
    protected LocalDate by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy")
                                                                              .withLocale(Locale.ENGLISH);

    /**
     * Constructs a Deadline with the given description and due date.
     *
     * @param description the task description
     * @param by the due date as a string in format yyyy-MM-dd
     * @throws IllegalArgumentException if the date format is invalid
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = parseDate(by);
    }

    /**
     * Constructs a Deadline with the given description and due date.
     *
     * @param description the task description
     * @param by the due date as a LocalDate
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Parses a date string into a LocalDate.
     *
     * @param dateStr the date string in format yyyy-MM-dd
     * @return the parsed LocalDate
     * @throws IllegalArgumentException if the date format is invalid
     */
    private static LocalDate parseDate(String dateStr) throws IllegalArgumentException {
        try {
            return LocalDate.parse(dateStr.trim(), INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd (e.g., 2019-12-01)");
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + " | " + by.format(INPUT_FORMAT);
    }

    public LocalDate getDate() {
        return by;
    }
}
