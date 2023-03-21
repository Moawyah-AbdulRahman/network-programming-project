import java.io.*;
import java.net.Socket;

public class ClientHandlerThread extends Thread{

    private static final DataTransferor dataTransferor = DataTransferor.getInstance();

    private static final Terminal terminal = Terminal.getInstance();

    private final Socket client;
    public ClientHandlerThread(Socket client) {
        super();
        this.client = client;
    }

    @Override
    public void run() {
        terminal.write("Handling client: " + client);

        try (InputStream clientInput = new BufferedInputStream(client.getInputStream());
             DataOutputStream clientOutput = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()))) {

            long fileSize = dataTransferor.calculateInputSize(clientInput);

            clientOutput.writeLong(fileSize);

            terminal.write("Client " + client + " succeeded");

        } catch (IOException e) {
            terminal.write("Client " + client + " failed");
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
