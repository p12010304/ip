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
        List<String> tasks = new ArrayList<>();

        while (true) {
            input = in.nextLine();

            if (input.equals("bye")) {
                System.out.println(line);
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }

            else if (input.equals("list")) {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks.get(i));
                }
                System.out.println(line);
            }

            else{
                System.out.println(line);
                tasks.add(input);
                System.out.println(" added: " + input);
                System.out.println(line);
            }
        }
    }
}