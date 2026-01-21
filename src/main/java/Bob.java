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
                int idx = Integer.parseInt(input.split(" ")[1]) - 1;
                tasks.get(idx).markAsDone();
                System.out.println(line);
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + tasks.get(idx));
                System.out.println(line);
            } else if (input.startsWith("unmark")) {
                int idx = Integer.parseInt(input.split(" ")[1]) - 1;
                tasks.get(idx).unmarkAsDone();
                System.out.println(line);
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks.get(idx));
                System.out.println(line);
            } else {
                Task t = null;
                if (input.startsWith("todo")) {
                    t = new Todo(input.substring(5));
                } else if (input.startsWith("deadline")) {
                    String[] parts = input.substring(9).split(" /by ");
                    t = new Deadline(parts[0], parts[1]);
                } else if (input.startsWith("event")) {
                    String[] parts = input.substring(6).split(" /from | /to ");
                    t = new Event(parts[0], parts[1], parts[2]);
                }

                if (t != null) {
                    tasks.add(t);
                    System.out.println(line);
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + t);
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(line);
                }
            }
        }
    }
}