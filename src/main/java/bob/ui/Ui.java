package bob.ui;

import java.util.List;
import java.util.Scanner;

import bob.task.Task;

/**
 * Handles user interface operations for the Bob application.
 * Displays messages to the user and reads command input from the console.
 */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private Scanner scanner;

    /**
     * Constructs a new Ui instance with a Scanner for reading user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message when the application starts.
     */
    public void showWelcome() {
        showLine();
        println(" Hello! I'm Bob");
        println(" What can I do for you?");
        showLine();
    }

    /**
     * Displays the exit message when the user quits the application.
     */
    public void showExit() {
        showLine();
        println(" Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Displays a dividing line to separate output sections.
     */
    public void showLine() {
        System.out.println(DIVIDER);
    }

    /**
     * Prints a message to the console followed by a newline.
     * @param message the text to display
     */
    public void println(String message) {
        System.out.println(message);
    }

    /**
     * Prints a message to the console without a newline.
     * @param message the text to display
     */
    public void print(String message) {
        System.out.print(message);
    }

    /**
     * Reads the next command input from the user.
     * @return the command string entered by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays an error message indicating a failure to load the task list from storage.
     * Informs the user that the application will start with an empty list.
     */
    public void showLoadingError() {
        showLine();
        println(" Error loading tasks: starting with empty list.");
        showLine();
    }

    /**
     * Displays a message indicating the number of tasks successfully loaded from storage.
     * If the count is 0, informs the user that they are starting with a fresh list.
     * @param count the number of tasks loaded
     */
    public void showLoadingSuccess(int count) {
        if (count == 0) {
            println(" No existing tasks found. Starting with a fresh task list.");
        } else {
            println(" Loaded " + count + " task(s) from storage.");
        }
    }

    /**
     * Displays an error message to the user.
     * Formats the message with the "Hey Bob!!" prefix and dividing lines.
     * @param message the error description to display
     */
    public void showError(String message) {
        showLine();
        println(" Hey Bob!! " + message);
        showLine();
    }

    /**
     * Displays the user's task list.
     * Shows a message if the list is empty, or displays all tasks with numbering.
     * @param tasks the list of tasks to display
     */
    public void showTaskList(List<Task> tasks) {
        showLine();
        if (tasks.isEmpty()) {
            println(" Your task list is empty.");
        } else {
            println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                println(" " + (i + 1) + "." + tasks.get(i));
            }
        }
        showLine();
    }

    /**
     * Displays a confirmation message that a task has been added to the list.
     * Shows the task details and the new total number of tasks.
     * @param task the task that was added
     * @param totalTasks the total number of tasks after adding
     */
    public void showTaskAdded(Task task, int totalTasks) {
        showLine();
        println(" Got it. I've added this task:");
        println("   " + task);
        println(" Now you have " + totalTasks + " tasks in the list.");
        showLine();
    }

    /**
     * Displays a confirmation message that a task has been marked as done.
     * @param task the task that was marked as done
     */
    public void showTaskMarked(Task task) {
        showLine();
        println(" Nice! I've marked this task as done:");
        println("   " + task);
        showLine();
    }

    /**
     * Displays a confirmation message that a task has been marked as not done.
     * @param task the task that was marked as not done
     */
    public void showTaskUnmarked(Task task) {
        showLine();
        println(" OK, I've marked this task as not done yet:");
        println("   " + task);
        showLine();
    }

    /**
     * Displays a confirmation message that a task has been deleted.
     * Shows the deleted task details and the new total number of remaining tasks.
     * @param task the task that was deleted
     * @param remainingTasks the number of tasks remaining after deletion
     */
    public void showTaskDeleted(Task task, int remainingTasks) {
        showLine();
        println(" Noted. I've removed this task:");
        println("   " + task);
        println(" Now you have " + remainingTasks + " tasks in the list.");
        showLine();
    }

    /**
     * Displays tasks that match a specified date or date range.
     * Shows a message if no tasks are found, or displays matching tasks with numbering.
     * For Deadline tasks, exact date match is required. For Event tasks, date must fall within the event range.
     * @param matchingTasks the list of tasks found for the specified date
     * @param dateStr the date string in the format yyyy-MM-dd
     */
    public void showTasksOnDate(List<Task> matchingTasks, String dateStr) {
        showLine();
        if (matchingTasks.isEmpty()) {
            println(" No tasks found for " + dateStr);
        } else {
            println(" Here are the tasks on or around " + dateStr + ":");
            for (int i = 0; i < matchingTasks.size(); i++) {
                println(" " + (i + 1) + "." + matchingTasks.get(i));
            }
        }
        showLine();
    }

    /**
     * Displays tasks that match a keyword search.
     *
     * @param matchingTasks the list of tasks matching the keyword
     * @param keyword the search keyword
     */
    public void showTasksFound(List<Task> matchingTasks, String keyword) {
        showLine();
        if (matchingTasks.isEmpty()) {
            println(" No matching tasks found for \"" + keyword + "\"");
        } else {
            println(" Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                println(" " + (i + 1) + "." + matchingTasks.get(i));
            }
        }
        showLine();
    }
}
