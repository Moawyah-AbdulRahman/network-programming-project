import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataTransferor {

    private static volatile DataTransferor transferor;

    public static DataTransferor getInstance(){
        if (transferor == null){
            synchronized (DataTransferor.class){
                if (transferor == null){
                    transferor = new DataTransferor();
                }
            }
        }
        return transferor;
    }

    private static final int PACKET_SIZE = 1024*100;

    public void transfer(InputStream src, OutputStream dest) throws IOException {
        byte[] input = new byte[PACKET_SIZE];
        int bytesRead;

        while ((bytesRead = src.read(input, 0, input.length)) != -1){
            dest.write(input, 0, bytesRead);
            dest.flush();
        }
    }

    public long calculateInputSize(InputStream src) throws IOException {
        byte[] input = new byte[PACKET_SIZE];
        int bytesRead;
        long total = 0;

        while ((bytesRead = src.read(input, 0, input.length)) != -1){
            total += bytesRead;
        }

        return total;
    }
}
