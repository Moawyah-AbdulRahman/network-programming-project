import java.util.Scanner;

public class Terminal {
    private static volatile Terminal terminal;

    private final Scanner scanner = new Scanner(System.in);

    public static Terminal getInstance(){
        if (terminal == null){
            synchronized (DataTransferor.class){
                if (terminal == null){
                    terminal = new Terminal();
                }
            }
        }
        return terminal;
    }

    public String read(String displayMassage){
        write(displayMassage);
        return read();
    }

    public String read(){
        return scanner.nextLine().trim();
    }

    public void write(Object massage){
        System.out.println(massage);
    }
}
