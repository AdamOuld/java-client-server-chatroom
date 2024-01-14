import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 9090);
        System.out.println("You have connected to the server! Enter a message to communicate!");
        Client client = new Client(socket);
        client.listenMessage();
        client.sendMessage();
        client.closeAll(socket, client.reader, client.writer);


    }
    Client(Socket socket) throws IOException {
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
        messageListener listen = new messageListener(socket, reader, writer, "Server");
        Thread thread = new Thread(listen);
        thread.start();
    }

}