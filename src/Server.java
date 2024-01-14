import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private BufferedReader reader;
    private PrintWriter writer;

    private static Socket socket;
    private static ServerSocket serverS;

    private static final int PORT = 9090;

    public static void main(String[] args) throws IOException {
        serverS = new ServerSocket(PORT);
        System.out.println("Waiting for client to connect...");
        socket = serverS.accept();
        System.out.println("Client has connected!");
        Server server = new Server(socket);
        server.listenMessage();
        server.sendMessage();
        server.closeAll(socket, server.reader, server.writer);

    }

    Server(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
    }
    public void sendMessage() {
        String response;
        Scanner scanner = new Scanner(System.in);
        while (socket.isConnected()) {
            response = scanner.nextLine();
            writer.println(response);
        }
    }

    public void closeAll(Socket socket, BufferedReader reader, PrintWriter writer) {
        try {
            if (socket != null) {
                socket.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void listenMessage () {
        messageListener listen = new messageListener(socket, reader, writer, "Client");
        Thread thread = new Thread(listen);
        thread.start();
    }
}

