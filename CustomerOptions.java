import java.io.*;
import java.util.Scanner;

public class CustomerOptions {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        boolean loop = true;
        String option;
        do {
            System.out.println("""
                    (1) Create New Conversation
                    (2) Edit Conversation
                    (3) Delete Conversation
                    (4) Export Conversation
                    (5) Block Tutor
                    (6) Go Back""");
            option = s.nextLine();
            switch (option) {
                case "1":
                    //Make new conversation with a tutor only if that conversation doesn't already exist
                    break;
                case "2":
                    //Edit existing conversation with tutor
                    break;
                case "3":
                    //Delete existing conversation with tutor
                    break;
                case "4":
                    //Export conversation to a CSV file.
                    System.out.println("Enter the filepath to export the conversation to:");
                    String filename = s.nextLine();
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
                case "5":
                    //Block tutor (Cannot send messages; will become invisible for blocked tutor)
                    break;
                case "6":
                    loop = false;
                    System.out.println("Leaving conversation options.");
                    break;
                default:
                    System.out.println("Please enter a valid input!");
                    break;
            }
        } while (loop);
    }
}
