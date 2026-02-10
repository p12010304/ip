package bob.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Represents a task with a deadline.
 * A deadline has a description and a date by which it must be completed.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy")
                                                                              .withLocale(Locale.ENGLISH);
    protected LocalDate by;

    /**
     * Constructs a new Deadline with the given description and deadline date.
     *
     * @param description a brief description of the deadline task
     * @param by the deadline date in yyyy-MM-dd format
     * @throws IllegalArgumentException if the date format is invalid
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = parseDate(by);
    }

    /**
     * Constructs a new Deadline with the given description and LocalDate.
     *
     * @param description a brief description of the deadline task
     * @param by the deadline date as a LocalDate
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Parses a date string in yyyy-MM-dd format to a LocalDate.
     *
     * @param dateStr the date string to parse
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

    /**
     * Returns the string representation of this deadline.
     * Format: [D][Status] Description (by: MMM dd yyyy)
     *
     * @return the deadline string representation
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Returns the file format string representation of this deadline.
     *
     * @return the file format string
     */
    @Override
    public String toFileString() {
        return super.toFileString() + " | " + by.format(INPUT_FORMAT);
    }

    /**
     * Gets the deadline date.
     *
     * @return the LocalDate of the deadline
     */
    public LocalDate getDate() {
        return by;
    }
}
