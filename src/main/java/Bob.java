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
            String firstWord = input.split(" ")[0].toUpperCase();

            Command cmd;
            try {
                cmd = Command.valueOf(firstWord);
            } catch (IllegalArgumentException e) {
                cmd = Command.UNKNOWN;
            }

            try {
                switch (cmd) {
                    case BYE:
                        System.out.println(line);
                        System.out.println(" Bye. Hope to see you again soon!");
                        System.out.println(line);
                        return;

                    case LIST:
                        System.out.println(line);
                        System.out.println(" Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println(" " + (i + 1) + "." + tasks.get(i));
                        }
                        System.out.println(line);
                        break;

                    case MARK:
                        handleMarkUnmark(input, tasks, true);
                        break;

                    case UNMARK:
                        handleMarkUnmark(input, tasks, false);
                        break;

                    case DELETE:
                        handleDelete(input, tasks);
                        break;

                    case TODO:
                        if (input.trim().length() <= 4) {
                            throw new BobException("The description of a todo cannot be empty.");
                        }
                        Task todoTask = new Todo(input.substring(5));
                        addTask(todoTask, tasks);
                        break;

                    case DEADLINE:
                        if (!input.contains(" /by ")) {
                            throw new BobException("A deadline must have a /by part.");
                        }
                        String[] deadlineParts = input.substring(9).split(" /by ");
                        if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty()) {
                            throw new BobException("The description or date of a deadline cannot be empty.");
                        }
                        Task deadlineTask = new Deadline(deadlineParts[0], deadlineParts[1]);
                        addTask(deadlineTask, tasks);
                        break;

                    case EVENT:
                        if (!input.contains(" /from ") || !input.contains(" /to ")) {
                            throw new BobException("An event must have /from and /to parts.");
                        }
                        String[] eventParts = input.substring(6).split(" /from | /to ");
                        if (eventParts.length < 3) {
                            throw new BobException("The description, from, or to of an event cannot be empty.");
                        }
                        Task eventTask = new Event(eventParts[0], eventParts[1], eventParts[2]);
                        addTask(eventTask, tasks);
                        break;

                    case UNKNOWN:
                    default:
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

    //Adding tasks to the list
    private static void addTask(Task t, List<Task> tasks) {
        String line = "____________________________________________________________";
        tasks.add(t);
        System.out.println(line);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + t);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(line);
    }

    //Mark or unmark a task
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

    //Deleting an event
    private static void handleDelete(String input, List<Task> tasks) throws BobException {
        String line = "____________________________________________________________";
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new BobException("Please specify which task number to delete.");
        }
        int idx = Integer.parseInt(parts[1]) - 1;
        Task removedTask = tasks.remove(idx);
        System.out.println(line);
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + removedTask);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(line);
    }
}