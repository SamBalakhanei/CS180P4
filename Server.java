import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {
    Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.out.printf("Client connected: %s%n", socket);
        PrintWriter pw;
        BufferedReader br;
        try {
            pw = new PrintWriter(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while (true) {
            try {

                String query;
                while ((query = br.readLine()) != null) {
                    String[] querySplit = query.split("\\$:");
                    String command = querySplit[0];
                    switch (command) {
                        case "userExists":
                            String[] userPass = querySplit[1].split(":");
                            String username = userPass[0];
                            String password = userPass[1];
                            boolean userExists = checkUserExists(username, password);
                            pw.println(userExists);
                            pw.flush();
                            break;
                        case "validateUser":
                            String[] userPass2 = querySplit[1].split(":");
                            String username2 = userPass2[0];
                            String password2 = userPass2[1];
                            Boolean type = Boolean.parseBoolean(userPass2[2]);
                            User user = new User(username2, password2, type);
                            boolean validUser = checkValidateUser(user);
                            pw.println(validUser);
                            pw.flush();
                            break;
                        case "createUser":
                            String[] userPass3 = querySplit[1].split(":");
                            String username3 = userPass3[0];
                            String password3 = userPass3[1];
                            Boolean type3 = Boolean.parseBoolean(userPass3[2]);
                            User user3 = new User(username3, password3, type3);
                            boolean userCreated = createUser(user3);
                            pw.println(userCreated);
                            pw.flush();
                            break;

                    }

                }

                pw.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    // Checks if user exists in accountDetails.txt (not case sensitive)
    public static boolean checkUserExists(String username, String password) {
        ArrayList<String> users = getUsers();
        for (String u : users) {
            String[] details = u.split(":");
            String usernameFile = details[0];
            if (usernameFile.toLowerCase().equals(username.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    // Returns an ArrayList of all users in accountDetails.txt
    public static synchronized ArrayList<String> getUsers() {
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

    // Checks if user exists in accountDetails.txt and if the password is correct
    // (case sensitive)
    public static boolean checkValidateUser(User user) {
        ArrayList<String> users = getUsers();
        for (String u : users) {
            String[] details = u.split(":");
            String username = details[0];
            String password = details[1];
            String type = details[2];
            if (user.getUsername().equals(username) &&
                    user.getPassword().equals(password)) {
                user.setUserType(Boolean.parseBoolean(type));
                return true;
            }
        }
        return false;
    }

    // Writes a new user to accountDetails.txt
    public static boolean createUser(User user) {
        File g = new File("accountDetails.txt");
        try {
            if (!g.exists()) {
                g.createNewFile();
            }
            File f = new File("accountDetails.txt");
            FileWriter fr = new FileWriter(f, true);
            PrintWriter pwFile = new PrintWriter(fr);
            pwFile.println(user.getUsername() + ":" + user.getPassword() + ":" + user.getUserType());
            System.out.println("User created!");
            pwFile.flush();
            pwFile.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return true;

    }
    

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4343);
        System.out.printf("socket open, waiting for connections on %s\n", serverSocket);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new Server(socket)).start();
        }
    }

}
