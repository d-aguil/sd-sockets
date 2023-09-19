package proxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPLossyProxy {

    public static void main(String[] args) {
        int proxyPort = 5000; // Puerto del proxy
        int targetPort = 8080; // Puerto del servidor de destino
        String targetHost = "localhost"; // Host del servidor de destino
        double lossProbability = 0.01; // Probabilidad de pérdida de paquetes (0.0 - 1.0)

        try {
            DatagramSocket proxySocket = new DatagramSocket(proxyPort);
            DatagramSocket targetSocket = new DatagramSocket();
            InetAddress targetAddress = InetAddress.getByName(targetHost);

            System.out.println("Proxy en ejecución en el puerto " + proxyPort);

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                proxySocket.receive(receivePacket);

                // Simular pérdida de paquetes
                if (Math.random() < lossProbability) {
                    System.out.println("Paquete perdido");
                    continue;
                }

                byte[] packetData = receivePacket.getData();
                int packetLength = receivePacket.getLength();


                DatagramPacket sendPacket = new DatagramPacket(packetData, packetLength, targetAddress, targetPort);
                targetSocket.send(sendPacket);

                //System.out.println("Reenviado paquete al servidor de destino: " + targetHost + ":" + targetPort);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
