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
        String input;
        List<Task> tasks = new ArrayList<>();

        while (true) {
            input = in.nextLine();
            String[] words = input.split(" ");

            if (input.equals("bye")) {
                System.out.println(line);
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }

            else if (input.equals("list")) {
                System.out.println(line);
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(" " + (i + 1) + "." + tasks.get(i));
                }
                System.out.println(line);
            }
            else if(words[0].equals("mark")){
                int taskIndex = Integer.parseInt(words[1]) - 1;
                tasks.get(taskIndex).markAsDone();

                System.out.println(line);
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + tasks.get(taskIndex));
                System.out.println(line);
            }
            else if (words[0].equals("unmark")) {
                int taskIndex = Integer.parseInt(words[1]) - 1;
                tasks.get(taskIndex).unmarkAsDone();

                System.out.println(line);
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks.get(taskIndex));
                System.out.println(line);
            }
            else{
                System.out.println(line);
                tasks.add(new Task(input));
                System.out.println(" added: " + input);
                System.out.println(line);
            }
        }
    }
}