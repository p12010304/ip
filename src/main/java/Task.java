import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmarkAsDone() {
        this.isDone = false;
    }

    public String toFileString() {
        return String.format("%s | %d | %s", 
            (this instanceof Todo ? "T" : this instanceof Deadline ? "D" : "E"),
            (isDone ? 1 : 0), 
            description);
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}

class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

class Deadline extends Task {
    protected LocalDate by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy")
                                                                              .withLocale(Locale.ENGLISH);

    public Deadline(String description, String by) {
        super(description);
        this.by = parseDate(by);
    }

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

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

class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy")
                                                                              .withLocale(Locale.ENGLISH);

    public Event(String description, String from, String to) {
        super(description);
        this.from = parseDate(from);
        this.to = parseDate(to);
    }

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    private static LocalDate parseDate(String dateStr) throws IllegalArgumentException {
        try {
            return LocalDate.parse(dateStr.trim(), INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd (e.g., 2019-12-01)");
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT) + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + " | " + from.format(INPUT_FORMAT) + " | " + to.format(INPUT_FORMAT);
    }

    public LocalDate getFromDate() {
        return from;
    }

    public LocalDate getToDate() {
        return to;
    }
}