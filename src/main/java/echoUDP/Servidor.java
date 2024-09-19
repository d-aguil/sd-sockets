package echoUDP;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Servidor {
    public static void main(String[] args) {

        int serverPort = 12345;

        try {
            DatagramSocket socket = new DatagramSocket(serverPort);

            byte[] receivedData = new byte[1024];

            while (true) {
                System.out.println("Esperando paquetes....");
                DatagramPacket receivePacket = new DatagramPacket(receivedData, receivedData.length);
                socket.receive(receivePacket);

                System.out.println("paquete recibido");

                byte[] data = receivePacket.getData();
                int length = receivePacket.getLength();

                // Convertir los bytes a String
                String mensaje = new String(data, 0, length, StandardCharsets.UTF_8);

                System.out.println("longitud: "+ length);
                System.out.println("contenido: "+ mensaje);
                //
                if (length == 0) {
                    System.out.println("Señal de apagado recibida");
                    break;
                }

                //
                System.out.println();
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
