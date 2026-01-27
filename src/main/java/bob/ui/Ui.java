package bob.ui;

import bob.task.Task;
import java.util.List;
import java.util.Scanner;

public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        showLine();
        println(" Hello! I'm Bob");
        println(" What can I do for you?");
        showLine();
    }

    public void showExit() {
        showLine();
        println(" Bye. Hope to see you again soon!");
        showLine();
    }

    public void showLine() {
        System.out.println(DIVIDER);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void print(String message) {
        System.out.print(message);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLoadingError() {
        showLine();
        println(" Error loading tasks: starting with empty list.");
        showLine();
    }

    public void showLoadingSuccess(int count) {
        if (count == 0) {
            println(" No existing tasks found. Starting with a fresh task list.");
        } else {
            println(" Loaded " + count + " task(s) from storage.");
        }
    }

    public void showError(String message) {
        showLine();
        println(" Hey Bob!! " + message);
        showLine();
    }

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

    public void showTaskAdded(Task task, int totalTasks) {
        showLine();
        println(" Got it. I've added this task:");
        println("   " + task);
        println(" Now you have " + totalTasks + " tasks in the list.");
        showLine();
    }

    public void showTaskMarked(Task task) {
        showLine();
        println(" Nice! I've marked this task as done:");
        println("   " + task);
        showLine();
    }

    public void showTaskUnmarked(Task task) {
        showLine();
        println(" OK, I've marked this task as not done yet:");
        println("   " + task);
        showLine();
    }

    public void showTaskDeleted(Task task, int remainingTasks) {
        showLine();
        println(" Noted. I've removed this task:");
        println("   " + task);
        println(" Now you have " + remainingTasks + " tasks in the list.");
        showLine();
    }

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
