import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.awt.*;

/**
 * This class is where the actual messaging happens. The user is given several
 * options related to messaging (covered in the descriptions below) including,
 * but not limited to, sending a message, editing a message, and blocking a user.
 *
 * @author Niharika Raj, Saahil Mathur, Sam Balakhanei, Abhi Tandon
 * @version November 13, 2023
 */

public class Options extends JComponent implements Runnable {
    private JFrame frame;
    private JButton viewButton;
    private JButton exportButton;
    private JButton blockButton;
    private JButton backButton;
    private User userTerminal;
    private User userSelected;
    private static ArrayList<String> blockedList = new ArrayList<>(0);
    private String senderConvoFileName;
    private String receiverConvoFileName;
    private String filter;
    private String replacement;

    public Options(User userTerminal, User userSelected) {
        this.userTerminal = userTerminal;
        this.userSelected = userSelected;
        this.senderConvoFileName = userTerminal.getUsername() + "_" + userSelected.getUsername() + ".txt";
        this.receiverConvoFileName = userSelected.getUsername() + "_" + userTerminal.getUsername() + ".txt";
        this.filter = "";
        this.replacement = "";
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == viewButton) {
                JFrame conversationFrame = new JFrame("View Conversation");
                conversationFrame.setSize(600, 400);
                conversationFrame.setLayout(new BorderLayout());
                conversationFrame.setVisible(true);
                conversationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JTextArea conversationTextArea = new JTextArea();
                conversationTextArea.setEditable(false);
                conversationTextArea.setText(getConversation());
                conversationTextArea.setLineWrap(true);
                conversationTextArea.setWrapStyleWord(true);
                JScrollPane scrollPane = new JScrollPane(conversationTextArea);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                conversationFrame.add(scrollPane, BorderLayout.CENTER);


                JButton sendButton = new JButton("Send Message");
                sendButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String message = JOptionPane.showInputDialog(null, "Enter your message:",
                                "Send Message", JOptionPane.QUESTION_MESSAGE);
                        if ((message == null) || (message.isEmpty())) {
                            JOptionPane.showMessageDialog(null, "Message cannot be empty!", "Send Message",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        sendMessage(message);
                        conversationTextArea.setText(getConversation());
                    }
                });

                JButton editButton = new JButton("Edit Message");
                editButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ArrayList<String> messages = displayMessages();
                        if (messages.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "There are no messages to edit!", "Edit Message",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Object[] messageArray = messages.toArray();
                        String message = (String) JOptionPane.showInputDialog(null, "Choose the message you want to edit:",
                                "Edit Message", JOptionPane.QUESTION_MESSAGE, null,
                                messageArray, messageArray[0]);
                        if ((message == null)) {
                            return;
                        }
                        String newMessage = JOptionPane.showInputDialog(null, "Enter your new message:",
                                "Edit Message", JOptionPane.QUESTION_MESSAGE);
                        if ((newMessage == null) || (newMessage.isEmpty())) {
                            JOptionPane.showMessageDialog(null, "Message cannot be empty!", "Edit Message",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        editMessage(message, newMessage);
                        conversationTextArea.setText(getConversation());
                    }
                });

