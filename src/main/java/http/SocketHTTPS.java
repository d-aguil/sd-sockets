package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SocketHTTPS {

    public static void main(String[] args) throws IOException {
        // Dirección del servidor y puerto 443 para HTTPS
        String servidor = "www.untdf.edu.ar";
        int puerto = 443;

        // Crear una fábrica de sockets SSL
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

        // Crear un socket SSL seguro
        try (SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket(servidor, puerto)) {
            // Establecer un tiempo de espera para la conexión (opcional)
            socket.setSoTimeout(5000); // 5 segundos

            // Iniciar el handshake SSL
            socket.startHandshake();

            // Obtener flujos de entrada y salida para comunicarse con el servidor
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Realizar una solicitud GET segura
            String solicitud = "GET / HTTP/1.1\r\n";
            solicitud += "Host: " + servidor + "\r\n";
            solicitud += "Connection: close\r\n\r\n";

            outputStream.write(solicitud.getBytes());
            outputStream.flush();

            // Leer y mostrar la respuesta del servidor
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        }
    }
}