import java.io.BufferedReader;
import java.io.FileReader;
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
                    System.out.println("User does not exist or details are incorrect!");
                    choice = "-1";
                    user = null;
                }
                break;
            case "2":
                System.out.print("Enter your username: ");
                username = scan.nextLine();
                System.out.print("Enter your password: ");
                password = scan.nextLine();
                System.out.print("Are you a student or a tutor?\n (1) Student\n (2) Tutor\n");
                type = scan.nextLine().equals("1");
                user = new User(username, password);
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

    }

    public static boolean userExists(User user) {
        ArrayList<String> users = getUsers();
        for (String u : users) {
            String[] details = u.split(":");
            String username = details[0];
            String password = details[1];
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

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

    public static void createUser(User user) {

    }
}