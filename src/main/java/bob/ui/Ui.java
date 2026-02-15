package bob.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

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
        println(" Hey there! 👋 I'm Bob, your friendly task manager!");
        println(" Ready to tackle your to-do list together? Let's get organized! 📋✨");
        showLine();
    }

    /**
     * Displays the exit message when the user quits the application.
     */
    public void showExit() {
        showLine();
        println(" Aww, leaving already? 😢");
        println(" Keep crushing those tasks! See you soon! 🚀✨");
        showLine();
    }

    /**
     * Displays a dividing line to separate output sections.
     */
    public void showLine() {
        System.out.println(DIVIDER);
    }

    /**
     * Prints messages to the console, each followed by a newline.
     * @param messages the text messages to display
     */
    public void println(String... messages) {
        Arrays.stream(messages).forEach(System.out::println);
    }

    /**
     * Prints messages to the console without newlines.
     * @param messages the text messages to display
     */
    public void print(String... messages) {
        Arrays.stream(messages).forEach(System.out::print);
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
        println(" Oops! 😅 Couldn't find your old tasks.");
        println(" No worries though! Let's start fresh with a clean slate! 🎨");
        showLine();
    }

    /**
     * Displays a message indicating the number of tasks successfully loaded from storage.
     * If the count is 0, informs the user that they are starting with a fresh list.
     * @param count the number of tasks loaded
     */
    public void showLoadingSuccess(int count) {
        if (count == 0) {
            println(" 🌟 Fresh start! No tasks yet. Let's add some!");
        } else {
            println(" ✅ Awesome! Loaded " + count + " task(s) from last time!");
        }
    }

    /**
     * Displays an error message to the user.
     * Formats the message with a friendly prefix and dividing lines.
     * @param message the error description to display
     */
    public void showError(String message) {
        showLine();
        println(" Whoops! 🤔 " + message);
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
            println(" 🎉 Woohoo! Your task list is empty!");
            println(" Time to relax or add some new goals! 😎");
        } else {
            println(" 📝 Here's what's on your plate:");
            IntStream.range(0, tasks.size())
                    .forEach(i -> println(" " + (i + 1) + "." + tasks.get(i)));
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
        println(" ✨ Perfect! Added this to your list:");
        println("   " + task);
        println(" 📊 You now have " + totalTasks + " task(s). Let's crush them! 💪");
        showLine();
    }

    /**
     * Displays a confirmation message that a task has been marked as done.
     * @param task the task that was marked as done
     */
    public void showTaskMarked(Task task) {
        showLine();
        println(" 🎉 Awesome job! One down! Marked as complete:");
        println("   " + task);
        println(" Keep up the great work! ⭐");
        showLine();
    }

    /**
     * Displays a confirmation message that a task has been marked as not done.
     * @param task the task that was marked as not done
     */
    public void showTaskUnmarked(Task task) {
        showLine();
        println(" 🔄 No problem! Unmarked this one:");
        println("   " + task);
        println(" You've got this! 💪");
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
        println(" 🗑️ Poof! Removed this task:");
        println("   " + task);
        println(" 📋 " + remainingTasks + " task(s) left. You're making progress! 🚀");
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
            println(" 📅 Hmm, nothing scheduled for " + dateStr + "!");
            println(" Looks like a free day! 😊");
        } else {
            println(" 📅 Here's what's happening around " + dateStr + ":");
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
            println(" 🔍 Couldn't find anything matching \"" + keyword + "\"!");
            println(" Try another search term? 🤔");
        } else {
            println(" 🔍 Found them! Here are your matching tasks:");
            IntStream.range(0, matchingTasks.size())
                    .forEach(i -> println(" " + (i + 1) + "." + matchingTasks.get(i)));
        }
        showLine();
    }

    /**
     * Displays a message indicating that tasks have been sorted.
     */
    public void showSortedMessage() {
        showLine();
        println(" 🎯 Done! Your tasks are now sorted alphabetically.");
        println(" Much more organized now! 📚✨");
        showLine();
    }
}
