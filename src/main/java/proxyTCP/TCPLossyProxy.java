package proxyTCP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TCPLossyProxy {

    public static void main(String[] args) {
        int proxyPort = 5000; // Puerto del proxy
        String targetHost = "localhost"; // Host del servidor de destino
        int targetPort = 8080; // Puerto del servidor de destino
        double lossProbability = 0.3; // Probabilidad de pérdida de paquetes (0.0 - 1.0)

        try {
            ServerSocket serverSocket = new ServerSocket(proxyPort);
            System.out.println("Proxy en ejecución en el puerto " + proxyPort);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                Thread proxyThread = new Thread(() -> {
                    try {
                        Socket targetSocket = new Socket(targetHost, targetPort);
                        InputStream clientInput = clientSocket.getInputStream();
                        OutputStream clientOutput = clientSocket.getOutputStream();
                        InputStream targetInput = targetSocket.getInputStream();
                        OutputStream targetOutput = targetSocket.getOutputStream();

                        byte[] buffer = new byte[1024];
                        int bytesRead;

                        while ((bytesRead = clientInput.read(buffer)) != -1) {
                            // Simular pérdida de paquetes
                            if (Math.random() < lossProbability) {
                                System.out.println("Paquete TCP perdido");
                                continue;
                            }

                            targetOutput.write(buffer, 0, bytesRead);
                            targetOutput.flush();

                            // Leer la respuesta del servidor de destino y reenviarla al cliente
                            bytesRead = targetInput.read(buffer);
                            clientOutput.write(buffer, 0, bytesRead);
                            clientOutput.flush();
                        }

                        clientSocket.close();
                        targetSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                proxyThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
