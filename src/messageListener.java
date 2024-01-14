import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class messageListener implements Runnable{
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String username;

    @Override
    public void run() {
        String response;
        while (socket.isConnected()) {
            try {
                response = reader.readLine();
                System.out.println(username + ": " + response);
            }
            catch (IOException e) {
                closeAll(socket, reader, writer);
            }
        }
    }
    messageListener(Socket socket, BufferedReader reader, PrintWriter writer, String username) {
        this.socket = socket;
        this.reader = reader;
        this.writer = writer;
        this.username = username;
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
}
