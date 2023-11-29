import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server implements Runnable {
    Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.out.printf("Client connected: %s%n", socket);
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            Scanner in = new Scanner(socket.getInputStream());
            





            pw.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
