import java.io.IOException;
import java.net.ServerSocket;

public class ExitThread extends Thread{

    private final ServerSocket serverSocket;
    private final Terminal terminal = Terminal.getInstance();
    private static final String EXIT_STRING = "exit";

    public ExitThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while(true) {
            if(terminal.read().trim().equalsIgnoreCase(EXIT_STRING)){
                try {
                    serverSocket.close();
                } catch (IOException ignored) {
                }finally {
                    System.exit(0);
                }
            }
        }
    }
}
