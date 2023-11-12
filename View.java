import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class View {
    //if user is a tutor
    public static User findStudent() {
        Scanner scanner = new Scanner(System.in);
        String finalSelection = "";
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("accountDetails.txt"));
            String line = "";
            int choice = 0;
            ArrayList<String> foundPeople = new ArrayList<>();
            boolean found = false;
            int finalNumber = 0;
            String invalidInput = "Invalid input. Please try again.";
            System.out.println("Select one of the options below:\n1. View list of students\n2. Search for a student");
            while (true)  {
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice == 1 || choice == 2)
                        break;
                    System.out.println(invalidInput);
                } catch (InputMismatchException e) {
                    scanner.next();
                    System.out.println(invalidInput);
                }
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
                while (true) {
                    try {
                        finalNumber = scanner.nextInt();
                        scanner.nextLine();
                        if (finalNumber < counter && finalNumber > 0)
                            break;
                        System.out.println(invalidInput);
                    } catch (InputMismatchException e) {
                        System.out.println(invalidInput);
                        scanner.next();
                    }
                }
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
                    bfr.close();
                    if (!found) {
                        System.out.println("User not found! Would you like to search again? (Yes/No)");
                        while (true) {//!searchAgain.equalsIgnoreCase("no") && !searchAgain.equalsIgnoreCase("yes")) {
                            searchAgain = scanner.nextLine();
                            if (searchAgain.equalsIgnoreCase("no")) {
                                System.out.println("Thank you for using TutorFinder! Goodbye.");
                                return null;
                            } else if (searchAgain.equalsIgnoreCase("yes")) {
                                break;
                            }
                            System.out.println(invalidInput);
                        }
                    }
                    bfr = new BufferedReader(new FileReader("accountDetails.txt"));
                } while (!found && searchAgain.equalsIgnoreCase("yes"));
                if (found) {
                    System.out.println("Select from students found:");
                    while (true) {
                        try {
                            finalNumber = scanner.nextInt();
                            scanner.nextLine();
                            if (finalNumber < counter && finalNumber > 0)
                                break;
                            System.out.println(invalidInput);
                        } catch (InputMismatchException e) {
                            System.out.println(invalidInput);
                            scanner.next();
                        }
                    }
                    finalSelection = foundPeople.get(finalNumber - 1);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new User(finalSelection, true);
    }

    //if user is a student
    public static User findTutor() {
        Scanner scanner = new Scanner(System.in);
        String finalSelection = "";
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("accountDetails.txt"));
            String line = "";
            int choice = 0;
            ArrayList<String> foundPeople = new ArrayList<>();
            boolean found = false;
            int finalNumber = 0;
            String invalidInput = "Invalid input. Please try again.";
            System.out.println("Select one of the options below:\n1. View list of tutors\n2. Search for a tutor");
            while (true) {
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice == 1 || choice == 2)
                        break;
                    System.out.println(invalidInput);
                } catch (InputMismatchException e) {
                    scanner.next();
                    System.out.println(invalidInput);
                }
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
                while (true) {
                    try {
                        finalNumber = scanner.nextInt();
                        scanner.nextLine();
                        if (finalNumber < counter && finalNumber > 0)
                            break;
                        System.out.println(invalidInput);
                    } catch (InputMismatchException e) {
                        System.out.println(invalidInput);
                        scanner.next();
                    }
                }
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
                    bfr.close();
                    if (!found) {
                        System.out.println("User not found! Would you like to search again? (Yes/No)");
                        while (true) {//!searchAgain.equalsIgnoreCase("no") && !searchAgain.equalsIgnoreCase("yes")) {
                            searchAgain = scanner.nextLine();
                            if (searchAgain.equalsIgnoreCase("no")) {
                                System.out.println("Thank you for using TutorFinder! Goodbye.");
                                return null;
                            } else if (searchAgain.equalsIgnoreCase("yes")) {
                                break;
                            }
                            System.out.println(invalidInput);
                        }
                        bfr = new BufferedReader(new FileReader("accountDetails.txt"));
                    }
                } while (!found && searchAgain.equalsIgnoreCase("yes"));
                if (found) {
                    System.out.println("Select from tutors found:");
                    while (true) {
                        try {
                            finalNumber = scanner.nextInt();
                            scanner.nextLine();
                            if (finalNumber < counter && finalNumber > 0)
                                break;
                            System.out.println(invalidInput);
                        } catch (InputMismatchException e) {
                            System.out.println(invalidInput);
                            scanner.next();
                        }
                    }
                    finalSelection = foundPeople.get(finalNumber - 1);
                }
            }
        } catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new User(finalSelection, false);
    }

}
