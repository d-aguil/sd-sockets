package echoUDP;

import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {

        String serverHost = "localhost"; //127.0.0.1 -

        int serverPort = 12345;

        try {
            InetAddress serverAddress = InetAddress.getByName(serverHost);

            DatagramSocket socket = new DatagramSocket();

            byte[] buffer = args[0].getBytes();
            System.out.println("enviado contenido: " + new String(buffer));
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);

            socket.send(packet);

        } catch (UnknownHostException ex) {
            throw new RuntimeException(ex);
        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}
