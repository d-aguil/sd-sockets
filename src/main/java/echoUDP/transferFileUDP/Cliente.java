package echoUDP.transferFileUDP;

import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {

        String serverHost = "localhost"; //127.0.0.1 -

        int serverPort = 12345;

        try {
            InetAddress serverAddress = InetAddress.getByName(serverHost);

            DatagramSocket socket = new DatagramSocket();



            byte[] buffer = "hola mundo....".getBytes();


                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);

                socket.send(packet);

                // FUNDAMENTAL ??
                //Thread.sleep(10);
            } catch (UnknownHostException ex) {
            throw new RuntimeException(ex);
        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}
