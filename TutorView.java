import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class TutorView {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        BufferedReader bfr = new BufferedReader(new FileReader("accountDetails.txt"));
        String line = "";
        ArrayList<String> foundPeople = new ArrayList<>();
        boolean found = false;
        int finalNumber = 0;
        String finalSelection = "";
        do {
            System.out.println("Select one of the options below:\n1. View list of students\n2. Search for a student");
            choice = scanner.nextInt();
            scanner.nextLine();
            int counter = 1;
            if (choice == 1) {
                line = bfr.readLine();
                while(line != null) {
                    String[] splitLine = line.split(":");
                    if (Boolean.parseBoolean(splitLine[2])) {
                        foundPeople.add(splitLine[0]);
                        System.out.println(counter + ". " + foundPeople.get(counter - 1));
                        counter++;
                    }
                    line = bfr.readLine();
                }
                System.out.println("Select from students found:");
                finalSelection = scanner.nextLine();
            } else if (choice == 2) {
                String searchAgain = "";
                do {
                    System.out.print("Search: ");
                    String name = scanner.next();
                    scanner.nextLine();
                    line = bfr.readLine();
                    while (line != null) {
                        String[] splitLine = line.split(":");
                        if (splitLine[0].toLowerCase().contains(name.toLowerCase())) {
                            if (Boolean.parseBoolean(splitLine[2])) {
                                foundPeople.add(splitLine[0]);
                                System.out.println(counter + ". " + foundPeople.get(counter - 1));
                                counter++;
                                found = true;
                            }
                        }
                        line = bfr.readLine();
                    }
                    if (!found) {
                        System.out.println("User not found! Would you like to search again? (Yes/No)");
                        searchAgain = scanner.nextLine();
                    }
                } while (!found && searchAgain.equalsIgnoreCase("yes"));
                if (found) {
                    System.out.println("Select from tutors found:");
                    finalNumber = scanner.nextInt();
                    finalSelection = foundPeople.get(finalNumber - 1);
                }
                if (searchAgain.equalsIgnoreCase("no")) {
                    System.out.println("Thank you for using TutorFinder! Goodbye.");
                    break;
                }
            } else {
                System.out.println("Invalid option! Please try again!");
            }
        } while (choice != 1 && choice != 2);
    }
}
