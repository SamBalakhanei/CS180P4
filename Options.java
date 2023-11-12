import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Options {

    private User userTerminal;
    private User userSelected;
    private static ArrayList<String> blockedList = new ArrayList<>(0);

    public Options(User userTerminal, User userSelected) {
        this.userTerminal = userTerminal;
        this.userSelected = userSelected;
    }

    public static ArrayList<String> getBlocked() {
        ArrayList<String> blocked = new ArrayList<>(0);
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(new File("blocked-usernames.txt")));
            String line = bfr.readLine();
            while (line != null) {
                blocked.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blocked;
    }

    public void viewMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        String option;
        BufferedReader bfr = new BufferedReader(new FileReader("blocked-usernames.txt"));
        String line = bfr.readLine();
        while (line != null) {
            blockedList.add(line);
            line = bfr.readLine();
        }
        do {
            for (String c : blockedList) {
                if(c.split(":")[0].equals(this.userTerminal.getUsername()) && 
                c.split(":")[1].equals(this.userSelected.getUsername())) {
                    System.out.println("This user is blocked!");
                    break;
                } else if(c.split(":")[1].equals(this.userTerminal.getUsername()) && 
                c.split(":")[0].equals(this.userSelected.getUsername())) {
                    System.out.println("This user has blocked you!");
                    break;
                }

            }
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
                        PrintWriter pw = new PrintWriter(new FileWriter(f, false));
                        pw.println("Participants,Message Sender,Timestamp,Contents\n");
                        export(this.userTerminal.getUsername(), this.userSelected.getUsername(), senderConvoFileName, f);
                        pw.close();
                    } catch (IOException e) {
                        System.out.println("Error writing to file.");
                    }
                    break;
                case "3":
                    //Block tutor
                    PrintWriter pw = new PrintWriter(new FileWriter(new File("blocked-usernames.txt"), true));
                    System.out.println("You would like to block " + this.userSelected.getUsername() + "? (Y/N)");
                    String choice = scanner.nextLine();
                    if (choice.equalsIgnoreCase("N")) break;
                    blockedList.add(this.userTerminal.getUsername() + ":" + this.userSelected.getUsername());
                    pw.append(this.userTerminal.getUsername() + ":" + this.userSelected.getUsername() + "\n");
                    break;
                case "4":
                    // Go back to view list of users or search
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
            BufferedReader bfr = new BufferedReader(new FileReader(fileName));
            String line = bfr.readLine();
            while (line != null) {
                String time = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                String contents = line.substring(line.indexOf(":") + 1);
                //Handle special characters
                if (contents.contains(",") || contents.contains("\"") || contents.contains("'")) {
                    contents = contents.replace("\"", "\"\"");
                    contents = "\"" + contents + "\"";
                }

                pw.append(senderName + " and " + recipientName + "," + senderName + "," + time + "," +  contents + "\n");

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
