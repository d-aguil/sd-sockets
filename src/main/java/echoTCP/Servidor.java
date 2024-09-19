package echoTCP;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Servidor {

    private static final int PORT = 5000;
    private static final int BUFFER_SIZE = 16;

    public static void main(String[] args) {

        try(ServerSocket server = new ServerSocket(PORT)){

            System.out.println("escuchando en el puerto 5000");

            while (true)
                try (Socket cliente = server.accept()) {

                    System.out.println(cliente + " conectado\n");

                    try (DataInputStream dataInputStream = new DataInputStream(cliente.getInputStream())) {

                        //
                        byte[] buffer = new byte[BUFFER_SIZE];
                        int bytes;

                        //
                        StringBuilder mensaje = new StringBuilder();
                        while ((bytes = dataInputStream.read(buffer, 0, BUFFER_SIZE)) > 0) {
                            System.out.print(".");
                            mensaje.append(new String(buffer, 0, bytes, StandardCharsets.UTF_8));

                        }
                        System.out.println("\n" + mensaje.toString());

                    }
                }

        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}