package proxy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Cliente {
    public static void main(String[] args) {

        String serverHost = "localhost";

        int serverPort = 5000;

        String filePath = "/tmp/sockets/archivo";

        try {
            InetAddress serverAddress = InetAddress.getByName(serverHost);

            DatagramSocket socket = new DatagramSocket();

            File fileToSend = new File(filePath);

            FileInputStream fileInputStream = new FileInputStream(fileToSend);

            byte[] buffer = new byte[1024];

            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {

                DatagramPacket packet = new DatagramPacket(buffer, bytesRead, serverAddress, serverPort);

                //
                socket.send(packet);

                // FUNDAMENTAL ??
                Thread.sleep(10);
            }

            // ???
            DatagramPacket finalPacket = new DatagramPacket(new byte[0], 0, serverAddress, serverPort);

            socket.send(finalPacket);

            System.out.println("Archivo enviado al servidor.");

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
