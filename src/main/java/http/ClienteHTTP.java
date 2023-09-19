package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ClienteHTTP {

    public static void main(String[] args) throws IOException {
        // Dirección IP y puerto del servidor web
        String servidorIP = "www.untdf.edu.ar";//"www.untdf.edu.ar:443 no se puede por el certificado";
        int puerto = 80;

        // Recurso en la URL que deseas solicitar
        String recurso = "/";

        // Construir la solicitud HTTP
        String solicitud = "GET " + recurso + " HTTP/1.1\r\n";
        solicitud += "Host: " + servidorIP + "\r\n";
        solicitud += "Connection: close\r\n\r\n";

        try (Socket socket = new Socket(servidorIP, puerto);
             OutputStream outputStream = socket.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Enviar la solicitud HTTP al servidor
            outputStream.write(solicitud.getBytes());
            outputStream.flush();

            // Leer la respuesta del servidor
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        }
    }
}
