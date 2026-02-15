package bob.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import bob.task.Task;

/**
 * User interface for the Bob application.
 */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private Scanner scanner;

    /**
     * Constructs a new Ui instance.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        showLine();
        println(" Hello! I'm Bob, your personal task manager.");
        println(" What would you like to do today?");
        showLine();
    }

    /**
     * Displays the exit message.
     */
    public void showExit() {
        showLine();
        println(" Goodbye! Hope to see you again soon.");
        showLine();
    }

    /**
     * Displays a dividing line.
     */
    public void showLine() {
        System.out.println(DIVIDER);
    }

    /**
     * Prints messages to the console.
     * @param messages the messages to display
     */
    public void println(String... messages) {
        Arrays.stream(messages).forEach(System.out::println);
    }

    /**
     * Prints messages without newlines.
     * @param messages the messages to display
     */
    public void print(String... messages) {
        Arrays.stream(messages).forEach(System.out::print);
    }

    /**
     * Reads the next command from the user.
     * @return the command string
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays an error when tasks cannot be loaded.
     */
    public void showLoadingError() {
        showLine();
        println(" Couldn't load your saved tasks.");
        println(" No worries, starting fresh!");
        showLine();
    }

    /**
     * Displays the number of tasks loaded.
     * @param count the number of tasks loaded
     */
    public void showLoadingSuccess(int count) {
        if (count == 0) {
            println(" Starting fresh, no tasks yet!");
        } else {
            println(" Loaded " + count + " task(s) from last time.");
        }
    }

    /**
     * Displays an error message.
     * @param message the error message
     */
    public void showError(String message) {
        showLine();
        println(" Error: " + message);
        showLine();
    }

    /**
     * Displays the task list.
     * @param tasks the list of tasks
     */
    public void showTaskList(List<Task> tasks) {
        showLine();
        if (tasks.isEmpty()) {
            println(" Your task list is empty! Time to add some tasks.");
        } else {
            println(" Here are your tasks:");
            IntStream.range(0, tasks.size())
                    .forEach(i -> println(" " + (i + 1) + "." + tasks.get(i)));
        }
        showLine();
    }

    /**
     * Displays confirmation that a task has been added.
     * @param task the task added
     * @param totalTasks the total number of tasks
     */
    public void showTaskAdded(Task task, int totalTasks) {
        showLine();
        println(" Got it! Added this task:");
        println("   " + task);
        println(" You now have " + totalTasks + " task(s) in your list.");
        showLine();
    }

    /**
     * Displays confirmation that a task is marked as done.
     * @param task the task marked
     */
    public void showTaskMarked(Task task) {
        showLine();
        println(" Nice! Marked this task as done:");
        println("   " + task);
        showLine();
    }

    /**
     * Displays confirmation that a task is marked as not done.
     * @param task the task unmarked
     */
    public void showTaskUnmarked(Task task) {
        showLine();
        println(" Okay, marked this as not done:");
        println("   " + task);
        showLine();
    }

    /**
     * Displays confirmation that a task has been deleted.
     * @param task the task deleted
     * @param remainingTasks the number of tasks remaining
     */
    public void showTaskDeleted(Task task, int remainingTasks) {
        showLine();
        println(" Noted. Removed this task:");
        println("   " + task);
        println(" Now you have " + remainingTasks + " task(s) in the list.");
        showLine();
    }

    /**
     * Displays tasks on a specific date.
     * @param matchingTasks the tasks found
     * @param dateStr the date
     */
    public void showTasksOnDate(List<Task> matchingTasks, String dateStr) {
        showLine();
        if (matchingTasks.isEmpty()) {
            println(" No tasks found for " + dateStr + ".");
        } else {
            println(" Tasks on " + dateStr + ":");
            for (int i = 0; i < matchingTasks.size(); i++) {
                println(" " + (i + 1) + "." + matchingTasks.get(i));
            }
        }
        showLine();
    }

    /**
     * Displays tasks matching a keyword.
     * @param matchingTasks the matching tasks
     * @param keyword the keyword
     */
    public void showTasksFound(List<Task> matchingTasks, String keyword) {
        showLine();
        if (matchingTasks.isEmpty()) {
            println(" No tasks found with \"" + keyword + "\".");
        } else {
            println(" Here are the matching tasks:");
            IntStream.range(0, matchingTasks.size())
                    .forEach(i -> println(" " + (i + 1) + "." + matchingTasks.get(i)));
        }
        showLine();
    }

    /**
     * Displays message that tasks have been sorted.
     */
    public void showSortedMessage() {
        showLine();
        println(" Done! Your tasks are now sorted alphabetically.");
        showLine();
    }
}
