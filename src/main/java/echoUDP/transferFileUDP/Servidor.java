
import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {

        int serverPort = 12345;

        try {
            DatagramSocket socket = new DatagramSocket(serverPort);

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
                System.out.println(new String(packetData,packetLength));
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
