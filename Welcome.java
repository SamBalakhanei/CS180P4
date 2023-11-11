import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Welcome {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String username;
        String password;
        User user;
        String choice;
        boolean type; // true = student, false = tutor
        do {
            System.out.print("Welcome to the tutor marketplace!\n(1) Log In\n(2) Sign Up\n(3) Exit\n");
            choice = scan.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("Enter your username: ");
                    username = scan.nextLine();
                    System.out.print("Enter your password: ");
                    password = scan.nextLine();
                    user = new User(username, password);
                    if (!userExists(user)) {
                        System.out.println("User does not exist!");
                        choice = "-1";
                        user = null;
                    } else if (!validateUser(user)) {
                        System.out.println("Incorrect password!");
                        choice = "-1";
                        user = null;
                    } else {
                        System.out.println("Welcome " + username + "!");
                    }
                    break;
                case "2":
                    System.out.print("Enter your username: ");
                    username = scan.nextLine();
                    System.out.print("Enter your password: ");
                    password = scan.nextLine();
                    System.out.print("Are you a student or a tutor?\n (1) Student\n (2) Tutor\n");
                    type = scan.nextLine().equals("1");
                    user = new User(username, password, type);
                    createUser(user);
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid Option!");
                    user = null;
                    break;
            }
        } while (user == null);

        try {
            if (user.getUserType()) {
                StudentView.main(args);
            } else {
                TutorView.main(args);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());

        }

    }

    // Checks if user exists in accountDetails.txt
    public static boolean userExists(User user) {
        ArrayList<String> users = getUsers();
        for (String u : users) {
            String[] details = u.split(":");
            String username = details[0];
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Checks if user exists in accountDetails.txt and if the password is correct
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
    public static void createUser(User user) {
        try (BufferedReader bfr = new BufferedReader(new FileReader("accountDetails.txt"))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                String[] details = line.split(":");
                String username = details[0];
                if (user.getUsername().equals(username)) {
                    System.out.println("User already exists!");
                    return;
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

    }
}