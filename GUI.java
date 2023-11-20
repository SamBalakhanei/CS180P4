import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JComponent implements Runnable {
       JButton login;
       JButton signUP;
       Container content;
       ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == login) {
                LoginPage logIn = new LoginPage();
                logIn.setVisible(true);
            }
            if (e.getSource() == signUP) {
                SignUpPage signUp = new SignUpPage();
                signUp.setVisible(true);
            }
        }

       };
   public GUI() {
       JFrame loginOrSignup = new JFrame("Login or Signup");
       loginOrSignup.setSize(600, 400);
       loginOrSignup.setLocationRelativeTo(null);
       loginOrSignup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       loginOrSignup.setVisible(true);
       content = loginOrSignup.getContentPane();
       content.setLayout(new BorderLayout());
       login = new JButton("Login");
       login.addActionListener(actionListener);
       signUP = new JButton("Sign Up");
       signUP.addActionListener(actionListener);
       JPanel firstOption = new JPanel();
       firstOption.add(login);
       firstOption.add(signUP);
       content.add(firstOption, BorderLayout.CENTER);
   }

    @Override
    public void run() {
        GUI loginOrSignUp = new GUI();
        loginOrSignUp.setVisible(true);


    }

    public static void main(String[] args) {
       SwingUtilities.invokeLater(new GUI());
   }
}




