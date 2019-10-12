import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    Socket isocket;
    public Server(Socket socket){
        this.isocket=socket;
    }

    @Override
    public void run() {
        try {
        InputStream inputStream = isocket.getInputStream();
        OutputStream outputStream = isocket.getOutputStream();
        System.out.println(Thread.currentThread().getName()+" rev from client: "+inputStream.read());

        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: 11\r\n\r\n" +
                "Hello World";

            outputStream.write(response.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                isocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static void main(String[] args) {
        System.out.println("I am Server");

        try {
            ServerSocket serverSocket = new ServerSocket(18080);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new Server(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
