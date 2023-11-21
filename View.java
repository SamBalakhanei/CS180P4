import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class displays the view page and allows for the user to select who to converse with.
 *
 * @author Niharika Raj, Saahil Mathur, Sam Balakhanei, Abhi Tandon
 * @version November 13, 2023
 */

/** major changes made - changed the methods from static to non-static (for work with GUI)
 * whenever you are trying to call this feature create a new View object with
 * String userName, User userTerminal and then call the run method rather than findStudent or findTutor
 * Saahil - if you're trying to edit the block feature there shouldn't be any major changes to what you've done already
 * it's just been split into separate methods
 */
public class View extends JComponent implements Runnable {
    private String userName; //username
    private User userTerminal; //user using terminal
    private ArrayList<String> blocked = new ArrayList<>(); //arraylist with all blocked members
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
                display();
            }
            if (e.getSource() == listStudent) {
                listStudent();
            }
            if (e.getSource() == listTutor) {
                listTutor();
            }
            if (e.getSource() == searchTutor) {
                display();
            }
        }
    };
    Container content;

    public View(String userName, User userTerminal) {
        this.userName = userName;
        this.userTerminal = userTerminal;
    }

    //if user is a tutor
    public void findStudent() {
        Scanner scanner = new Scanner(System.in);
        for (String c : Options.getBlocked()) {
            blocked.add(c);
        }
        listStudent = new JButton("List Student");
        listStudent.addActionListener(actionListener);
        searchStudent = new JButton("Search Student");
        searchStudent.addActionListener(actionListener);
    }

    public void listStudent() {
        Options options;
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("accountDetails.txt"));
            String line = "";
            ArrayList<String> foundPeople = new ArrayList<>();
            int counter = 1;
            boolean block;
            line = bfr.readLine();
            while (line != null) {
                block = false;
                String[] splitLine = line.split(":");
                for (String s : blocked) {
                    if (splitLine[0].equals(s.split(":")[1]) && userName.equals(s.split(":")[0])) {
                        block = true;
                    } else if (splitLine[0].equals(s.split(":")[0]) && userName.equals(s.split(":")[1])) {
                        block = true;
                    }
                }
                if (Boolean.parseBoolean(splitLine[2]) && !block) {
                    foundPeople.add(counter + ". " + splitLine[0]);
                    counter++;
                }
                line = bfr.readLine();
            }

            String[] foundPeopleArray = new String[foundPeople.size()];
            for (int i = 0; i < foundPeople.size(); i++) {
                foundPeopleArray[i] = foundPeople.get(i);
            }
            String choice = (String) JOptionPane.showInputDialog(null, "Select from students found:",
                    "Choice?", JOptionPane.QUESTION_MESSAGE,
                    null, foundPeopleArray, foundPeopleArray[0]);
            if (choice == null) {
                return;
            }
            String[] splitChoice = choice.split(". ");
            options = new Options(userTerminal, new User(splitChoice[1], true));
            options.viewMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void display() {
        content.removeAll();
        searchField.setText("exampleUser");
        JPanel search = new JPanel();
        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = searchField.getText();
                if (userTerminal.getUserType()) {
                    searchStudent(name);
                } else {
                    searchTutor(name);
                }
            }
        });
        search.add(searchField);
        search.add(searchButton);
        content.add(search);
        content.revalidate();
        content.repaint();
    }

    public void searchStudent(String comparisonName) {
        String finalChoice = "";
        String line;
        boolean found = false;
        int choice = 0;
        int counter = 1;
        Options options;
        ArrayList<String> foundPeople = new ArrayList<>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("accountDetails.txt"));
            do {
                line = bfr.readLine();
                boolean block;
                while (line != null) {
                    block = false;
                    String[] splitLine = line.split(":");
                    for (String s : blocked) {
                        if (splitLine[0].equals(s.split(":")[1]) && userName.equals(s.split(":")[0])) {
                            block = true;
                        } else if (splitLine[0].equals(s.split(":")[0]) && userName.equals(s.split(":")[1])) {
                            block = true;
                        }
                    }
                    if (splitLine[0].toLowerCase().contains(comparisonName.toLowerCase())) {
                        if (Boolean.parseBoolean(splitLine[2]) && !block) {
                            foundPeople.add(counter + ". " + splitLine[0]);
                            counter++;
                            found = true;
                        }
                    }
                    line = bfr.readLine();
                }
                bfr.close();
                if (!found) {
                    while (true) {
                        choice = JOptionPane.showConfirmDialog(null, "User not found! Would you like to search again?", "User Not Found", JOptionPane.YES_NO_OPTION);
                        if (choice == 0) {
                            break;
                        } else if (choice == 1) {
                            System.out.println("Thank you for using TutorFinder! Goodbye.");
                            return;
                        } else {
                            return;
                        }
                    }
                    bfr = new BufferedReader(new FileReader("accountDetails.txt"));
                }
            } while (!found && choice == 0);
            if (found) {
                String[] foundPeopleArray = new String[foundPeople.size()];
                for (int i = 0; i < foundPeople.size(); i++) {
                    foundPeopleArray[i] = foundPeople.get(i);
                }
                finalChoice = (String) JOptionPane.showInputDialog(null, "Select from students found:",
                        "Choice?", JOptionPane.QUESTION_MESSAGE,
                        null, foundPeopleArray, foundPeopleArray[0]);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (finalChoice == null) {
            return;
        }
        String[] splitChoice = finalChoice.split(". ");
        options = new Options(userTerminal, new User(splitChoice[1], true));
        options.viewMenu();
    }

    public void findTutor() {
        Scanner scanner = new Scanner(System.in);
        for (String c : Options.getBlocked()) {
            blocked.add(c);
        }
        listTutor = new JButton("List Tutor");
        listTutor.addActionListener(actionListener);
        searchTutor = new JButton("Search Tutor");
        searchTutor.addActionListener(actionListener);
    }

    public void listTutor() {
        Options options;
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("accountDetails.txt"));
            String line = "";
            ArrayList<String> foundPeople = new ArrayList<>();
            int counter = 1;
            boolean block;
            line = bfr.readLine();
            while (line != null) {
                block = false;
                String[] splitLine = line.split(":");
                for (String s : blocked) {
                    if (splitLine[0].equals(s.split(":")[1]) && userName.equals(s.split(":")[0])) {
                        block = true;
                    } else if (splitLine[0].equals(s.split(":")[0]) && userName.equals(s.split(":")[1])) {
                        block = true;
                    }
                }
                if (!Boolean.parseBoolean(splitLine[2]) && !block) {
                    foundPeople.add(counter + ". " + splitLine[0]);
                    counter++;
                }
                line = bfr.readLine();
            }

            String[] foundPeopleArray = new String[foundPeople.size()];
            for (int i = 0; i < foundPeople.size(); i++) {
                foundPeopleArray[i] = foundPeople.get(i);
            }
            String choice = (String) JOptionPane.showInputDialog(null, "Select from tutors found:",
                    "Choice?", JOptionPane.QUESTION_MESSAGE,
                    null, foundPeopleArray, foundPeopleArray[0]);
            if (choice == null) {
                return;
            }
            String[] splitChoice = choice.split(". ");
            options = new Options(userTerminal, new User(splitChoice[1], true));
            options.viewMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchTutor(String comparisonName) {
        String finalChoice = "";
        String line;
        boolean found = false;
        int choice = 0;
        int counter = 1;
        Options options;
        ArrayList<String> foundPeople = new ArrayList<>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("accountDetails.txt"));
            do {
                line = bfr.readLine();
                boolean block;
                while (line != null) {
                    block = false;
                    String[] splitLine = line.split(":");
                    for (String s : blocked) {
                        if (splitLine[0].equals(s.split(":")[1]) && userName.equals(s.split(":")[0])) {
                            block = true;
                        } else if (splitLine[0].equals(s.split(":")[0]) && userName.equals(s.split(":")[1])) {
                            block = true;
                        }
                    }
                    if (splitLine[0].toLowerCase().contains(comparisonName.toLowerCase())) {
                        if (!Boolean.parseBoolean(splitLine[2]) && !block) {
                            foundPeople.add(counter + ". " + splitLine[0]);
                            counter++;
                            found = true;
                        }
                    }
                    line = bfr.readLine();
                }
                bfr.close();
                if (!found) {
                    while (true) {
                        choice = JOptionPane.showConfirmDialog(null, "User not found! Would you like to search again?", "User Not Found", JOptionPane.YES_NO_OPTION);
                        if (choice == 0) {
                            break;
                        } else if (choice == 1) {
                            System.out.println("Thank you for using TutorFinder! Goodbye.");
                            return;
                        } else {
                            return;
                        }
                    }
                    bfr = new BufferedReader(new FileReader("accountDetails.txt"));
                }
            } while (!found && choice == 0);
            if (found) {
                String[] foundPeopleArray = new String[foundPeople.size()];
                for (int i = 0; i < foundPeople.size(); i++) {
                    foundPeopleArray[i] = foundPeople.get(i);
                }
                finalChoice = (String) JOptionPane.showInputDialog(null, "Select from tutors found:",
                        "Choice?", JOptionPane.QUESTION_MESSAGE,
                        null, foundPeopleArray, foundPeopleArray[0]);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (finalChoice == null) {
            return;
        }
        String[] splitChoice = finalChoice.split(". ");
        options = new Options(userTerminal, new User(splitChoice[1], true));
        options.viewMenu();
    }

    @Override
    public void run() {
        JFrame listOrSearch = new JFrame("Login or Signup");
        listOrSearch.setSize(600, 400);
        listOrSearch.setLocationRelativeTo(null);
        listOrSearch.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        listOrSearch.setVisible(true);
        content = listOrSearch.getContentPane();
        content.setLayout(new BorderLayout());
        JPanel listORSearch = new JPanel();
        JLabel select = new JLabel("Select one of the options below:");
        listORSearch.add(select);
        if (userTerminal.getUserType()) {
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

    /* public static void main(String[] args) {
        User niha = new User("suhi", "Priya", false);
        View view = new View("suhi", niha);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                view.run();
            }
        });
    }
    
     */
}
