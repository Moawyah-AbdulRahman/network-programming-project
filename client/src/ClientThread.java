import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class ClientThread extends Thread{

    private static final DataTransferor transferor = DataTransferor.getInstance();

    private static final Terminal terminal = Terminal.getInstance();

    private static final String DOMAIN_NAME = "localhost";

    private static final int PORT = 1234;

    @Override
    public void run() {
        String urlString = terminal.read("Enter image URL to download.");

        String pathToSave = "./" + terminal.read("Save in path: ") + ".TIFF";

        try {
            downloadImage(urlString, pathToSave);

            sendToServer(pathToSave);

        }catch (Exception ignored){
            System.exit(1);
        }
    }

    private static void downloadImage(String urlString, String pathToSave) throws IOException {
        assertDirectoryExists(pathToSave);

        URLConnection connection = new URL(urlString).openConnection();

        try( InputStream internetInputStream = connection.getInputStream();
             OutputStream output = new BufferedOutputStream(new FileOutputStream(pathToSave)) ) {
            transferor.transfer(internetInputStream, output);
        }
    }

    private static  void assertDirectoryExists(String fullPath) {
        String dirPath = fullPath.substring(0, fullPath.lastIndexOf('/'));
        File dir = new File(dirPath);

        if(!dir.exists()) {
            dir.mkdirs();
        }
    }

    private static void sendToServer(String pathToSave) throws IOException {
        try(Socket socket = new Socket(DOMAIN_NAME, PORT);
            InputStream fileInput = new BufferedInputStream(new FileInputStream(pathToSave));
            OutputStream serverWriter = new BufferedOutputStream(socket.getOutputStream());
            DataInputStream serverReader = new DataInputStream(new BufferedInputStream(socket.getInputStream()))
        ) {

            transferor.transfer(fileInput, serverWriter);
            socket.shutdownOutput();

            long bytesReceived = serverReader.readLong();

            terminal.write("Image size: " + bytesReceived + " Bytes.");
        }
    }
}
