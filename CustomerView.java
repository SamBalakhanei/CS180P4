import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;



public class CustomerView {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        BufferedReader bfr = new BufferedReader(new FileReader("Tutors.txt"));
        String line = "";
        boolean found = false;
        do {
            String finalSelection = "";
            System.out.println("Select one of the options below:\n1. View list of tutors\n2. Search for a tutor");
            choice = scanner.nextInt();
            scanner.nextLine();
            int counter = 1;
            if (choice == 1) {
                line = bfr.readLine();
                while(line != null) {
                    System.out.println(counter + ". " + line);
                    counter++;
                    line = bfr.readLine();
                }
                System.out.println("Select from tutors found:");
                finalSelection = scanner.nextLine();
            } else if (choice == 2) {
                String searchAgain = "";
                do {
                    System.out.print("Search: ");
                    String name = scanner.next();
                    scanner.nextLine();
                    line = bfr.readLine();
                    while (line != null) {
                        if (line.contains(name)) {
                            System.out.println(counter + ". " + line);
                            counter++;
                            found = true;
                        }
                        line = bfr.readLine();
                    }
                    if (!found) {
                        System.out.println("User not found! Would you like to search again? (Yes/No)");
                        searchAgain = scanner.nextLine();
                    }
                } while (!found && searchAgain.equalsIgnoreCase("yes"));
                System.out.println("Select from tutors found:");

            } else {
                System.out.println("Invalid option! Please try again!");
            }
        } while (choice != 1 && choice != 2);
    }

   }
