package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class ClienteHTTPS {
    public static void main(String[] args) throws IOException {
        // URL a la que deseas realizar la solicitud HTTPS
        String urlStr = "https://www.untdf.edu.ar";

        // Crear una URL con la dirección
        URL url = new URL(urlStr);

        // Abrir una conexión HTTPS segura
        HttpsURLConnection conexion = (HttpsURLConnection) url.openConnection();

        // Establecer un tiempo de espera para la conexión (opcional)
        conexion.setConnectTimeout(5000); // 5 segundos

        // Realizar la solicitud GET
        conexion.setRequestMethod("GET");

        // Obtener y mostrar la respuesta del servidor
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } finally {
            // Cerrar la conexión
            conexion.disconnect();
        }
    }
}