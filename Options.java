import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
                    System.out.println(getConversation());
                    boolean inConvo = true;
                    do {
                        System.out.println("""
                                (1) Send Message
                                (2) Go Back""");
                        String convoOption = scanner.nextLine();
                        switch (convoOption) {
                            case "1":
                                // Send message
                                System.out.println("Enter your message:");
                                String message = scanner.nextLine();
                                
                                break;
                            case "2":
                                // Go back to viewMenu();
                                inConvo = false;
                                viewMenu();
                                break;
                            default:
                                System.out.println("Please enter a valid input!");
                                break;
                        }

                    } while (inConvo);
                    
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

    public String getConversation() {
        String convoFileName = userTerminal.getUsername() + "_" + userSelected.getUsername() + ".txt";
        String convo = "";
        try {
            File f = new File(convoFileName);
            if(!f.exists()) {
                f.createNewFile();
            }
            BufferedReader bfr = new BufferedReader(new FileReader(convoFileName));
            String line;
            while ((line = bfr.readLine()) != null) {
                convo += line + "\n";
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
            return null;
        }
        return convo;
    }

    public void export(String senderName, String recipientName, String fileName, File csvFile) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(csvFile, true));
            BufferedReader bfr = new BufferedReader(new FileReader(fileName))
            String line = bfr.readLine();
            while (line != null) {
                String time = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                String contents = line.substring(line.indexOf(":") + 1);
                //Handle special characters
                if (contents.contains(",") || contents.contains("\"") || contents.contains("'")) {
                    contents = contents.replace("\"", "\"\"");
                    contents = "\"" + contents + "\"";
                }

                pw.append(senderName + " and " + recipientName + "," + senderName + "," + time + "," +  contents);

                line = bfr.readLine();
            }
            bfr.close();
            pw.close();
        } catch (IOException e) {
            System.out.println("Error exporting file.");
            return;
        }
    }

}
