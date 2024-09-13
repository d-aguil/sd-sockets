package http;

import java.io.*;
import java.net.Socket;

//TODO TERMINAR!!!
public class RecursosHttp {

    public static BufferedReader getReader(String servidorIP, int puerto, String recurso)throws Exception{

        // Construir la solicitud HTTP
        String solicitud = "GET " + recurso + " HTTP/1.1\r\n";
        solicitud += "Host: " + servidorIP + "\r\n";
        solicitud += "Connection: close\r\n\r\n";

        Socket socket = new Socket(servidorIP, puerto);
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }
    public static void main(String[] args) {

        try {

            //http://www.ipvtdf.gov.ar/#!/compras
            BufferedReader reader = getReader("concejoushuaia.sytes.net", 80, "/asuntos/index.php");

            showInTerminal(reader);
            
            

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private static void showInTerminal(BufferedReader reader) throws IOException {

        String linea;
        while ((linea = reader.readLine()) != null) {
            System.out.println(linea);

        }
    }
}
