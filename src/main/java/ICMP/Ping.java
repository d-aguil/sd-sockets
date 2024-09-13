package ICMP;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Ping {
    public static void main(String[] args) {
        try {
            // Ejecutar el comando ping
            String host = "google.com";
            Process process = Runtime.getRuntime().exec("ping " + host);

            // Leer la salida del comando ping
            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = inputStream.readLine()) != null) {
                System.out.println(line);  // Imprime la respuesta del ping
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}