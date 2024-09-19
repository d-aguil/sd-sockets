package echoWebSocket;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;

public class Servidor {

    private static final int PORT = 5000;
    private static final int BUFFER_SIZE = 16;


    public static void main(String[] args) throws Exception {

        //
        ServerSocket servidor = new ServerSocket(PORT);

        //
        System.out.println("escuchando en el puerto 5000");

        //
        while (true) {
            //
            Socket cliente = servidor.accept();
            System.out.println(cliente + " conectado\n");

            //
            manejarLaConexion(cliente);
        }
    }

    private static void manejarLaConexion(Socket cliente) throws Exception {

        InputStream entrada = cliente.getInputStream();
        OutputStream salida = cliente.getOutputStream();

        BufferedReader lector = new BufferedReader(new InputStreamReader(entrada));

        //(handshake)
        String linea;
        StringBuilder solicitud = new StringBuilder();
        String websocketKey = null;

       /* //
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytes;

        //
        StringBuilder mensaje = new StringBuilder();
        while ((bytes = lector.read(buffer, 0, BUFFER_SIZE)) > 0) {
            System.out.print(".");
            mensaje.append(new String(buffer, 0, bytes, StandardCharsets.UTF_8));

        }*/

        while ((linea = lector.readLine()) != null && !linea.isEmpty()) {
            solicitud.append(linea).append("\r\n");
            if (linea.startsWith("Sec-WebSocket-Key")) {
                websocketKey = linea.split(": ")[1];
            }
        }

        // continuamos con el handshake del protocolo WebSocket
        if (websocketKey != null) {
            System.out.println("Handshake WebSocket recibido");
            String acceptKey = generateWebSocketAcceptKey(websocketKey);
            String respuestaConHandShake = "HTTP/1.1 101 Switching Protocols\r\n" +
                    "Upgrade: websocket\r\n" +
                    "Connection: Upgrade\r\n" +
                    "Sec-WebSocket-Accept: " + acceptKey + "\r\n\r\n";
            salida.write(respuestaConHandShake.getBytes(StandardCharsets.UTF_8));
            salida.flush();
            System.out.println("Handshake completado impecablemente!!!");

            // Leer mensajes del cliente (WebSocket frame)
            while (true) {
                int firstByte = entrada.read();
                if (firstByte == -1) {
                    break;
                }

                // Decodificar frame del WebSocket
                int secondByte = entrada.read();
                // Máscara para obtener el tamaño máximo en 7 bits
                int payloadLength = secondByte & 0x7F;

                // La clave de máscara
                byte[] maskingKey = new byte[4];
                entrada.read(maskingKey);

                // El mensaje encriptado
                byte[] encodedMessage = new byte[payloadLength];
                entrada.read(encodedMessage);

                // Desencriptamos
                byte[] decodedMessage = new byte[payloadLength];
                for (int i = 0; i < payloadLength; i++) {
                    decodedMessage[i] = (byte) (encodedMessage[i] ^ maskingKey[i % 4]);
                }

                String clientMessage = new String(decodedMessage, StandardCharsets.UTF_8);
                System.out.println("Mensaje recibido del cliente: " + clientMessage);

                // Respondemos con un bonito mensaje
                sendMessage(salida, "mortadela ["+ Instant.now()+"]");
            }
        }else{
            System.out.println("No parece ser una conexión del tipo websocket!!!");
        }

        cliente.close();
    }

    private static String generateWebSocketAcceptKey(String key) throws Exception {
        //el código GUID definido en https://www.rfc-editor.org/rfc/rfc6455.html
        String guid = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        String magia = key + guid;

        // SHA-1 hash
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hashedBytes = digest.digest(magia.getBytes(StandardCharsets.UTF_8));

        // Convertir a Base64
        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    private static void sendMessage(OutputStream salida, String mensaje) throws IOException {
        byte[] messageBytes = mensaje.getBytes(StandardCharsets.UTF_8);
        int messageLength = messageBytes.length;

        // Crear frame WebSocket
        ByteArrayOutputStream frame = new ByteArrayOutputStream();
        // Primer byte: texto y fin de frame
        frame.write(0x81);
        if (messageLength <= 125) {
            // Segundo byte: longitud
            frame.write(messageLength);
        } else if (messageLength <= 65535) {
            // 2 bytes para longitud
            frame.write(126);
            frame.write((messageLength >> 8) & 0xFF);
            frame.write(messageLength & 0xFF);
        }

        //el mensaje
        frame.write(messageBytes);
        salida.write(frame.toByteArray());
        salida.flush();
    }
}
