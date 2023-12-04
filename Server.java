import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


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
            while(socket.isConnected()) {
            try {
                String query;
                while ((query = br.readLine()) != null && query.contains("$")) {
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
                        case "getUserType":
                            String[] userPass2 = querySplit[1].split(":");
                            String username2 = userPass2[0];
                            boolean userType = retrieveUserType(username2);
                            pw.println(userType);
                            pw.flush();
                            break;
                        case "validateUser":
                            String[] userPass3 = querySplit[1].split(":");
                            String username3 = userPass3[0];
                            String password3 = userPass3[1];
                            Boolean type = Boolean.parseBoolean(userPass3[2]);
                            User user = new User(username3, password3, type);
                            boolean validUser = checkValidateUser(user);
                            pw.println(validUser);
                            pw.flush();
                            break;
                        case "createUser":
                            String[] userPass4 = querySplit[1].split(":");
                            String username4 = userPass4[0];
                            String password4 = userPass4[1];
                            Boolean type4 = Boolean.parseBoolean(userPass4[2]);
                            User user4 = new User(username4, password4, type4);
                            boolean userCreated = createUser(user4);
                            pw.println(userCreated);
                            pw.flush();
                            break;

                    }

                }
                String listORSearch = query;
                String userName = br.readLine();
                String password = br.readLine();
                boolean userType = Boolean.parseBoolean(br.readLine());
                User userTerminal = new User (userName, password, userType);
                String foundPeople = "";
                if (listORSearch.equals("list"))
                    foundPeople = list(userTerminal.getUsername(), userTerminal);
                else if (listORSearch.equals("search")) {
                    String compareName = br.readLine();
                    foundPeople = search(userTerminal.getUsername(), userTerminal, compareName);
                }
                pw.println(foundPeople);
                pw.flush();

                pw.close();
                br.close();
            } catch(IOException e){
                e.printStackTrace();
            }
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

    public static boolean retrieveUserType(String username) {
        ArrayList<String> users = getUsers();
        for (String u : users) {
            String[] details = u.split(":");
            String usernameFile = details[0];
            if (usernameFile.toLowerCase().equals(username.toLowerCase())) {
                return Boolean.parseBoolean(details[2]);
            }
        }
        return false;
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
    public static synchronized boolean createUser(User user) {
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


    public String list(String userName, User userTerminal) {
        String foundPeople = "";
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("accountDetails.txt"));
            String line = "";
            String[] blocked = {"hello", "hi", "goodbye"};
            int counter = 1;
            boolean block;
            line = bfr.readLine();
            while (line != null) {
                block = false;
                String[] splitLine = line.split(":");
                /*for (String s : blocked) {
                    if (splitLine[0].equals(s.split(":")[1]) && userName.equals(s.split(":")[0])) {
                        block = true;
                    } else if (splitLine[0].equals(s.split(":")[0]) && userName.equals(s.split(":")[1])) {
                        block = true;
                    }
                }
                 */
                if (!userTerminal.getUserType()) {
                    if (Boolean.parseBoolean(splitLine[2]) && !block) {
                        foundPeople += counter + ". " + splitLine[0] + ":";
                        counter++;
                    }
                } else {
                    if (!Boolean.parseBoolean(splitLine[2]) && !block) {
                        foundPeople += counter + ". " + splitLine[0] + ":";
                        counter++;
                    }
                }
                line = bfr.readLine();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return foundPeople;
    }



    public String search(String userName, User userTerminal, String comparisonName) {
        String line;
        int counter = 1;
        int countStudent = 0;
        int countTutor = 0;
        String foundPeople = "";
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("accountDetails.txt"));
            line = bfr.readLine();
            boolean block;
            while (line != null) {
                block = false;
                String[] splitLine = line.split(":");
                /*for (String s : blocked) {
                    if (splitLine[0].equals(s.split(":")[1]) && userName.equals(s.split(":")[0])) {
                        block = true;
                    } else if (splitLine[0].equals(s.split(":")[0]) && userName.equals(s.split(":")[1])) {
                        block = true;
                    }
                }

                 */
                if (!userTerminal.getUserType()) {
                    if (Boolean.parseBoolean(splitLine[2])) {
                        countStudent++;
                        if (splitLine[0].toLowerCase().contains(comparisonName.toLowerCase()) && !block) {
                            foundPeople += counter + ". " + splitLine[0] + ":";
                            counter++;
                        }
                    }
                } else {
                    if (!Boolean.parseBoolean(splitLine[2])) {
                        countTutor++;
                        if (splitLine[0].toLowerCase().contains(comparisonName.toLowerCase()) && !block) {
                            foundPeople += counter + ". " + splitLine[0] + ":";
                            counter++;
                        }
                    }
                }
                line = bfr.readLine();
            }
            bfr.close();
        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
        }
        if (foundPeople.isEmpty()) {
            if (userTerminal.getUserType())
                foundPeople = countTutor + ":notFound";
            else
                foundPeople = countStudent + ":notFound";
        }

        return foundPeople;
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