                JButton deleteButton = new JButton("Delete Message");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ArrayList<String> messages = displayMessages();
                        Object[] messageArray = messages.toArray();
                        String message = (String) JOptionPane.showInputDialog(null, "Choose the message you want to edit:",
                                "Edit Message", JOptionPane.QUESTION_MESSAGE, null,
                                messageArray, messageArray[0]);
                        deleteMessage(message);
                        conversationTextArea.setText(getConversation());
                    }
                });

                JButton importButton = new JButton("Import Message");
                importButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String filename = JOptionPane.showInputDialog(null, "Enter the file name to import the message from:",
                                "Import Message", JOptionPane.QUESTION_MESSAGE);
                        if ((filename == null) || (filename.isEmpty())) {
                            JOptionPane.showMessageDialog(null, "File name cannot be empty!", "Import Message",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        String messageImport = importFile(filename);
                        if (messageImport == null) {
                            JOptionPane.showMessageDialog(null, "Error importing file.", "Import Message",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        } else {
                            sendMessage(messageImport);
                            conversationTextArea.setText(getConversation());
                        }
                    }
                });
                JButton filterButton = new JButton("Filter Message");
                filterButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String message = JOptionPane.showInputDialog(null, "Enter the phrase you want to filter:",
                                "Filter Message", JOptionPane.QUESTION_MESSAGE);
                        if ((message == null) || (message.isEmpty())) {
                            JOptionPane.showMessageDialog(null, "Message cannot be empty!", "Filter Message",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        String replacement = JOptionPane.showInputDialog(null, "Enter the phrase you want to replace it with:",
                                "Filter Message", JOptionPane.QUESTION_MESSAGE);
                        if ((replacement == null) || (replacement.isEmpty())) {
                            JOptionPane.showMessageDialog(null, "Message cannot be empty!", "Filter Message",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        filterMessage(message, replacement);
                        conversationTextArea.setText(getConversation());
                    }
                });

                JButton backButton = new JButton("Go Back");
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        conversationFrame.dispose();
                    }
                });

                JPanel bottomPanel = new JPanel();

                bottomPanel.add(sendButton);
                bottomPanel.add(editButton);
                bottomPanel.add(deleteButton);
                bottomPanel.add(importButton);
                bottomPanel.add(filterButton);
                bottomPanel.add(backButton);

                conversationFrame.add(bottomPanel, BorderLayout.SOUTH);
                conversationFrame.add(conversationTextArea, BorderLayout.CENTER);


            } else if (e.getSource() == exportButton) {
                String filename = JOptionPane.showInputDialog(null, "Enter the filepath to export the conversation to:",
                        "Export Conversation", JOptionPane.QUESTION_MESSAGE);
                if ((filename == null) || (filename.isEmpty())) {
                    JOptionPane.showMessageDialog(null, "File name cannot be empty!", "Export Conversation",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    File f = new File(filename);
                    PrintWriter pw = new PrintWriter(new FileWriter(f, false));
                    pw.println("Participants,Message Sender,Timestamp,Contents");
                    pw.close();
                    export(userTerminal.getUsername(), userSelected.getUsername(), senderConvoFileName,
                            f);
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(null, "Error writing to file", "Export Conversation",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == blockButton) {
                String toBlock = userSelected.getUsername();
                String username = userTerminal.getUsername();
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to block " +
                        toBlock + "? (Y/N)", "Block " + toBlock, JOptionPane.YES_NO_OPTION);
                try (PrintWriter pw = new PrintWriter(new FileWriter(new File("blocked-usernames.txt"), true))) {
                    blockedList.add(username + ":" + toBlock);
                    pw.append(username + ":" + toBlock + "\n");
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(null, "Error blocking " + toBlock, "Block " + toBlock,
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == backButton) {
                View view = new View(userTerminal.getUsername(), userTerminal);
                view.run();
            }
        }
    };

    @Override
    public void run() {
        frame = new JFrame("Conversation Options");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        JLabel label = new JLabel("Choose from one of the options below:");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        panel.add(label);
        viewButton = new JButton("View Conversation");
        viewButton.addActionListener(actionListener);
        exportButton = new JButton("Export Conversation");
        exportButton.addActionListener(actionListener);
        blockButton = new JButton("Block " + this.userSelected.getUsername());
        blockButton.addActionListener(actionListener);
        backButton = new JButton("Go Back");
        backButton.addActionListener(actionListener);
        panel.add(viewButton);
        panel.add(exportButton);
        panel.add(blockButton);
        panel.add(backButton);
        content.add(panel, BorderLayout.CENTER);
        frame.validate();
    }

    public static ArrayList<String> getBlocked() {
        ArrayList<String> blocked = new ArrayList<>(0);
        try {
            File blockedFile = new File("blocked-usernames.txt");
            if (!blockedFile.exists()) {
                blockedFile.createNewFile();
            }
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

    /*public void viewMenu() {
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
                    return;
                } else if (c.split(":")[1].equals(this.userTerminal.getUsername()) &&
                        c.split(":")[0].equals(this.userSelected.getUsername())) {
                    System.out.println("This user has blocked you!");
                    return;
                }

            }
            System.out.println("(1) View Conversation\n(2) Export Conversation");
            System.out.println("(3) Block User\n(4) Go Back\n(5) Exit Application");

            option = scanner.nextLine();
            switch (option) {
                case "1":
                    // View conversation
                    shouldExit = false;
                    System.out.println("\n" + getConversation());
                    boolean inConvo = true;
                    do {
                        System.out.println("""
                                (1) Send Message
                                (2) Edit message
                                (3) Delete message
                                (4) Import message from text file
                                (5) Filter Messages
                                (6) Go Back""");
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
                                            System.out.println("Please enter a number between 1 and "
                                                    + bound + "!");
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
                                            System.out.println("Please enter a number between 1 and "
                                                    + bound2 + "!");
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
                                //Filter messages
                                System.out.println("Which phrase do you want to filter?");
                                filter = scanner.nextLine();
                                System.out.println("What do you want to replace it with?");
                                replacement = scanner.nextLine();
                                filterMessage(filter, replacement);
                            case "6":
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
                    try (PrintWriter pw = new PrintWriter(new FileWriter(new File("blocked-usernames.txt"),
                            true))) {
                        do {
                            System.out.println("Are you sure you want to block " + this.userSelected.getUsername() +
                                    "? (Y/N)");
                            String choice = scanner.nextLine();
                            if (choice.equalsIgnoreCase("N"))
                                break;
                            else if (choice.equalsIgnoreCase("Y")) {
                                blockedList.add(this.userTerminal.getUsername() + ":"
                                        + this.userSelected.getUsername());
                                pw.append(this.userTerminal.getUsername() + ":" + this.userSelected.getUsername()
                                        + "\n");
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
                    /*if (userTerminal.getUserType()) {
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
    } */

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
            filterMessage(filter, replacement);
            BufferedReader bfr = new BufferedReader(new FileReader(senderConvoFileName));
            String line;
            while ((line = bfr.readLine()) != null) {
                convo += line + "\n";
            }
            if (isEmpty()) {
                convo += "There are no messages to display. Send a message to start a conversation!\n";
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
                if (message.contains(",")) {
                    // contents = contents.replace("\"", "\"\"");
                    message = "\"" + message + "\"";
                }

                pw.append(senderName).append(" and ").append(recipientName).append(",").append(sender).append(",")
                        .append(time).append(",").append(message).append("\n");

                line = bfr.readLine();
            }
            bfr.close();
            pw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to file", "Export Conversation",
                    JOptionPane.ERROR_MESSAGE);
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
                            pw.println("(" + formattedTimestamp + ") " + "Student-" + userTerminal.getUsername() + ": "
                                    + newMessage);
                            pw.flush();
                            pw2.println("(" + formattedTimestamp + ") " + "Student-" + userTerminal.getUsername() + ": "
                                    + newMessage);
                            pw2.flush();
                        } else if (userType.equals("Tutor")) {
                            pw.println("(" + formattedTimestamp + ") " + "Tutor-" + userTerminal.getUsername() + ": "
                                    + newMessage);
                            pw.flush();
                            pw2.println("(" + formattedTimestamp + ") " + "Tutor-" + userTerminal.getUsername() + ": "
                                    + newMessage);
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

    public ArrayList<String> displayMessages() {
        ArrayList<String> messages = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader(senderConvoFileName))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                if (userTerminal.getUserType()) {
                    if (line.contains("Student-" + userTerminal.getUsername())) {
                        messages.add(line);
                    }
                } else {
                    if (line.contains("Tutor-" + userTerminal.getUsername())) {
                        messages.add(line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return messages;
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

    public boolean isEmpty() {
        try (BufferedReader bfr = new BufferedReader(new FileReader(senderConvoFileName))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                return false;
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return true;
    }

    public boolean filterMessage(String message, String other) {
        File current = new File(senderConvoFileName);
        File f = new File("temp.txt");
        int count = 0;
        try (BufferedReader bfr = new BufferedReader(new FileReader(senderConvoFileName))) {
            PrintWriter pw = new PrintWriter(new FileWriter(f, true));
            String line = bfr.readLine();
            while (line != null) {
                if (line.contains(message)) {
                    count++;
                    line = line.replace(message, other);
                }
                pw.println(line);
                pw.flush();
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

        return (count > 0);
    }
}
