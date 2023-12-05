import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class Server implements Runnable {
    Socket socket;
    private String senderConvoFileName;
    private String receiverConvoFileName;
    private static Object obj = new Object();

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
            while (socket.isConnected()) {
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
                    
                    String found = "";
                    User userTerminal;
                    String userName = br.readLine();
                    String password = br.readLine();
                    boolean userType = Boolean.parseBoolean(br.readLine());
                    userTerminal = new User(userName, password, userType);
                    String foundPeople = "";
                    String listORSearch;
                    do {
                        listORSearch = br.readLine();
                        if (listORSearch.equals("list")) {
                            do {
                                foundPeople = list(userTerminal.getUsername(), userTerminal);
                                pw.println(foundPeople);
                                pw.flush();
                                found = br.readLine();
                            } while (found.equals("notFound"));
                        } else {
                            do {
                                String compareName = br.readLine();
                                foundPeople = search(userTerminal.getUsername(), userTerminal, compareName);
                                pw.println(foundPeople);
                                pw.flush();
                                found = br.readLine();
                            } while (found.equals("notFound"));
                        }
                        listORSearch = br.readLine();
                    } while (listORSearch.equals("list") || listORSearch.equals("search"));

                    this.senderConvoFileName = br.readLine();
                    this.receiverConvoFileName = br.readLine();

                    String choice = br.readLine();
                    while (!choice.equals("Back")) {
                        switch (choice) {
                            case "View":
                                String convo = getConversation(senderConvoFileName, receiverConvoFileName);
                                String displayMessages = "";
                                String convoChoice;
                                pw.println(convo);
                                pw.flush();
                                convoChoice = br.readLine();
                                while (!convoChoice.equals("Back")) {
                                    switch (convoChoice) {
                                        case "Send":
                                            String status = br.readLine();
                                            if (status.equals("Cancel")) {
                                                break;
                                            }
                                            String message = br.readLine();
                                            sendMessage(message, userTerminal);
                                            convo = getConversation(senderConvoFileName, receiverConvoFileName);
                                            pw.println(convo);
                                            pw.flush();

                                            break;
                                        case "Edit":
                                            displayMessages = displayMessages(userTerminal);
                                            pw.println(displayMessages);
                                            pw.flush();
                                            String isEmpty = br.readLine();
                                            if (isEmpty.equals("Cancel")) {
                                                break;
                                            }
                                            String status2 = br.readLine();
                                            if (status2.equals("Cancel")) {
                                                break;
                                            }
                                            String messageStatus = br.readLine();
                                            if (messageStatus.equals("Cancel")) {
                                                break;
                                            }
                                            String messageToEdit = br.readLine();
                                            String newMessage = br.readLine();
                                            editMessage(messageToEdit, newMessage, userTerminal);
                                            convo = getConversation(senderConvoFileName, receiverConvoFileName);
                                            pw.println(convo);
                                            pw.flush();
                                            break;
                                        case "Delete":
                                            displayMessages = displayMessages(userTerminal);
                                            pw.println(displayMessages);
                                            pw.flush();
                                            String isEmpty2 = br.readLine();
                                            if (isEmpty2.equals("Cancel")) {
                                                break;
                                            }
                                            String status3 = br.readLine();
                                            if (status3.equals("Cancel")) {
                                                break;
                                            }
                                            String messageToDelete = br.readLine();
                                            deleteMessage(messageToDelete);
                                            convo = getConversation(senderConvoFileName, receiverConvoFileName);
                                            pw.println(convo);
                                            pw.flush();
                                            break;
                                        case "Refresh":
                                            convo = getConversation(senderConvoFileName, receiverConvoFileName);
                                            pw.println(convo);
                                            pw.flush();
                                            break;
                                        case "Import":
                                            String status5 = br.readLine();
                                            if (status5.equals("Cancel")) {
                                                break;
                                            }
                                            String filename = br.readLine();
                                            String messageToImport = importFile(filename);
                                            if (messageToImport == null) {
                                                pw.println("File not found");
                                                pw.flush();
                                            } else {
                                                pw.println("File found");
                                                pw.flush();
                                                sendMessage(messageToImport, userTerminal);
                                                convo = getConversation(senderConvoFileName, receiverConvoFileName);
                                                pw.println(convo);
                                                pw.flush();
                                            }
                                            break;
                                        case "Filter":
                                            String status6 = br.readLine();
                                            if (status6.equals("Cancel")) {
                                                break;
                                            }
                                            String replaementStatus = br.readLine();
                                            if (replaementStatus.equals("Cancel")) {
                                                break;
                                            }
                                            String filter = br.readLine();
                                            String replacement = br.readLine();
                                            filterMessage(filter, replacement);
                                            convo = getConversation(senderConvoFileName, receiverConvoFileName);
                                            pw.println(convo);
                                            pw.flush();
                                            break;
                                    }
                                    convoChoice = br.readLine();
                                }
                                break;
                            case "Export":
                                String recipientName = br.readLine();
                                String filename = br.readLine();
                                File csvFile = new File(filename);
                                try {
                                    PrintWriter pw2 = new PrintWriter(new FileWriter(csvFile));
                                    pw2.println("Participants,Message Sender,Timestamp,Contents");
                                    pw2.flush();
                                    pw2.close();
                                    boolean status = export(userTerminal.getUsername(), recipientName, senderConvoFileName, csvFile);
                                    if (status) {
                                        pw.println("Export successful");
                                        pw.flush();
                                    } else {
                                        pw.println("Export unsuccessful");
                                        pw.flush();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                        choice = br.readLine();
                    }


                    pw.close();
                    br.close();
                } catch (IOException e) {
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

    public synchronized String getConversation(String senderConvoFileName, String receiverConvoFileName) {
        String convo = "";
        try {
            File f1 = new File(senderConvoFileName);
            File f2 = new File(receiverConvoFileName);
            if (!f1.exists() && !f2.exists()) {
                f1.createNewFile();
                f2.createNewFile();
            } else if (f2.exists() && !f1.exists()) {
                BufferedReader bfr2 = new BufferedReader(new FileReader(receiverConvoFileName));
                PrintWriter pw3 = new PrintWriter(new FileWriter(senderConvoFileName, true));
                String line;
                while ((line = bfr2.readLine()) != null) {
                    pw3.println(line);
                    pw3.flush();
                }
                bfr2.close();
                pw3.close();
            }
            //filterMessage(filter, replacement);
            BufferedReader bfr2 = new BufferedReader(new FileReader(senderConvoFileName));
            String line;
            while ((line = bfr2.readLine()) != null) {
                convo += line + ",";
            }
            if (isEmpty()) {
                convo += "There are no messages to display. Send a message to start a conversation!";
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
            return null;
        }
        return convo;
    }

    public synchronized boolean export(String senderName, String recipientName, String fileName, File csvFile) {
        try {
            PrintWriter pw2 = new PrintWriter(new FileWriter(csvFile, true));
            BufferedReader bfr2 = new BufferedReader(new FileReader(fileName));
            String line = bfr2.readLine();
            while (line != null) {
                String time = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                String contents = line.substring(line.indexOf(")") + 1);
                String sender = contents.substring(contents.indexOf("-") + 1, contents.indexOf(":"));
                String message = contents.substring(contents.indexOf(":") + 1);

                // Handle special characters
                if (message.contains(",")) {
                    // contents = contents.replace("\"", "\"\"");
                    message = "\"" + message + "\"";
                }

                pw2.append(senderName).append(" and ").append(recipientName).append(",").append(sender).append(",")
                        .append(time).append(",").append(message).append("\n");

                line = bfr2.readLine();
            }
            bfr2.close();
            pw2.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public synchronized void sendMessage(String message, User userTerminal) {
        try {
            PrintWriter pw2 = new PrintWriter(new FileWriter(senderConvoFileName, true));
            PrintWriter pw3 = new PrintWriter(new FileWriter(receiverConvoFileName, true));
            LocalDateTime timestamp = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTimestamp = timestamp.format(formatter);
            if (userTerminal.getUserType()) {
                pw2.println("(" + formattedTimestamp + ") " + "Student-" + userTerminal.getUsername() + ": " + message);
                pw2.flush();
                pw3.println("(" + formattedTimestamp + ") " + "Student-" + userTerminal.getUsername() + ": " + message);
                pw3.flush();
            } else {
                pw2.println("(" + formattedTimestamp + ") " + "Tutor-" + userTerminal.getUsername() + ": " + message);
                pw2.flush();
                pw3.println("(" + formattedTimestamp + ") " + "Tutor-" + userTerminal.getUsername() + ": " + message);
                pw3.flush();
            }
            pw2.close();
            pw3.close();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    public synchronized void editMessage(String message, String newMessage, User userTerminal) {
        File current = new File(senderConvoFileName);
        File f = new File("temp.txt");
        File currentTwo = new File(receiverConvoFileName);
        File fTwo = new File("tempTwo.txt");
        try (BufferedReader bfr = new BufferedReader(new FileReader(senderConvoFileName))) {
            PrintWriter pw2 = new PrintWriter(new FileOutputStream(f));
            PrintWriter pw3 = new PrintWriter(new FileOutputStream(fTwo));
            String line = bfr.readLine();
            while (line != null) {
                if (line.equals(message)) {
                    LocalDateTime timestamp = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formattedTimestamp = timestamp.format(formatter);
                    int hyphenIndex = line.indexOf("-", line.indexOf(')') + 2);
                    if (hyphenIndex != -1) {
                        String userType = line.substring(line.indexOf(")") + 2, hyphenIndex);
                        if (userType.equals("Student")) {
                            pw2.println("(" + formattedTimestamp + ") " + "Student-" + userTerminal.getUsername() + ": "
                                    + newMessage);
                            pw2.flush();
                            pw3.println("(" + formattedTimestamp + ") " + "Student-" + userTerminal.getUsername() + ": "
                                    + newMessage);
                            pw3.flush();
                        } else if (userType.equals("Tutor")) {
                            pw2.println("(" + formattedTimestamp + ") " + "Tutor-" + userTerminal.getUsername() + ": "
                                    + newMessage);
                            pw2.flush();
                            pw3.println("(" + formattedTimestamp + ") " + "Tutor-" + userTerminal.getUsername() + ": "
                                    + newMessage);
                            pw3.flush();
                        }
                    }
                } else {
                    pw2.println(line);
                    pw2.flush();
                    pw3.println(line);
                    pw3.flush();
                }
                line = bfr.readLine();
            }
            pw2.close();
            pw3.close();
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        if (!current.delete()) {
            System.out.println("Error deleting file.");
        }
        if (!f.renameTo(current)) {
            System.out.println("Error renaming file.");
        }
        if (!currentTwo.delete()) {
            System.out.println("Error deleting file.");
        }
        if (!fTwo.renameTo(currentTwo)) {
            System.out.println("Error renaming file.");
        }
    }

    public synchronized void deleteMessage(String message) {
        File current = new File(senderConvoFileName);
        File f = new File("temp.txt");
        try (BufferedReader bfr2 = new BufferedReader(new FileReader(senderConvoFileName))) {
            PrintWriter pw2 = new PrintWriter(new FileWriter(f, true));
            String line = bfr2.readLine();
            while (line != null) {
                if (!line.equals(message)) {
                    pw2.println(line);
                    pw2.flush();
                }
                line = bfr2.readLine();
            }
            pw2.close();
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        if (!current.delete()) {
            System.out.println("Error deleting file.");
        }
        if (!f.renameTo(current)) {
            System.out.println("Error renaming file.");
        }
    }

    public String displayMessages(User userTerminal) {
        String messages = "";
        try (BufferedReader bfr2 = new BufferedReader(new FileReader(senderConvoFileName))) {
            String line;
            while ((line = bfr2.readLine()) != null) {
                if (userTerminal.getUserType()) {
                    if (line.contains("Student-" + userTerminal.getUsername())) {
                        messages += line + ",";
                    }
                } else {
                    if (line.contains("Tutor-" + userTerminal.getUsername())) {
                        messages += line + ",";
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return messages;
    }

    public String findMessage(int index, User userTerminal) {
        try (BufferedReader bfr2 = new BufferedReader(new FileReader(senderConvoFileName))) {
            String line;
            int i = 1;
            while ((line = bfr2.readLine()) != null) {
                if (userTerminal.getUserType()) {
                    if (line.contains("Student-" + userTerminal.getUsername())) {
                        if (i == index) {
                            return line;
                        }
                        i++;
                    }
                } else {
                    if (line.contains("Tutor-" + userTerminal.getUsername())) {
                        if (i == index) {
                            return line;
                        }
                        i++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return null;
    }

    public String importFile(String filename) {
        String message = "";
        try (BufferedReader bfr2 = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bfr2.readLine()) != null) {
                message += line;
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
            return null;
        }
        return message;
    }

    public boolean isEmpty() {
        try (BufferedReader bfr2 = new BufferedReader(new FileReader(senderConvoFileName))) {
            String line;
            while ((line = bfr2.readLine()) != null) {
                return false;
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return true;
    }

    public synchronized boolean filterMessage(String message, String other) {
        File current = new File(senderConvoFileName);
        File f = new File("temp.txt");
        int count = 0;
        try (BufferedReader bfr2 = new BufferedReader(new FileReader(senderConvoFileName))) {
            PrintWriter pw2 = new PrintWriter(new FileWriter(f, true));
            String line = bfr2.readLine();
            while (line != null) {
                if (line.contains(message)) {
                    count++;
                    line = line.replace(message, other);
                }
                pw2.println(line);
                pw2.flush();
                line = bfr2.readLine();
            }
            pw2.close();
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

        if (!current.delete()) {
            System.out.println("Error deleting file.");
        }
        if (!f.renameTo(current)) {
            System.out.println("Error renaming file.");
        }

        return (count > 0);
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
