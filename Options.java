import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Options {

    private User userTerminal;
    private User userSelected;
    private String senderConvoFileName;
    private String receiverConvoFileName;

    public Options(User userTerminal, User userSelected) {
        this.userTerminal = userTerminal;
        this.userSelected = userSelected;
        this.senderConvoFileName = userTerminal.getUsername() + "_" + userSelected.getUsername() + ".txt";
        this.receiverConvoFileName = userSelected.getUsername() + "_" + userTerminal.getUsername() + ".txt";
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
                                (2) Edit message
                                (3) Delete message
                                (4) Go Back""");
                        String convoOption = scanner.nextLine();
                        switch (convoOption) {
                            case "1":
                                // Send message
                                System.out.println("Enter your message:");
                                String message = scanner.nextLine();
                                sendMessage(message);
                                break;
                            case "2":
                                //Edit message
                                System.out.println("Which message do you want to edit?");
                                int bound = displayMessages();
                                String messageEdit = "";
                                boolean valid = true;
                                do {
                                    try {
                                        int messageIndex = Integer.parseInt(scanner.nextLine());
                                        if (messageIndex < 1 || messageIndex > bound) {
                                            System.out.println("Please enter a a number between 1 and " + bound + "!");
                                            valid = false;
                                        } else {
                                            valid = true;
                                        }
                                        messageEdit = findMessage(messageIndex);
                                    } catch (NumberFormatException e) {
                                        System.out.println("Please enter a valid input!");
                                        valid = false;
                                    }
                                } while (!valid);
                                System.out.println("Enter your new message:");
                                String newMessage = scanner.nextLine();
                                editMessage(messageEdit, newMessage);
                                break;
                            case "3":
                                // Delete message
                                System.out.println("Which message do you want to delete?");
                                int bound2 = displayMessages();
                                String messageDelete = "";
                                boolean valid2 = true;
                                do {
                                    try {
                                        int messageIndex = Integer.parseInt(scanner.nextLine());
                                        if (messageIndex < 1 || messageIndex > bound2) {
                                            System.out.println("Please enter a a number between 1 and " + bound2 + "!");
                                            valid2 = false;
                                        } else {
                                            valid2 = true;
                                        }
                                        messageDelete = findMessage(messageIndex);
                                    } catch (NumberFormatException e) {
                                        System.out.println("Please enter a valid input!");
                                    }

                                    deleteMessage(messageDelete);
                                } while (!valid2);
                                break;
                            case "4":
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
        String convo = "";
        try {
            File f = new File(senderConvoFileName);
            if (!f.exists()) {
                f.createNewFile();
            }
            BufferedReader bfr = new BufferedReader(new FileReader(senderConvoFileName));
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

    public void sendMessage(String message) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(senderConvoFileName, true));
            LocalDateTime timestamp = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTimestamp = timestamp.format(formatter);
            pw.println(userTerminal.getUsername() + "(" + formattedTimestamp + ")" + ":" + message);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    //Don't touch, not tested yet
    public void editMessage(String message, String newMessage) {
        File current = new File(senderConvoFileName);
        File f = new File("temp.txt");
        try (BufferedReader bfr = new BufferedReader(new FileReader(senderConvoFileName))) {
            PrintWriter pw = new PrintWriter(new FileWriter(f));
            String line = bfr.readLine();
            while (line != null) {
                if (line.equals(message)) {
                    LocalDateTime timestamp = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formattedTimestamp = timestamp.format(formatter);
                    pw.println(userTerminal.getUsername() + "(" + formattedTimestamp + ")" + ":" + newMessage);
                    pw.flush();
                } else {
                    pw.println(line);
                    pw.flush();
                }
                line = bfr.readLine();
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        if (!current.delete()) {
            System.out.println("Error deleting file.");
        }
        if (!f.renameTo(current)) {
            System.out.println("Error renaming file.");
        }
    }

    //Don't touch, not tested yet
    public void deleteMessage(String message) {
        try (BufferedReader bfr = new BufferedReader(new FileReader(senderConvoFileName))) {
            PrintWriter pw = new PrintWriter(new FileWriter(senderConvoFileName, false));
            String line = bfr.readLine();
            while (line != null) {
                if (!line.substring(line.indexOf(":")).equals(message)) {
                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    public int displayMessages() {
        try (BufferedReader bfr = new BufferedReader(new FileReader(senderConvoFileName))) {
            String line;
            int i = 1;
            while ((line = bfr.readLine()) != null) {
                System.out.println("(" + i + ") " + line);
                i++;
            }
            return i;
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return 0;
    }

    public String findMessage(int index) {
        try (BufferedReader bfr = new BufferedReader(new FileReader(senderConvoFileName))) {
            String line;
            int i = 1;
            while ((line = bfr.readLine()) != null) {
                if (i == index) {
                    return line;
                }
                i++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return null;
    }

}
