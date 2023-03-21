import java.io.*;
import java.net.ServerSocket;

public class Main {
    private static final int PORT = 1234;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);

        new ExitThread(serverSocket).start();

        while(true) {
            new ClientHandlerThread(serverSocket.accept()).start();
        }
    }

}