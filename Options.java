import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Options {

    private User userTerminal;
    private User userSelected;

    public Options(User userTerminal, User userSelected) {
        this.userTerminal = userTerminal;
        this.userSelected = userSelected;
    }

    public void viewMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        String option;
        String convoFileName = userTerminal.getUsername() + userSelected.getUsername();
        do {
            System.out.println("""
                    (1) View Conversation
                    (2) Export Conversation
                    (3) Block Tutor
                    (4) Go Back
                    (5) Exit Application""");
            option = scanner.nextLine();
            switch (option) {
                case "1":
                    // View conversation
                    break;
                case "2":
                    // Export conversation to a CSV file.
                    System.out.println("Enter the filepath to export the conversation to:");
                    String filename = scanner.nextLine();
                    try {
                        File f = new File(filename);
                        f.createNewFile();
                        PrintWriter pw = new PrintWriter(new FileWriter(f, false));
                        pw.println("Participants,Message Sender,Timestamp,Contents\n");

                        pw.close();
                    } catch (IOException e) {
                        System.out.println("Error writing to file.");
                    }
                    break;
                case "3":
                    //Block tutor
                    break;
                case "4":
                    // Go back to view list of users or searchl
                    loop = false;
                    System.out.println("Leaving conversation options.");
                    break;
                case "5":
                    System.out.println("Thank you for using TutorFinder! Goodbye.");
                    return;
                default:
                    System.out.println("Please enter a valid input!");
                    break;
            }
        } while (loop);
    }
}
