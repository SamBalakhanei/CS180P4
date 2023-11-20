import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPage extends JComponent implements Runnable{
    JTextField username = new JTextField(5);
    JPasswordField password = new JPasswordField(5);
    JButton login;
    ActionListener actionListener;

    Container content;

    public LoginPage() {
        JFrame loginOrSignup = new JFrame("Login or Signup");
        loginOrSignup.setSize(600, 400);
        loginOrSignup.setLocationRelativeTo(null);
        loginOrSignup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginOrSignup.setVisible(true);
        content = loginOrSignup.getContentPane();
        content.setLayout(new BorderLayout());
        username.setText("username");
        password.setText("password");
        login = new JButton("Login");
        login.addActionListener(actionListener);
        JPanel firstOption = new JPanel();
        firstOption.add(username);
        firstOption.add(password);
        firstOption.add(login);
        content.add(firstOption, BorderLayout.CENTER);

    }
    public void run() {
       LoginPage loginPage = new LoginPage();
       loginPage.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new LoginPage());
    }
}
