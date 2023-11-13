import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class View {
    //if user is a tutor 
    public static void findStudent(String userName, User userTerminal) {
        Options options;
        ArrayList<String> blocked = new ArrayList<>();
        for (String c : Options.getBlocked()) {
            blocked.add(c);
        }
        Scanner scanner = new Scanner(System.in);
        String choice = "";
        String finalSelection = "";
        try (BufferedReader bfr = new BufferedReader(new FileReader("accountDetails.txt"))) {
            String line = "";
            ArrayList<String> foundPeople = new ArrayList<>();
            boolean found = false;
            int finalNumber = 0;
            System.out.println("Select one of the options below:\n1. View list of students\n2. Search for a student");
            choice = scanner.nextLine();
            while (!choice.equals("1") && !choice.equals("2")) {
                System.out.println("Invalid option! Please try again.");
                System.out.println("Select one of the options below:\n1. View list of students\n2. Search for a student");
                choice = scanner.nextLine();
                scanner.nextLine();
            }
            int counter = 1;
            if (choice.equals("1")) {
                boolean block;
                line = bfr.readLine();
                while (line != null) {
                    block = false;
                    String[] splitLine = line.split(":");
                    for (String s : blocked) {
                        if (splitLine[0].equals(s.split(":")[1]) && userName.equals(s.split(":")[0])) {
                            block = true;
                        } else if (splitLine[0].equals(s.split(":")[0]) && userName.equals(s.split(":")[1])) {
                            block = true;
                        }
                    }
                    if (Boolean.parseBoolean(splitLine[2]) && !block) {
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
                    boolean block;
                    while (line != null) {
                        block = false;
                        String[] splitLine = line.split(":");
                        for (String s : blocked) {
                            if (splitLine[0].equals(s.split(":")[1]) && userName.equals(s.split(":")[0])) {
                                block = true;
                            } else if (splitLine[0].equals(s.split(":")[0]) && userName.equals(s.split(":")[1])) {
                                block = true;
                            }
                        }
                        if (splitLine[0].toLowerCase().contains(name.toLowerCase())) {
                            if (Boolean.parseBoolean(splitLine[2]) && !block) {
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
                            System.out.println("Thank you for using TutorFinder! Goodbye.");
                            return;
                        }
                    }
                } while (!found && searchAgain.equalsIgnoreCase("yes"));
                if (found) {
                    System.out.println("Select from students found:");
                    finalNumber = scanner.nextInt();
                    scanner.nextLine();
                    finalSelection = foundPeople.get(finalNumber - 1);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        options = new Options(userTerminal, new User(finalSelection, true));
        options.viewMenu();
        scanner.close();
    }

    //if user is a student
    public static void findTutor(String userName, User userTerminal) {
        Options options;
        ArrayList<String> blocked = new ArrayList<>(0);
        for (String c : Options.getBlocked()) {
            blocked.add(c);
        }
        Scanner scanner = new Scanner(System.in);
        String choice = "";
        String finalSelection = "";
        try (BufferedReader bfr = new BufferedReader(new FileReader("accountDetails.txt"))) {
            String line = "";
            ArrayList<String> foundPeople = new ArrayList<>();
            boolean found = false;
            int finalNumber = 0;
            System.out.println("Select one of the options below:\n1. View list of tutors\n2. Search for a tutor");
            choice = scanner.nextLine();
            while (!choice.equals("1") && !choice.equals("2")) {
                System.out.println("Invalid option! Please try again.");
                System.out.println("Select one of the options below:\n1. View list of tutor\n2. Search for a tutor");
                choice = scanner.nextLine();
            }
            int counter = 1;
            if (choice.equals("1")) {
                line = bfr.readLine();
                boolean block;
                while (line != null) {
                    block = false;
                    String[] splitLine = line.split(":");
                    for (String s : blocked) {
                        if (splitLine[0].equals(s.split(":")[1]) && userName.equals(s.split(":")[0])) {
                            block = true;
                        } else if (splitLine[0].equals(s.split(":")[0]) && userName.equals(s.split(":")[1])) {
                            block = true;
                        }
                    }
                    if (!Boolean.parseBoolean(splitLine[2]) && !block) {
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
                    boolean block;
                    while (line != null) {
                        block = false;
                        String[] splitLine = line.split(":");
                        for (String s : blocked) {
                            if (splitLine[0].equals(s.split(":")[1]) && userName.equals(s.split(":")[0])) {
                                block = true;
                            } else if (splitLine[0].equals(s.split(":")[0]) && userName.equals(s.split(":")[1])) {
                                block = true;
                            }
                        }
                        if (splitLine[0].toLowerCase().contains(name.toLowerCase())) {
                            if (!Boolean.parseBoolean(splitLine[2]) && !block) {
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
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        options = new Options(userTerminal, new User(finalSelection, true));
        options.viewMenu();
        scanner.close();
    }
}
