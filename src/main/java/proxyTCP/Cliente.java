package proxyTCP;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.Socket;

public class Cliente {

        public static void main(String[] args) {

            try(Socket socket = new Socket("localhost",5000)){

                //
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                //
                try (FileInputStream fileInputStream = new FileInputStream("/tmp/sockets/archivo")) {

                    byte[] buffer = new byte[1024];
                    int bytes;

                    //
                    while ((bytes = fileInputStream.read(buffer, 0, buffer.length)) > 0) {
                        dataOutputStream.write(buffer, 0, bytes);
                    }
                }

            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
}
