import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        System.out.println("I am client");
        try {
            Socket socket = new Socket("127.0.0.1", 18080);
            System.out.println("send to server 11");
            socket.getOutputStream().write(11);
            System.out.println("rev from server: "+ socket.getInputStream().read());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
