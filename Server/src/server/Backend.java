/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

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
                String file_path = (sch.dictionary.get("destination") + ".txt");
                writeDataToFile(sch.dictionary.get("content"), file_path);
            }

        } catch (IOException ex) {
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
        try {
            File file = new File("../"+ fileName);
            FileWriter writer = new FileWriter(file, true);
            BufferedWriter bwr = new BufferedWriter(writer);
            bwr.write(client_response);
            bwr.write("\n");
            bwr.close();
            System.out.println("succesfully written to a file");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

}
