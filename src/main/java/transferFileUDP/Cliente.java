package transferFileUDP;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Cliente {
    public static void main(String[] args) {

        String serverHost = "localhost"; //127.0.0.1 -

        int serverPort = 12345;

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

                /*if(new Random().nextInt(100) > 2) {
                    socket.send(packet);
                }else{
                    System.out.println("se perdió...");
                }*/
                socket.send(packet);

                // FUNDAMENTAL ??
                //Thread.sleep(10);
            }

            // ???
            DatagramPacket finalPacket = new DatagramPacket(new byte[0], 0, serverAddress, serverPort);

            socket.send(finalPacket);

            System.out.println("Archivo enviado al servidor.");

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
