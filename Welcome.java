import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
/**
 *
 * This class begins the communication with the user and takes care of the login and signup page.
 *
 *
 * @author Niharika Raj, Saahil Mathur, Sam Balakhanei, Abhi Tandon
 * @version November 13, 2023
 */
public class Welcome extends JComponent implements Runnable {
    Image image;
    Container content;



    JTextField username; // = String username
    JPasswordField password; // = String password
    JCheckBox student; //checked = student, unchecked = tutor
    JButton loginButtonWhich;
    JButton signUpButtonWhich;
    JButton loginButton;
    JButton signUpButton;









    public void run() {
        JFrame frame = new JFrame("Tutor Messenger");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        JLabel label = new JLabel("Welcome to Tutor Messenger!");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        panel.add(label);
        loginButtonWhich = new JButton("Login?");
        loginButtonWhich.addActionListener(actionListener);
        signUpButtonWhich = new JButton("Sign Up?");
        signUpButtonWhich.addActionListener(actionListener);
        panel.add(loginButtonWhich);
        panel.add(signUpButtonWhich);
        

    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButtonWhich) {

            }
            if (e.getSource() == signUpButtonWhich) {

            }
        }

    };
    public static void main(String[] args) {

        // Scanner scan = new Scanner(System.in);
        // String username;
        // String password;
        // User user;
        // String choice;
        // boolean type; // true = student, false = tutor
        // do {
        //     System.out.print("Welcome to the tutor marketplace!\n(1) Log In\n(2) Sign Up\n(3) Exit\n");
        //     choice = scan.nextLine();
        //     switch (choice) {
        //         case "1":
        //             System.out.print("Enter your username: ");
        //             username = scan.nextLine();
        //             System.out.print("Enter your password: ");
        //             password = scan.nextLine();
        //             user = new User(username, password);
        //             if (!userExists(user)) {
        //                 System.out.println("User does not exist!");
        //                 choice = "-1";
        //                 user = null;
        //             } else if (!validateUser(user)) {
        //                 System.out.println("Incorrect password!");
        //                 choice = "-1";
        //                 user = null;
        //             } else {
        //                 System.out.println("Welcome " + username + "!");
        //             }
        //             break;
        //         case "2":
        //             System.out.print("Enter your username: ");
        //             username = scan.nextLine();
        //             System.out.print("Enter your password: ");
        //             password = scan.nextLine();
        //             boolean valid = false;
        //             boolean temp = false;
        //             do {
        //                 System.out.print("Are you a student or a tutor?\n (1) Student\n (2) Tutor\n");
        //                 String option = scan.nextLine();
        //                 if (option.equals("1") || option.equals("2")) {
        //                     temp = option.equals("1");
        //                     valid = true;
        //                 } else {
        //                     System.out.println("Please enter a valid input!");
        //                 }
        //             } while (!valid);
        //             type = temp;
        //             user = new User(username, password, type);
        //             if (!createUser(user)) {
        //                 choice = "-1";
        //                 user = null;
        //             }
        //             break;
        //         case "3":
        //             System.out.println("Goodbye!");
        //             return;
        //         default:
        //             System.out.println("Invalid Option!");
        //             user = null;
        //             break;
        //     }
        // } while (user == null);

        // if (user.getUserType()) {
        //     View.findTutor(user.getUsername(), user);
        // } else {
        //     View.findStudent(user.getUsername(), user);
        // }

        // scan.close();
    }

    // Checks if user exists in accountDetails.txt (not case sensitive)
    public static boolean userExists(User user) {
        ArrayList<String> users = getUsers();
        for (String u : users) {
            String[] details = u.split(":");
            String username = details[0];
            if (user.getUsername().toLowerCase().equals(username.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    // Checks if user exists in accountDetails.txt and if the password is correct
    // (case sensitive)
    public static boolean validateUser(User user) {
        ArrayList<String> users = getUsers();
        for (String u : users) {
            String[] details = u.split(":");
            String username = details[0];
            String password = details[1];
            String type = details[2];
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                user.setUserType(Boolean.parseBoolean(type));
                return true;
            }
        }
        return false;
    }

    // Returns an ArrayList of all users in accountDetails.txt
    public static ArrayList<String> getUsers() {
        ArrayList<String> users = new ArrayList<String>();
        try (BufferedReader bfr = new BufferedReader(new FileReader("accountDetails.txt"))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                users.add(line);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return users;
    }

    // Writes a new user to accountDetails.txt
    public static boolean createUser(User user) {
        File g = new File("accountDetails.txt");
        try {
            if (!g.exists()) {
                g.createNewFile();
            }
            BufferedReader bfr = new BufferedReader(new FileReader("accountDetails.txt"));
            String line;
            while ((line = bfr.readLine()) != null) {
                String[] details = line.split(":");
                String username = details[0];
                if (user.getUsername().equals(username)) {
                    System.out.println("User already exists!");
                    return false;
                }
                if (user.getUsername().contains(":") || user.getPassword().contains(":")) {
                    System.out.println("Username and password cannot contain ':'!");
                    return false;
                }
            }
            File f = new File("accountDetails.txt");
            FileWriter fr = new FileWriter(f, true);
            PrintWriter pw = new PrintWriter(fr);
            pw.println(user.getUsername() + ":" + user.getPassword() + ":" + user.getUserType());
            System.out.println("User created!");
            pw.flush();
            pw.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return true;

    }
}
