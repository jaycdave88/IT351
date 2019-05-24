/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jay.dave
 */
public class Backend {

    private static InputStream input;
    private static int port = 4444;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("New client connected");

                InputStream in = socket.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                String client_response = convertInputStreamToString(reader);
                
                ServerCommunicationHelper sch = new ServerCommunicationHelper();
                sch.unpackageFields(client_response);
                for (Map.Entry<String, String> entry : sch.dictionary.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    
                    System.out.println(key);
                    System.out.println(value);
                }

                    writeDataToFile(sch.dictionary.get("content"), "./"+sch.dictionary.get("destination") + ".txt");

                }

            }catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        }

    

    private static String convertInputStreamToString(InputStreamReader reader) throws IOException {

        char[] buffer = new char[4096];
        StringBuilder sb = new StringBuilder();
        for (int len; (len = reader.read(buffer)) > 0;) {
            sb.append(buffer, 0, len);
        }
        return sb.toString();
    }

    private static void writeDataToFile(String client_response, String fileName) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(fileName);
        byte[] strToBytes = client_response.getBytes();
        try {
            outputStream.write(strToBytes);
        } catch (IOException ex) {
            Logger.getLogger(Backend.class.getName()).log(Level.SEVERE, null, ex);
        }

        outputStream.close();
    }

}
