import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SignUpPage extends JComponent implements Runnable {
        JTextField username = new JTextField(5);
        JPasswordField password = new JPasswordField(5);

        JButton student;
        JButton tutor;
        JButton signup;
        ActionListener actionListener;

        Container content;

        public SignUpPage() {
            JFrame loginOrSignup = new JFrame("Login or Signup");
            loginOrSignup.setSize(600, 400);
            loginOrSignup.setLocationRelativeTo(null);
            loginOrSignup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            loginOrSignup.setVisible(true);
            content = loginOrSignup.getContentPane();
            content.setLayout(new BorderLayout());
            username.setText("username");
            password.setText("password");
            student = new JButton("Student");
            student.addActionListener(actionListener);
            tutor = new JButton("Tutor");
            tutor.addActionListener(actionListener);
            signup = new JButton("Login");
            signup.addActionListener(actionListener);
            JPanel firstOption = new JPanel();
            firstOption.add(username);
            firstOption.add(password);
            firstOption.add(student);
            firstOption.add(tutor);
            firstOption.add(signup);
            content.add(firstOption, BorderLayout.CENTER);

        }
        public void run() {
            SignUpPage signUpPage = new SignUpPage();
            signUpPage.setVisible(true);
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(new SignUpPage());
        }
}


