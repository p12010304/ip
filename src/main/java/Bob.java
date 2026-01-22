import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bob {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        System.out.println(line);
        System.out.println(" Hello! I'm Bob");
        System.out.println(" What can I do for you?");
        System.out.println(line);

        Scanner in = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();

        while (true) {
            String input = in.nextLine();
            //lvl 5: try catch exception
            try {
                if (input.equals("bye")) {
                    System.out.println(line);
                    System.out.println(" Bye. Hope to see you again soon!");
                    System.out.println(line);
                    break;
                } else if (input.equals("list")) {
                    System.out.println(line);
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(" " + (i + 1) + "." + tasks.get(i));
                    }
                    System.out.println(line);
                } else if (input.startsWith("mark")) {
                    handleMarkUnmark(input, tasks, true);
                } else if (input.startsWith("unmark")) {
                    handleMarkUnmark(input, tasks, false);
                } else if (input.startsWith("todo")) {
                    if (input.trim().length() <= 4) {
                        throw new BobException("The description of a todo cannot be empty.");
                    }
                    Task t = new Todo(input.substring(5));
                    addTask(t, tasks);
                } else if (input.startsWith("deadline")) {
                    if (!input.contains(" /by ")) {
                        throw new BobException("A deadline must have a /by part.");
                    }
                    String[] parts = input.substring(9).split(" /by ");
                    if (parts.length < 2 || parts[0].trim().isEmpty()) {
                        throw new BobException("The description or date of a deadline cannot be empty.");
                    }
                    Task t = new Deadline(parts[0], parts[1]);
                    addTask(t, tasks);
                } else if (input.startsWith("event")) {
                    if (!input.contains(" /from ") || !input.contains(" /to ")) {
                        throw new BobException("An event must have /from and /to parts.");
                    }
                    String[] parts = input.substring(6).split(" /from | /to ");
                    if (parts.length < 3) {
                        throw new BobException("The description, from, or to of an event cannot be empty.");
                    }
                    Task t = new Event(parts[0], parts[1], parts[2]);
                    addTask(t, tasks);
                } else {
                    //when user inputs indistinguishable prompts
                    throw new BobException("I'm sorry, but I don't know what that means :-(");
                }
                //catch the exceptions and print corresponding message
            } catch (BobException e) {
                System.out.println(line);
                System.out.println(" Hey Bob!! " + e.getMessage());
                System.out.println(line);
            } catch (NumberFormatException e) {
                System.out.println(line);
                System.out.println(" Hey Bob!! Please provide a valid task number.");
                System.out.println(line);
            } catch (IndexOutOfBoundsException e) {
                System.out.println(line);
                System.out.println(" Hey Bob!! That task number doesn't exist in your list.");
                System.out.println(line);
            }
        }
    }

    //separate add task method
    private static void addTask(Task t, List<Task> tasks) {
        String line = "____________________________________________________________";
        tasks.add(t);
        System.out.println(line);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + t);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(line);
    }

    private static void handleMarkUnmark(String input, List<Task> tasks, boolean isMark) throws BobException {
        String line = "____________________________________________________________";
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new BobException("Please specify which task number to " + (isMark ? "mark" : "unmark") + ".");
        }
        int idx = Integer.parseInt(parts[1]) - 1;
        if (isMark) {
            tasks.get(idx).markAsDone();
            System.out.println(line);
            System.out.println(" Nice! I've marked this task as done:");
        } else {
            tasks.get(idx).unmarkAsDone();
            System.out.println(line);
            System.out.println(" OK, I've marked this task as not done yet:");
        }
        System.out.println("   " + tasks.get(idx));
        System.out.println(line);
    }
}