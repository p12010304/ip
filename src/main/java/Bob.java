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

        while (true) {
            input = in.nextLine();

            if (input.equals("bye")) {
                System.out.println(line);
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }

            System.out.println(line);
            System.out.println(" " + input);
            System.out.println(line);
        }
    }
}