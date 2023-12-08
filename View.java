import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class displays the view page and allows for the user to select who to converse with.
 *
 * @author Niharika Raj, Saahil Mathur, Sam Balakhanei, Abhi Tandon
 * @version December 8, 2023
 */

/**
 * major changes made: findStudent and findTutor are purely for GUI - all the background things happen in list() and search(comparisonName)
 * (this should make things slightly easier for network I/O), display() added to deal with GUI for the search feature, list and search methods no longer separate
 * for tutors and students (if statements within each method to accommodate for both)
 * <p>
 * how to call view: create a new View object with String userName, User userTerminal and then call the run method rather than findStudent or findTutor
 * <p>
 * blocked feature: Saahil - if you're trying to edit the block feature there shouldn't be any major changes to what you've done already
 * it's just been split into separate methods and duplicates have been removed. i've left the duplicate methods commented in the bottom in case there are any changes
 * that you would like to make there
 */
public class View extends JComponent implements Runnable {
    private String userName; //username
    private User userTerminal; //user using terminal
    private ArrayList<String> blocked = new ArrayList<>(); //arraylist with all blocked members

    private BufferedReader clientReader;
    private PrintWriter clientWriter;
    JFrame listOrSearch;
    JButton searchStudent; //button to search for student
    JButton listStudent; //button to list all students
    JButton searchTutor; //button to search for tutors
    JButton listTutor; //button to list all tutors
    JButton searchButton;
    JTextField searchField = new JTextField(15);
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == searchStudent) {
                clientWriter.println("search");
                clientWriter.flush();
                display();
            }
            if (e.getSource() == listStudent) {
                clientWriter.println("list");
                clientWriter.flush();
                list();
            }
            if (e.getSource() == listTutor) {
                clientWriter.println("list");
                clientWriter.flush();
                list();
            }
            if (e.getSource() == searchTutor) {
                clientWriter.println("search");
                clientWriter.flush();
                display();
            }
        }
    };
    Container content;

    public View(String userName, User userTerminal, BufferedReader clientReader, PrintWriter clientWriter, boolean calledSecondTime) {
        this.userName = userName;
        this.userTerminal = userTerminal;
        this.clientReader = clientReader;
        this.clientWriter = clientWriter;
        if (!calledSecondTime) {
            sendUserDetails();
        }
    }
    private void sendUserDetails() {
        clientWriter.println(userTerminal.getUsername());
        clientWriter.flush();
        clientWriter.println(userTerminal.getPassword());
        clientWriter.flush();
        clientWriter.println(userTerminal.getUserType());
        clientWriter.flush();
    }

    @Override
    public void run() {
        listOrSearch = new JFrame("Login or Signup");
        listOrSearch.setSize(600, 400);
        listOrSearch.setLocationRelativeTo(null);
        listOrSearch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listOrSearch.setVisible(true);
        content = listOrSearch.getContentPane();
        content.setLayout(new BorderLayout());
        JPanel listORSearch = new JPanel();
        listORSearch.setLayout(new GridLayout(3, 1));
        JLabel select = new JLabel("Select one of the options below:");
        select.setHorizontalAlignment(JLabel.CENTER);
        select.setVerticalAlignment(JLabel.CENTER);
        listORSearch.add(select);
        if (!userTerminal.getUserType()) {
            this.findStudent();
            listORSearch.add(listStudent);
            listORSearch.add(searchStudent);
        } else {
            this.findTutor();
            listORSearch.add(listTutor);
            listORSearch.add(searchTutor);
        }
        content.add(listORSearch, BorderLayout.CENTER);
        listORSearch.validate();
    }

    public void findStudent() {
        for (String c : Options.getBlocked()) {
            blocked.add(c);
        }
        listStudent = new JButton("List Student");
        listStudent.addActionListener(actionListener);
        searchStudent = new JButton("Search Student");
        searchStudent.addActionListener(actionListener);
    }

    public void findTutor() {
        for (String c : Options.getBlocked()) {
            blocked.add(c);
        }
        listTutor = new JButton("List Tutor");
        listTutor.setAlignmentX(300);
        listTutor.addActionListener(actionListener);
        searchTutor = new JButton("Search Tutor");
        searchTutor.addActionListener(actionListener);
    }

    //all frontend
    public void display() {
        content.removeAll();
        searchField.setText("exampleUser");
        JLabel searchLabel = new JLabel("Search for a user: ");
        JPanel search = new JPanel(new GridBagLayout());
        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = searchField.getText();
                clientWriter.println(name);
                clientWriter.flush();
                search();
            }
        });
        search.add(searchLabel);
        search.add(searchField);
        search.add(searchButton);
        content.add(search);
        content.revalidate();
        content.repaint();
    }


    public void list() {
        String choice = "";
        Options options;
        try {
            String foundPeopleString = clientReader.readLine();
            //server returns an array to frontend
            if (foundPeopleString.isEmpty() && userTerminal.getUserType()) {
                JOptionPane.showMessageDialog(null, "Please add at least one tutor before using the program.", "No Tutors Found", JOptionPane.ERROR_MESSAGE);
                listOrSearch.dispose();
                return;
            } else if (foundPeopleString.isEmpty() && !userTerminal.getUserType()) {
                JOptionPane.showMessageDialog(null, "Please add at least one student before using the program.", "No Students Found", JOptionPane.ERROR_MESSAGE);
                listOrSearch.dispose();
                return;
            }
            if (!userTerminal.getUserType()) {
                String[] foundPeople = foundPeopleString.split(":");
                choice = (String) JOptionPane.showInputDialog(null, "Select from students found:",
                        "Choice?", JOptionPane.QUESTION_MESSAGE,
                        null, foundPeople, foundPeople[0]);
            } else {
                String[] foundPeople = foundPeopleString.split(":");
                choice = (String) JOptionPane.showInputDialog(null, "Select from tutors found:",
                        "Choice?", JOptionPane.QUESTION_MESSAGE,
                        null, foundPeople, foundPeople[0]);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File Not Found!", "File", JOptionPane.ERROR_MESSAGE);
            listOrSearch.dispose();
            return;
        }
        if (choice == null) {
            clientWriter.println("tryAgain");
            clientWriter.flush();
            clientWriter.println("endLoop");
            clientWriter.flush();
            clientWriter.println("try");
            clientWriter.flush();
            return;
        }
        listOrSearch.dispose();
        String[] splitChoice = choice.split(". ");
        clientWriter.println("found");
        clientWriter.flush();
        clientWriter.println("endLoop");
        clientWriter.flush();
        clientWriter.println("noTry");
        clientWriter.flush();
        clientWriter.println("endLoop");
        clientWriter.flush();
        options = new Options(userTerminal, new User(splitChoice[1], true), clientReader, clientWriter);
        options.run();
    }

    public void search() {
        String finalChoice = "";
        int choice = 0;
        int countStudent = 1;
        int countTutor = 1;
        Options options;
        try {
            String foundPeopleString = clientReader.readLine();
            if (foundPeopleString.contains("notFound")) {
                String[] splitNotFound = foundPeopleString.split(":");
                int count = Integer.parseInt(splitNotFound[0]);
                if (count == 0 && userTerminal.getUserType()) {
                    JOptionPane.showMessageDialog(null, "Please add at least one tutor before using the program.", "No Tutors Found", JOptionPane.ERROR_MESSAGE);
                    listOrSearch.dispose();
                    return;
                } else if (count == 0 && !userTerminal.getUserType()) {
                    JOptionPane.showMessageDialog(null, "Please add at least one student before using the program.", "No Students Found", JOptionPane.ERROR_MESSAGE);
                    listOrSearch.dispose();
                    return;
                } else {
                    choice = JOptionPane.showConfirmDialog(null, "User not found! Would you like to search again?",
                            "User Not Found", JOptionPane.YES_NO_OPTION);
                    if (choice == 0) {
                        clientWriter.println("notFound");
                        clientWriter.flush();
                        return;
                    } else if (choice == 1) {
                        JOptionPane.showMessageDialog(null, "Thank you for using TutorFinder! Goodbye.",
                                "Farewell Message", JOptionPane.ERROR_MESSAGE);
                        listOrSearch.dispose();
                        return;
                    } else {
                        listOrSearch.dispose();
                        return;
                    }
                }
            } else {
                String[] foundPeople = foundPeopleString.split(":");
                if (!userTerminal.getUserType()) {
                    finalChoice = (String) JOptionPane.showInputDialog(null, "Select from students found:",
                            "Choice?", JOptionPane.QUESTION_MESSAGE,
                            null, foundPeople, foundPeople[0]);
                } else {
                    finalChoice = (String) JOptionPane.showInputDialog(null, "Select from tutors found:",
                            "Choice?", JOptionPane.QUESTION_MESSAGE,
                            null, foundPeople, foundPeople[0]);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File not found!", "File", JOptionPane.ERROR_MESSAGE);
            listOrSearch.dispose();
            return;
        }

        if (finalChoice == null) {
            clientWriter.println("notFound");
            clientWriter.flush();
            return;
        }
        listOrSearch.dispose();
        String[] splitChoice = finalChoice.split(". ");
        clientWriter.println("found");
        clientWriter.flush();
        clientWriter.println("endLoop");
        clientWriter.flush();
        clientWriter.println("noTry");
        clientWriter.flush();
        clientWriter.println("endLoop");
        clientWriter.flush();
        options = new Options(userTerminal, new User(splitChoice[1], true), clientReader, clientWriter);
        options.run();
    }


}
