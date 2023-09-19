package transferFileTCP;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(5000)){

            System.out.println("listening to port:5000");

            try (Socket clientSocket = serverSocket.accept()) {

                System.out.println(clientSocket + " connected\n");

                DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());

                //
                try (FileOutputStream fileOutputStream = new FileOutputStream("/tmp/sockets/archivos-recibidos/recibido")) {

                    //
                    byte[] buffer = new byte[1024];
                    int bytes;

                    //
                    while ((bytes = dataInputStream.read(buffer, 0, buffer.length)) > 0) {
                        fileOutputStream.write(buffer, 0, bytes);
                    }

                }
            }

        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}