package http;

import java.io.*;
import java.net.Socket;

public class ClienteHTTP {

    public static void main(String[] args) throws IOException {

        // Dirección IP y puerto del servidor web
        String servidor = "www.untdf.edu.ar";
        int puerto = 443;

        // Recurso en la URL que deseas solicitar
        String recurso = "/";

        // Construir la solicitud HTTP
        String solicitud = "GET " + recurso + " HTTP/1.1\r\n";
        solicitud += "Host: " + servidor + "\r\n";
        solicitud += "Connection: close\r\n\r\n";

        //
        try (Socket socket = new Socket(servidor, puerto);
             OutputStream outputStream = socket.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Enviar la solicitud HTTP al servidor
            outputStream.write(solicitud.getBytes());
            outputStream.flush();

            // Crear el BufferedWriter para escribir en el archivo
            BufferedWriter writer = new BufferedWriter(new FileWriter("salida.html"));

            // Leer la respuesta del servidor y escribir en el archivo
            String linea;
            while ((linea = reader.readLine()) != null) {

                //
                System.out.println(linea);

                //
                writer.write(linea);
                writer.newLine();
            }
        }


    }
}
