import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class View {
    //if user is a tutor 
    public static String findStudent() throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        BufferedReader bfr = new BufferedReader(new FileReader("accountDetails.txt"));
        String line = "";
        ArrayList<String> foundPeople = new ArrayList<>();
        boolean found = false;
        int finalNumber = 0;
        String finalSelection = "";
        System.out.println("Select one of the options below:\n1. View list of students\n2. Search for a student");
        choice = scanner.nextInt();
        scanner.nextLine();
        while (choice != 1 && choice != 2) {
            System.out.println("Invalid option! Please try again.");
            System.out.println("Select one of the options below:\n1. View list of students\n2. Search for a student");
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        int counter = 1;
        if (choice == 1) {
            line = bfr.readLine();
            while (line != null) {
                String[] splitLine = line.split(":");
                if (Boolean.parseBoolean(splitLine[2])) {
                    foundPeople.add(splitLine[0]);
                    System.out.println(counter + ". " + foundPeople.get(counter - 1));
                    counter++;
                }
                line = bfr.readLine();
            }
            System.out.println("Select from students found:");
            finalNumber = scanner.nextInt();
            scanner.nextLine();
            finalSelection = foundPeople.get(finalNumber - 1);
        } else {
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
                    if (searchAgain.equalsIgnoreCase("no")) {
                        return "Thank you for using TutorFinder! Goodbye.";
                    }
                }
            } while (!found && searchAgain.equalsIgnoreCase("yes"));
            if (found) {
                System.out.println("Select from tutors found:");
                finalNumber = scanner.nextInt();
                scanner.nextLine();
                finalSelection = foundPeople.get(finalNumber - 1);
            }

        }
        return finalSelection;
    }

    //if user is a student
    public static String findTutor() throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        BufferedReader bfr = new BufferedReader(new FileReader("accountDetails.txt"));
        String line = "";
        ArrayList<String> foundPeople = new ArrayList<>();
        boolean found = false;
        int finalNumber = 0;
        String finalSelection = "";
        System.out.println("Select one of the options below:\n1. View list of tutors\n2. Search for a tutor");
        choice = scanner.nextInt();
        scanner.nextLine();
        while (choice != 1 && choice != 2) {
            System.out.println("Invalid option! Please try again.");
            System.out.println("Select one of the options below:\n1. View list of tutor\n2. Search for a tutor");
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        int counter = 1;
        if (choice == 1) {
            line = bfr.readLine();
            while (line != null) {
                String[] splitLine = line.split(":");
                if (!Boolean.parseBoolean(splitLine[2])) {
                    foundPeople.add(splitLine[0]);
                    System.out.println(counter + ". " + foundPeople.get(counter - 1));
                    counter++;
                }
                line = bfr.readLine();
            }
            System.out.println("Select from tutors found:");
            finalNumber = scanner.nextInt();
            finalSelection = foundPeople.get(finalNumber - 1);
        } else {
            String searchAgain = "";
            do {
                System.out.print("Search: ");
                String name = scanner.next();
                scanner.nextLine();
                line = bfr.readLine();
                while (line != null) {
                    String[] splitLine = line.split(":");
                    if (splitLine[0].toLowerCase().contains(name.toLowerCase())) {
                        if (!Boolean.parseBoolean(splitLine[2])) {
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
                return "Thank you for using TutorFinder! Goodbye.";
            }
        }
        return finalSelection;
    }
}