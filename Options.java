import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Options {

    private User userTerminal;
    private User userSelected;
    private static ArrayList<String> blockedList = new ArrayList<>(0);
    private String senderConvoFileName;
    private String receiverConvoFileName;

    public Options(User userTerminal, User userSelected) {
        this.userTerminal = userTerminal;
        this.userSelected = userSelected;
        this.senderConvoFileName = userTerminal.getUsername() + "_" + userSelected.getUsername() + ".txt";
        this.receiverConvoFileName = userSelected.getUsername() + "_" + userTerminal.getUsername() + ".txt";
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
        boolean shouldExit = false;
        String option;
        try (BufferedReader bfr = new BufferedReader(new FileReader("blocked-usernames.txt"))) {
            String line = bfr.readLine();
            while (line != null) {
                blockedList.add(line);
                line = bfr.readLine();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        do {
            for (String c : blockedList) {
                if (c.split(":")[0].equals(this.userTerminal.getUsername()) &&
                        c.split(":")[1].equals(this.userSelected.getUsername())) {
                    System.out.println("This user is blocked!");
                    break;
                } else if (c.split(":")[1].equals(this.userTerminal.getUsername()) &&
                        c.split(":")[0].equals(this.userSelected.getUsername())) {
                    System.out.println("This user has blocked you!");
                    break;
                }

            }
            System.out.println("""
                    (1) View Conversation
                    (2) Export Conversation
                    (3) Block User
                    (4) Go Back
                    (5) Exit Application""");
            option = scanner.nextLine();
            switch (option) {
                case "1":
                    // View conversation
                    shouldExit = false;
                    System.out.println(getConversation());
                    boolean inConvo = true;
                    do {
                        System.out.println("""
                                (1) Send Message
                                (2) Edit message
                                (3) Delete message
                                (4) Import message from text file
                                (5) Go Back""");
                        String convoOption = scanner.nextLine();
                        switch (convoOption) {
                            case "1":
                                // Send message
                                System.out.println("Enter your message:");
                                String message = scanner.nextLine();
                                sendMessage(message);
                                break;
                            case "2":
                                // Edit message
                                System.out.println("Which message do you want to edit?");
                                int bound = displayMessages();
                                if (bound == 1) {
                                    System.out.println("There are no messages to edit!");
                                    break;
                                }
                                System.out.println("(" + bound + ") Go Back");
                                String messageEdit = "";
                                boolean valid = true;
                                do {
                                    try {
                                        int messageIndex = Integer.parseInt(scanner.nextLine());
                                        if (messageIndex < 1 || messageIndex > bound) {
                                            System.out.println("Please enter a number between 1 and " + bound + "!");
                                            valid = false;
                                        } else if (messageIndex == bound) {
                                            break;
                                        } else {
                                            System.out.println("Enter your new message:");
                                            String newMessage = scanner.nextLine();
                                            messageEdit = findMessage(messageIndex);
                                            editMessage(messageEdit, newMessage);
                                            valid = true;
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Please enter a valid input!");
                                        valid = false;
                                    }
                                } while (!valid);

                                break;
                            case "3":
                                // Delete message
                                System.out.println("Which message do you want to delete?");
                                int bound2 = displayMessages();
                                if (bound2 == 1) {
                                    System.out.println("There are no messages to delete!");
                                    break;
                                }
                                System.out.println("(" + bound2 + ") Go Back");
                                String messageDelete = "";
                                boolean valid2 = true;
                                do {
                                    try {
                                        int messageIndex = Integer.parseInt(scanner.nextLine());
                                        if (messageIndex < 1 || messageIndex > bound2) {
                                            System.out.println("Please enter a number between 1 and " + bound2 + "!");
                                            valid2 = false;
                                        } else if (messageIndex == bound2) {
                                            break;
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
                                // Import message from text file
                                System.out.println("Enter the file name to import the message from:");
                                String filename = scanner.nextLine();
                                String messageImport = importFile(filename);
                                if (messageImport == null) {
                                    System.out.println("Error importing file.");
                                    break;
                                } else {
                                    sendMessage(messageImport);
                                }
                                break;
                            case "5":
                                // Go back to viewMenu();
                                inConvo = false;
                                break;
                            default:
                                System.out.println("Please enter a valid input!");
                                break;
                        }

                    } while (inConvo);
                    break;
                case "2":
                    // Export conversation to a CSV file.
                    shouldExit = false;
                    System.out.println("Enter the filepath to export the conversation to:");
                    String filename = scanner.nextLine();
                    try {
                        File f = new File(filename);
                        PrintWriter pw = new PrintWriter(new FileWriter(f, false));
                        pw.println("Participants,Message Sender,Timestamp,Contents");
                        pw.close();
                        export(this.userTerminal.getUsername(), this.userSelected.getUsername(), senderConvoFileName,
                                f);
                    } catch (IOException e) {
                        System.out.println("Error writing to file.");
                    }
                    break;
                case "3":
                    // Block tutor
                    try (PrintWriter pw = new PrintWriter(new FileWriter(new File("blocked-usernames.txt"), true))) {
                        do {
                            System.out.println("Are you sure you want to block " + this.userSelected.getUsername() + "? (Y/N)");
                            String choice = scanner.nextLine();
                            if (choice.equalsIgnoreCase("N"))
                                break;
                            else if (choice.equalsIgnoreCase("Y")) {
                                blockedList.add(this.userTerminal.getUsername() + ":" + this.userSelected.getUsername());
                                pw.append(this.userTerminal.getUsername() + ":" + this.userSelected.getUsername() + "\n");
                                break;
                            } else {
                                System.out.println("Please enter a valid input!");
                            }
                        } while (true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "4":
                    // Go back to view list of users or search
                    shouldExit = true;
                    System.out.println("Leaving conversation options.");
                    if (userTerminal.getUserType()) {
                        View.findTutor(userTerminal.getUsername(), userTerminal);
                    } else {
                        View.findStudent(userTerminal.getUsername(), userTerminal);
                    }
                    break;
                case "5":
                    shouldExit = true;
                    System.out.println("Thank you for using TutorFinder! Goodbye.");
                    return;
                default:
                    System.out.println("Please enter a valid input!");
                    break;
            }
        } while (!shouldExit);
    }

    public String getConversation() {
        String convo = "";
        try {
            File f1 = new File(senderConvoFileName);
            File f2 = new File(receiverConvoFileName);
            if (!f1.exists() && !f2.exists()) {
                f1.createNewFile();
                f2.createNewFile();
            } else if (f2.exists() && !f1.exists()) {
                BufferedReader bfr = new BufferedReader(new FileReader(receiverConvoFileName));
                PrintWriter pw = new PrintWriter(new FileWriter(senderConvoFileName, true));
                String line;
                while ((line = bfr.readLine()) != null) {
                    pw.println(line);
                    pw.flush();
                }
                bfr.close();
                pw.close();
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
    public void export(String senderName, String recipientName, String fileName, File csvFile) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(csvFile, true));
            BufferedReader bfr = new BufferedReader(new FileReader(fileName));
            String line = bfr.readLine();
            while (line != null) {
                String time = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                String contents = line.substring(line.indexOf(")") + 1);
                String sender = contents.substring(contents.indexOf("-") + 1, contents.indexOf(":"));
                String message = contents.substring(contents.indexOf(":") + 1);
                // Handle special characters
                if (contents.contains(",")) {
                    //contents = contents.replace("\"", "\"\"");
                    message = "\"" + contents + "\"";
                }

                pw.append(senderName).append(" and ").append(recipientName).append(",").append(sender).append(",").append(time).append(",").append(message).append("\n");

                line = bfr.readLine();
            }
            bfr.close();
            pw.close();
        } catch (IOException e) {
            System.out.println("Error exporting file.");
            return;
        }
    }

    public void sendMessage(String message) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(senderConvoFileName, true));
            PrintWriter pw2 = new PrintWriter(new FileWriter(receiverConvoFileName, true));
            LocalDateTime timestamp = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTimestamp = timestamp.format(formatter);
            if (userTerminal.getUserType()) {
                pw.println("(" + formattedTimestamp + ") " + "Student-" + userTerminal.getUsername() + ": " + message);
                pw.flush();
                pw2.println("(" + formattedTimestamp + ") " + "Student-" + userTerminal.getUsername() + ": " + message);
                pw2.flush();
            } else {
                pw.println("(" + formattedTimestamp + ") " + "Tutor-" + userTerminal.getUsername() + ": " + message);
                pw.flush();
                pw2.println("(" + formattedTimestamp + ") " + "Tutor-" + userTerminal.getUsername() + ": " + message);
                pw2.flush();
            }
            pw.close();
            pw2.close();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    public void editMessage(String message, String newMessage) {
        File current = new File(senderConvoFileName);
        File f = new File("temp.txt");
        File currentTwo = new File(receiverConvoFileName);
        File fTwo = new File("tempTwo.txt");
        try (BufferedReader bfr = new BufferedReader(new FileReader(senderConvoFileName))) {
            PrintWriter pw = new PrintWriter(new FileOutputStream(f));
            PrintWriter pw2 = new PrintWriter(new FileOutputStream(fTwo));
            String line = bfr.readLine();
            while (line != null) {
                if (line.equals(message)) {
                    LocalDateTime timestamp = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formattedTimestamp = timestamp.format(formatter);
                    int hyphenIndex = line.indexOf("-", line.indexOf(')') + 2);
                    if (hyphenIndex != -1) {
                        String userType = line.substring(line.indexOf(")") + 2, hyphenIndex);
                        if (userType.equals("Student")) {
                            pw.println("(" + formattedTimestamp + ") " + "Student-" + userTerminal.getUsername() + ": " + newMessage);
                            pw.flush();
                            pw2.println("(" + formattedTimestamp + ") " + "Student-" + userTerminal.getUsername() + ": " + newMessage);
                            pw2.flush();
                        } else if (userType.equals("Tutor")) {
                            pw.println("(" + formattedTimestamp + ") " + "Tutor-" + userTerminal.getUsername() + ": " + newMessage);
                            pw.flush();
                            pw2.println("(" + formattedTimestamp + ") " + "Tutor-" + userTerminal.getUsername() + ": " + newMessage);
                            pw2.flush();
                        }
                    }
                } else {
                    pw.println(line);
                    pw.flush();
                    pw2.println(line);
                    pw2.flush();
                }
                line = bfr.readLine();
            }
            pw.close();
            pw2.close();
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        if (!current.delete()) {
            System.out.println("Error deleting file.");
        }
        if (!f.renameTo(current)) {
            System.out.println("Error renaming file.");
        }
        if (!currentTwo.delete()) {
            System.out.println("Error deleting file.");
        }
        if (!fTwo.renameTo(currentTwo)) {
            System.out.println("Error renaming file.");
        }
    }

    public void deleteMessage(String message) {
        File current = new File(senderConvoFileName);
        File f = new File("temp.txt");
        try (BufferedReader bfr = new BufferedReader(new FileReader(senderConvoFileName))) {
            PrintWriter pw = new PrintWriter(new FileWriter(f, true));
            String line = bfr.readLine();
            while (line != null) {
                if (!line.equals(message)) {
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

    public int displayMessages() {
        try (BufferedReader bfr = new BufferedReader(new FileReader(senderConvoFileName))) {
            String line;
            int i = 1;
            while ((line = bfr.readLine()) != null) {
                if (userTerminal.getUserType()) {
                    if (line.contains("Student-" + userTerminal.getUsername())) {
                        System.out.println("(" + i + ") " + line);
                        i++;
                    }
                } else {
                    if (line.contains("Tutor-" + userTerminal.getUsername())) {
                        System.out.println("(" + i + ") " + line);
                        i++;
                    }
                }
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
                if (userTerminal.getUserType()) {
                    if (line.contains("Student-" + userTerminal.getUsername())) {
                        if (i == index) {
                            return line;
                        }
                        i++;
                    }
                } else {
                    if (line.contains("Tutor-" + userTerminal.getUsername())) {
                        if (i == index) {
                            return line;
                        }
                        i++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return null;
    }

    public String importFile(String filename) {
        String message = "";
        try (BufferedReader bfr = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                message += line;
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return message;
    }

}
