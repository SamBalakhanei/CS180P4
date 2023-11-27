import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * This class displays the view page and allows for the user to select who to converse with.
 *
 * @author Niharika Raj, Saahil Mathur, Sam Balakhanei, Abhi Tandon
 * @version November 13, 2023
 */

/**
 * major changes made: findStudent and findTutor are purely for GUI - all the background things happen in list() and search(comparisonName)
 * (this should make things slightly easier for network I/O), display() added to deal with GUI for the search feature, list and search methods no longer separate
 * for tutors and students (if statements within each method to accommodate for both)
 *
 * how to call view: create a new View object with String userName, User userTerminal and then call the run method rather than findStudent or findTutor
 *
 * blocked feature: Saahil - if you're trying to edit the block feature there shouldn't be any major changes to what you've done already
 * it's just been split into separate methods and duplicates have been removed. i've left the duplicate methods commented in the bottom in case there are any changes
 * that you would like to make there
 */
public class View extends JComponent implements Runnable {
    private String userName; //username
    private User userTerminal; //user using terminal
    private ArrayList<String> blocked = new ArrayList<>(); //arraylist with all blocked members
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
                display();
            }
            if (e.getSource() == listStudent) {
                list();
            }
            if (e.getSource() == listTutor) {
                list();
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
    @Override
    public void run() {
        listOrSearch = new JFrame("Login or Signup");
        listOrSearch.setSize(600, 400);
        listOrSearch.setLocationRelativeTo(null);
        listOrSearch.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        listOrSearch.setVisible(true);
        content = listOrSearch.getContentPane();
        content.setLayout(new BorderLayout());
        JPanel listORSearch = new JPanel();
        listORSearch.setLayout(new GridLayout(3,1));
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
                if (!userTerminal.getUserType()) {
                    search(name);
                } else {
                    search(name);
                }
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
        Options options;
        String choice = "";
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
                if (!userTerminal.getUserType()) {
                    if (Boolean.parseBoolean(splitLine[2]) && !block) {
                        foundPeople.add(counter + ". " + splitLine[0]);
                        counter++;
                    }
                } else {
                    if (!Boolean.parseBoolean(splitLine[2]) && !block) {
                        foundPeople.add(counter + ". " + splitLine[0]);
                        counter++;
                    }
                }
                line = bfr.readLine();
            }

            String[] foundPeopleArray = new String[foundPeople.size()];
            for (int i = 0; i < foundPeople.size(); i++) {
                foundPeopleArray[i] = foundPeople.get(i);
            }
            if (!userTerminal.getUserType()) {
                choice = (String) JOptionPane.showInputDialog(null, "Select from students found:",
                        "Choice?", JOptionPane.QUESTION_MESSAGE,
                        null, foundPeopleArray, foundPeopleArray[0]);
            } else {
                choice = (String) JOptionPane.showInputDialog(null, "Select from tutors found:",
                        "Choice?", JOptionPane.QUESTION_MESSAGE,
                        null, foundPeopleArray, foundPeopleArray[0]);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"File Not Found!", "File", JOptionPane.ERROR_MESSAGE);
            listOrSearch.dispose();
            return;
        }
        if (choice == null) {
            listOrSearch.dispose();
            return;
        }
        listOrSearch.dispose();
        String[] splitChoice = choice.split(". ");
        options = new Options(userTerminal, new User(splitChoice[1], true));
        options.run();
    }
    
    public void search(String comparisonName) {
        String finalChoice = "";
        String line;
        boolean found = false;
        int choice = 0;
        int counter = 1;
        Options options;
        ArrayList<String> foundPeople = new ArrayList<>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("accountDetails.txt"));
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
                    if (!userTerminal.getUserType()) {
                        if (Boolean.parseBoolean(splitLine[2]) && !block) {
                            foundPeople.add(counter + ". " + splitLine[0]);
                            counter++;
                            found = true;
                        }
                    } else {
                        if (!Boolean.parseBoolean(splitLine[2]) && !block) {
                            foundPeople.add(counter + ". " + splitLine[0]);
                            counter++;
                            found = true;
                        }
                    }
                }
                line = bfr.readLine();
            }
            bfr.close();
            if (!found) {
                choice = JOptionPane.showConfirmDialog(null, "User not found! Would you like to search again?",
                        "User Not Found", JOptionPane.YES_NO_OPTION);
                if (choice == 0) {
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
            } else {
                String[] foundPeopleArray = new String[foundPeople.size()];
                for (int i = 0; i < foundPeople.size(); i++) {
                    foundPeopleArray[i] = foundPeople.get(i);
                }
                if (!userTerminal.getUserType()) {
                    finalChoice = (String) JOptionPane.showInputDialog(null, "Select from students found:",
                            "Choice?", JOptionPane.QUESTION_MESSAGE,
                            null, foundPeopleArray, foundPeopleArray[0]);
                } else {
                    finalChoice = (String) JOptionPane.showInputDialog(null, "Select from tutors found:",
                            "Choice?", JOptionPane.QUESTION_MESSAGE,
                            null, foundPeopleArray, foundPeopleArray[0]);
                }

            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File not found!", "File", JOptionPane.ERROR_MESSAGE);
            listOrSearch.dispose();
            return;
        }

        if (finalChoice == null) {
            listOrSearch.dispose();
            return;
        }
        listOrSearch.dispose();
        String[] splitChoice = finalChoice.split(". ");
        options = new Options(userTerminal, new User(splitChoice[1], true));
        options.run();
    }



      /*
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
            System.out.println(choice);
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
                choice = JOptionPane.showConfirmDialog(null, "User not found! Would you like to search again?", "User Not Found", JOptionPane.YES_NO_OPTION);
                if (choice == 0) {
                    return;
                } else if (choice == 1) {
                    JOptionPane.showMessageDialog(null, "Thank you for using TutorFinder! Goodbye.", "Farewell Message", JOptionPane.ERROR_MESSAGE);
                    listOrSearch.dispose();
                    return;
                } else {
                    listOrSearch.dispose();
                    return;
                }
            }

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

        }
        if (finalChoice == null) {
            listOrSearch.dispose();
            return;
        }
        listOrSearch.dispose();
        String[] splitChoice = finalChoice.split(". ");
        options = new Options(userTerminal, new User(splitChoice[1], false));
        options.viewMenu();
    }
    */


}
