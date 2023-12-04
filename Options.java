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
    private BufferedReader bfr;
    private PrintWriter pw;

    public Options(User userTerminal, User userSelected, BufferedReader bfr, PrintWriter pw) {
        this.userTerminal = userTerminal;
        this.userSelected = userSelected;
        this.senderConvoFileName = userTerminal.getUsername() + "_" + userSelected.getUsername() + ".txt";
        this.receiverConvoFileName = userSelected.getUsername() + "_" + userTerminal.getUsername() + ".txt";
        this.filter = "";
        this.replacement = "";
        this.bfr = bfr;
        this.pw = pw;
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == viewButton) {

                pw.println("View");
                pw.flush();
                String conversation = "";
                try {
                    String serverConvo = bfr.readLine();
                    conversation = parseConversation(serverConvo);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                JFrame conversationFrame = new JFrame("View Conversation");
                conversationFrame.setSize(600, 400);
                conversationFrame.setLayout(new BorderLayout());
                conversationFrame.setVisible(true);
                conversationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JTextArea conversationTextArea = new JTextArea();
                conversationTextArea.setEditable(false);
                conversationTextArea.setText(conversation);
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
                        pw.println("Send");
                        pw.flush();
                        pw.println(message);
                        pw.flush();
                        String conversation = "";
                        try {
                            String serverConvo = bfr.readLine();
                            conversation = parseConversation(serverConvo);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        conversationTextArea.setText(conversation);
                    }
                });

                JButton editButton = new JButton("Edit Message");
                editButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pw.println("Edit");
                        pw.flush();
                        ArrayList<String> messages = new ArrayList<>();
                        try {
                            String serverMessages = bfr.readLine();
                            messages = parseMessages(serverMessages);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
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
                        pw.println(message);
                        pw.flush();
                        pw.println(newMessage);
                        pw.flush();
                        String conversation = "";
                        try {
                            String serverConvo = bfr.readLine();
                            conversation = parseConversation(serverConvo);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        conversationTextArea.setText(conversation);
                    }
                });

                JButton deleteButton = new JButton("Delete Message");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pw.println("Delete");
                        pw.flush();
                        ArrayList<String> messages = new ArrayList<>();
                        try {
                            String serverMessages = bfr.readLine();
                            messages = parseMessages(serverMessages);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        if (messages.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "There are no messages to delete!", "Delete Message",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Object[] messageArray = messages.toArray();
                        String message = (String) JOptionPane.showInputDialog(null, "Choose the message you want to edit:",
                                "Edit Message", JOptionPane.QUESTION_MESSAGE, null,
                                messageArray, messageArray[0]);
                        pw.println(message);
                        pw.flush();
                        String conversation = "";
                        try {
                            String serverConvo = bfr.readLine();
                            conversation = parseConversation(serverConvo);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        conversationTextArea.setText(conversation);
                    }
                });

                JButton refreshButton = new JButton("Refresh");
                refreshButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String conversation = "";
                        try {
                            String serverConvo = bfr.readLine();
                            conversation = parseConversation(serverConvo);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        conversationTextArea.setText(conversation);
                    }
                });

                JButton importButton = new JButton("Import Message");
                importButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pw.println("Import");
                        pw.flush();
                        String filename = JOptionPane.showInputDialog(null, "Enter the file name to import the message from:",
                                "Import Message", JOptionPane.QUESTION_MESSAGE);
                        if ((filename == null) || (filename.isEmpty())) {
                            JOptionPane.showMessageDialog(null, "File name cannot be empty!", "Import Message",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        pw.println(filename);
                        pw.flush();
                        try {
                            String messageImport = bfr.readLine();
                            if (messageImport.equals("File not found")) {
                                JOptionPane.showMessageDialog(null, "Error importing file.", "Import Message",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            } else {
                                String serverConvo = bfr.readLine();
                                String conversation = parseConversation(serverConvo);
                                conversationTextArea.setText(conversation);
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                JButton filterButton = new JButton("Filter Message");
                filterButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pw.println("Filter");
                        pw.flush();
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
                        pw.println(message);
                        pw.flush();
                        pw.println(replacement);
                        pw.flush();
                        String conversation = "";
                        try {
                            String serverConvo = bfr.readLine();
                            conversation = parseConversation(serverConvo);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        conversationTextArea.setText(conversation);
                    }
                });

                JButton backButton = new JButton("Go Back");
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pw.println("Back");
                        pw.flush();
                        conversationFrame.dispose();
                    }
                });

                JPanel bottomPanel = new JPanel();

                bottomPanel.add(sendButton);
                bottomPanel.add(editButton);
                bottomPanel.add(deleteButton);
                bottomPanel.add(importButton);
                bottomPanel.add(refreshButton);
                bottomPanel.add(filterButton);
                bottomPanel.add(backButton);

                conversationFrame.add(bottomPanel, BorderLayout.SOUTH);
                conversationFrame.add(conversationTextArea, BorderLayout.CENTER);


            } else if (e.getSource() == exportButton) {
                pw.println("Export");
                pw.flush();
                String filename = JOptionPane.showInputDialog(null, "Enter the filepath to export the conversation to:",
                        "Export Conversation", JOptionPane.QUESTION_MESSAGE);
                if ((filename == null) || (filename.isEmpty())) {
                    JOptionPane.showMessageDialog(null, "File name cannot be empty!", "Export Conversation",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                pw.println(userSelected.getUsername());
                pw.flush();
                pw.println(filename);
                pw.flush();

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
                pw.println("Back");
                pw.flush();
                View view = new View(userTerminal.getUsername(), userTerminal, bfr, pw);
                view.run();
            }
        }
    };

    @Override
    public void run() {
        pw.println(senderConvoFileName);
        pw.flush();
        pw.println(receiverConvoFileName);
        pw.flush();
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

    public String parseConversation(String conversation) {
        String[] messages = conversation.split(",");
        String parsedConversation = "";
        for (String message : messages) {
            parsedConversation += message + "\n";
        }
        return parsedConversation;

    }

    public ArrayList<String> parseMessages(String messages) {
        ArrayList<String> parsedMessages = new ArrayList<>();
        String[] messageArray = messages.split(",");
        for (String message : messageArray) {
            parsedMessages.add(message);
        }
        return parsedMessages;
    }
}
