package echoTCP;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Cliente {

    private static final String HOST = "localhost";
    private static final int PORT = 5000;
    private static final int BUFFER_SIZE = 16;

    public static void main(String[] args) {

        String message = """
                {
                     "nombre": "Juan",
                     "edad": 30,
                     "ciudad": "Ushuaia"
                }
                """;

        try (Socket socket = new Socket(HOST, PORT);
             OutputStream out = socket.getOutputStream()) {

            byte[] mensaje = message.getBytes(StandardCharsets.UTF_8);
            int longitud = mensaje.length;
            int bytesEnviados = 0;

            while (bytesEnviados < longitud) {

                int bytesFaltantes = longitud - bytesEnviados;
                int bytesAEnviar = Math.min(BUFFER_SIZE, bytesFaltantes);

                out.write(mensaje, bytesEnviados, bytesAEnviar);
                out.flush();

                bytesEnviados += bytesAEnviar;
                System.out.printf("Enviados %d bytes. Total enviado: %d/%d%n",
                        bytesAEnviar, bytesEnviados, longitud);
            }

            System.out.println("Mensaje enviado completamente.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
