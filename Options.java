import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class is where the actual messaging happens. The user is given several
 * options related to messaging (covered in the descriptions below) including,
 * but not limited to, sending a message, editing a message, and blocking a
 * user.
 *
 * @author Niharika Raj, Saahil Mathur, Sam Balakhanei, Abhi Tandon
 * @version December 8, 2023
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
                conversationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JTextArea conversationTextArea = new JTextArea();
                conversationTextArea.setEditable(false);
                conversationTextArea.setText(conversation);
                conversationTextArea.setLineWrap(true);
                conversationTextArea.setWrapStyleWord(true);
                JScrollPane scrollPane = new JScrollPane(conversationTextArea);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                conversationFrame.add(scrollPane, BorderLayout.CENTER);

                conversationFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        pw.println("Back");
                        pw.flush();
                    }
                });

                JButton sendButton = new JButton("Send Message");
                sendButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pw.println("Send");
                        pw.flush();
                        String message = JOptionPane.showInputDialog(null,
                                "Enter your message:",
                                "Send Message", JOptionPane.QUESTION_MESSAGE);
                        if ((message == null) || (message.isEmpty())) {
                            JOptionPane.showMessageDialog(null,
                                    "Message cannot be empty!", "Send Message",
                                    JOptionPane.ERROR_MESSAGE);
                            pw.println("Cancel");
                            pw.flush();
                            return;
                        } else {
                            pw.println("Proceed");
                            pw.flush();
                        }
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
                            JOptionPane.showMessageDialog(null,
                                    "There are no messages to edit!", "Edit Message",
                                    JOptionPane.ERROR_MESSAGE);
                            pw.println("Cancel");
                            pw.flush();
                            return;
                        } else {
                            pw.println("Proceed");
                            pw.flush();
                        }
                        Object[] messageArray = messages.toArray();
                        String message = (String) JOptionPane.showInputDialog(null,
                                "Choose the message you want to edit:",
                                "Edit Message", JOptionPane.QUESTION_MESSAGE, null,
                                messageArray, messageArray[0]);
                        if ((message == null)) {
                            pw.println("Cancel");
                            pw.flush();
                            return;
                        } else {
                            pw.println("Proceed");
                            pw.flush();
                        }
                        String newMessage = JOptionPane.showInputDialog(null,
                                "Enter your new message:",
                                "Edit Message", JOptionPane.QUESTION_MESSAGE);
                        if ((newMessage == null) || (newMessage.isEmpty())) {
                            JOptionPane.showMessageDialog(null,
                                    "Message cannot be empty!", "Edit Message",
                                    JOptionPane.ERROR_MESSAGE);
                            pw.println("Cancel");
                            pw.flush();
                            return;
                        } else {
                            pw.println("Proceed");
                            pw.flush();
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
                            JOptionPane.showMessageDialog(null,
                                    "There are no messages to delete!", "Delete Message",
                                    JOptionPane.ERROR_MESSAGE);
                            pw.println("Cancel");
                            pw.flush();
                            return;
                        } else {
                            pw.println("Proceed");
                            pw.flush();
                        }
                        Object[] messageArray = messages.toArray();
                        String message = (String) JOptionPane.showInputDialog(null,
                                "Choose the message you want to delete:",
                                "Delete Message", JOptionPane.QUESTION_MESSAGE, null,
                                messageArray, messageArray[0]);
                        if ((message == null)) {
                            pw.println("Cancel");
                            pw.flush();
                            return;
                        } else {
                            pw.println("Proceed");
                            pw.flush();
                        }
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
                        pw.println("Refresh");
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

                JButton importButton = new JButton("Import Message");
                importButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pw.println("Import");
                        pw.flush();
                        String filename = JOptionPane.showInputDialog(null,
                                "Enter the file name to import the message from:",
                                "Import Message", JOptionPane.QUESTION_MESSAGE);
                        if ((filename == null) || (filename.isEmpty())) {
                            JOptionPane.showMessageDialog(null,
                                    "File name cannot be empty!", "Import Message",
                                    JOptionPane.ERROR_MESSAGE);
                            pw.println("Cancel");
                            pw.flush();
                            return;
                        } else {
                            pw.println("Proceed");
                            pw.flush();
                        }
                        pw.println(filename);
                        pw.flush();
                        try {
                            String messageImport = bfr.readLine();
                            if (messageImport.equals("File not found")) {
                                JOptionPane.showMessageDialog(null,
                                        "Error importing file.", "Import Message",
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
                        String message = JOptionPane.showInputDialog(null,
                                "Enter the phrase you want to filter:",
                                "Filter Message", JOptionPane.QUESTION_MESSAGE);
                        if ((message == null) || (message.isEmpty())) {
                            JOptionPane.showMessageDialog(null, "Message cannot be empty!", "Filter Message",
                                    JOptionPane.ERROR_MESSAGE);
                            pw.println("Cancel");
                            pw.flush();
                            return;
                        } else {
                            pw.println("Proceed");
                            pw.flush();
                        }
                        String filterMsg = JOptionPane.showInputDialog(null,
                                "Enter the phrase you want to replace it with:",
                                "Filter Message", JOptionPane.QUESTION_MESSAGE);
                        if ((filterMsg == null) || (filterMsg.isEmpty())) {
                            JOptionPane.showMessageDialog(null, "Message cannot be empty!", "Filter Message",
                                    JOptionPane.ERROR_MESSAGE);
                            pw.println("Cancel");
                            pw.flush();
                            return;
                        } else {
                            pw.println("Proceed");
                            pw.flush();
                        }
                        pw.println(message);
                        pw.flush();
                        pw.println(filterMsg);
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

                JButton backButton2 = new JButton("Go Back");
                backButton2.addActionListener(new ActionListener() {
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
                bottomPanel.add(backButton2);

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
                    pw.println("Cancel");
                    pw.flush();
                    return;
                } else {
                    pw.println("Proceed");
                    pw.flush();
                }
                pw.println(userSelected.getUsername());
                pw.flush();
                pw.println(filename);
                pw.flush();
                try {
                    String status = bfr.readLine();
                    if (status.equals("Export unsuccessful")) {
                        JOptionPane.showMessageDialog(null, "Error exporting file.", "Export Conversation",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Error exporting file.", "Export Conversation",
                            JOptionPane.ERROR_MESSAGE);
                }

            } else if (e.getSource() == blockButton) {
                String toBlock = userSelected.getUsername();
                String username = userTerminal.getUsername();
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to block " +
                        toBlock + "? (Y/N)", "Block " + toBlock, JOptionPane.YES_NO_OPTION);
                if (option != 0)
                    return;
                pw.println("Block");
                pw.println(toBlock);
                pw.flush();
                pw.println(username);
                pw.flush();
                try {
                    String status = bfr.readLine();
                    if (status.equals("Blocking unsuccessful")) {
                        JOptionPane.showMessageDialog(null, "Error blocking " + toBlock, "Block " + toBlock,
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(null, "Error blocking " + toBlock, "Block " + toBlock,
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == backButton) {
                pw.println("Go Back");
                pw.flush();
                frame.dispose();
                View view = new View(userTerminal.getUsername(), userTerminal, bfr, pw, true);
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

    public static void addBlocked(String toBlock) {
        blockedList.add(toBlock);
    }

    public String parseConversation(String conversation) {
        String[] messages = conversation.split("`");
        String parsedConversation = "";
        for (String message : messages) {
            parsedConversation += message + "\n";
        }
        return parsedConversation;

    }

    public ArrayList<String> parseMessages(String messages) {
        ArrayList<String> parsedMessages = new ArrayList<>();
        String[] messageArray = messages.split("`");
        for (String message : messageArray) {
            parsedMessages.add(message);
        }
        return parsedMessages;
    }
}
