package bob.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Represents a task that spans a time period.
 * An event has a description, a start date, and an end date.
 */
public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy")
                                                                              .withLocale(Locale.ENGLISH);

    /**
     * Constructs a new Event with the given description and date range.
     *
     * @param description a brief description of the event
     * @param from the start date in yyyy-MM-dd format
     * @param to the end date in yyyy-MM-dd format
     * @throws IllegalArgumentException if the date format is invalid
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = parseDate(from);
        this.to = parseDate(to);
    }

    /**
     * Constructs a new Event with the given description and LocalDate range.
     *
     * @param description a brief description of the event
     * @param from the start date as a LocalDate
     * @param to the end date as a LocalDate
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
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
     * Returns the string representation of this event.
     * Format: [E][Status] Description (from: MMM dd yyyy to: MMM dd yyyy)
     *
     * @return the event string representation
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT) + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Returns the file format string representation of this event.
     *
     * @return the file format string
     */
    @Override
    public String toFileString() {
        return super.toFileString() + " | " + from.format(INPUT_FORMAT) + " | " + to.format(INPUT_FORMAT);
    }

    /**
     * Gets the start date of this event.
     *
     * @return the LocalDate of the event start
     */
    public LocalDate getFromDate() {
        return from;
    }

    /**
     * Gets the end date of this event.
     *
     * @return the LocalDate of the event end
     */
    public LocalDate getToDate() {
        return to;
    }
}
