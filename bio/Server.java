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
            int a=0;
            while (true) {
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                System.out.println(a++ + " rev from client: "+inputStream.read());
                
                String response = "HTTP/1.1 200 OK\r\n" +
                                "Content-Length: 11\r\n\r\n" +
                                "Hello World";
                outputStream.write(response.getBytes());

                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

