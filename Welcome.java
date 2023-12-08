import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * This class begins the communication with the user and takes care of the login
 * and signup page.
 *
 *
 * @author Niharika Raj, Saahil Mathur, Sam Balakhanei, Abhi Tandon
 * @version December 8, 2023
 */
public class Welcome extends JComponent implements Runnable {
    Image image;
    Container content;
    JFrame frame;
    JTextField username; // = String username
    JPasswordField password; // = String password
    JCheckBox student; // checked = student, unchecked = tutor
    JButton loginButtonWhich; // choose if they want to login
    JButton signUpButtonWhich; // choose if they want to sign up
    JButton loginButton; // choose to actually submit login information
    JButton signUpButton; // choose to actually submit sign up information

    private Socket socket;
    private PrintWriter pw;
    private BufferedReader br;

    public void run() {
        frame = new JFrame("Tutor Messenger");
        content = frame.getContentPane();
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
        content.add(panel, BorderLayout.CENTER);
        frame.validate(); // refreshes the screen

    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButtonWhich) {
                showLoginScreen();
            } else if (e.getSource() == signUpButtonWhich) {
                showSignUpScreen();
            } else if (e.getSource() == loginButton) {
                String passwordString = new String(password.getPassword());
                User user = new User(username.getText(), passwordString);
                System.out.println(user.getUsername() + " " + user.getPassword());
                if (user.getUsername().equals("") || user.getPassword().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Username and/or password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                boolean userType = getUserType(user);
                user.setUserType(userType);
                boolean userExistsChecked = userExists(user);
                boolean validateUserChecked = validateUser(user);
                System.out.println(userExistsChecked);
                if (!userExistsChecked) {
                    JOptionPane.showMessageDialog(null,
                            "User does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!validateUserChecked) {
                    JOptionPane.showMessageDialog(null,
                            "Incorrect Password!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Welcome " + user.getUsername() + "!", "Success!", JOptionPane.PLAIN_MESSAGE);
                    pw.println("");
                    pw.flush();
                    View view = new View(user.getUsername(), user, br, pw, false);
                    frame.dispose();
                    view.run();
                }

            } else if (e.getSource() == signUpButton) {
                String passwordString = new String(password.getPassword());
                User user = new User(username.getText(), passwordString, student.isSelected()); // have to check if
                // student box is
                // checked
                boolean userExistsChecked = userExists(user);
                if (userExistsChecked) {
                    JOptionPane.showMessageDialog(null,
                            "User already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (user.getUsername().contains(":") || user.getPassword().contains(":")) {
                    JOptionPane.showMessageDialog(null,
                            "Username and/or password cannot contain ':'",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else if (createUser(user)) {
                    JOptionPane.showMessageDialog(null,
                            "Account created successfully!", "Success!", JOptionPane.PLAIN_MESSAGE);
                    pw.println("");
                    pw.flush();
                    View view = new View(user.getUsername(), user, br, pw, false);
                    frame.dispose();
                    view.run();
                }

            }
        }
    };

    private void showLoginScreen() {
        content.removeAll();
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4, 1));
        JLabel label = new JLabel("Enter your existing username and password below:");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        loginPanel.add(label);
        username = new JTextField("");
        loginPanel.add(username);
        password = new JPasswordField("");
        loginPanel.add(password);
        loginButton = new JButton("Login");
        loginButton.addActionListener(actionListener);
        loginPanel.add(loginButton);

        content.add(loginPanel, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();

    }

    private void showSignUpScreen() {
        content.removeAll();
        JPanel signUpPanel = new JPanel();
        signUpPanel.setLayout(new GridLayout(5, 1));
        JLabel label = new JLabel("Enter your desired username and password below:");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        signUpPanel.add(label);
        username = new JTextField("");
        signUpPanel.add(username);
        password = new JPasswordField("");
        signUpPanel.add(password);
        student = new JCheckBox("Student?");
        signUpPanel.add(student);
        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(actionListener);
        signUpPanel.add(signUpButton);

        content.add(signUpPanel, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();

    }

    public static void main(String[] args) {
        Welcome welcome = new Welcome();
        // Connect to server
        try {
            welcome.connectToServer();
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    welcome.run();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }

    private void connectToServer() throws IOException {
        socket = new Socket("localhost", 4343);
        pw = new PrintWriter(socket.getOutputStream(), true);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Connected to server");
    }

    // Checks if user exists in accountDetails.txt (not case sensitive)
    public boolean userExists(User user) {
        pw.println("userExists$:" + user.getUsername() + ":" + user.getPassword());
        pw.flush();
        String res = getResponse();
        return Boolean.parseBoolean(res);
    }

    public String getResponse() {
        try {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    // Checks if user exists in accountDetails.txt and if the password is correct
    // (case sensitive)
    public boolean validateUser(User user) {
        pw.println("validateUser$:" + user.getUsername() + ":" + user.getPassword() + ":" + user.getUserType());
        pw.flush();
        String res = getResponse();
        return Boolean.parseBoolean(res);

    }

    // Writes a new user to accountDetails.txt
    public boolean createUser(User user) {
        pw.println("createUser$:" + user.getUsername() + ":" + user.getPassword() + ":" + user.getUserType());
        pw.flush();
        String res = getResponse();
        return Boolean.parseBoolean(res);

    }

    public boolean getUserType(User user) {
        pw.println("getUserType$:" + user.getUsername());
        pw.flush();
        String res = getResponse();
        return Boolean.parseBoolean(res);
    }
}
