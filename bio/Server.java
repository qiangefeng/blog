import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        System.out.println("I am Server");
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(18080);

            while (true) {
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                System.out.println("rev from client: "+inputStream.read());
                System.out.println("send to client 12");
                outputStream.write(12);
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

