package transferFileUDP;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {

        int serverPort = 12345;

        String savePath = "/tmp/sockets/archivos-recibidos/recibido";

        try {
            DatagramSocket socket = new DatagramSocket(serverPort);

            FileOutputStream fileOutputStream = new FileOutputStream(savePath);

            byte[] receivedData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receivedData, receivedData.length);
                socket.receive(receivePacket);

                byte[] packetData = receivePacket.getData();
                int packetLength = receivePacket.getLength();

                //
                if (packetLength == 0) {
                    break;
                }

                //
                fileOutputStream.write(packetData, 0, packetLength);
            }

            fileOutputStream.close();
            System.out.println("Archivo recibido y guardado en el servidor en: " + savePath);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
