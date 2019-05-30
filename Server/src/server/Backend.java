/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.IOException;

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
                String client_response = readStream(in);

                ServerCommunicationHelper sch = new ServerCommunicationHelper();
                sch.unpackageFields(client_response);
                
                userChoiceFileIO(sch, socket);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void userChoiceFileIO(ServerCommunicationHelper sch, Socket socket) throws IOException {
        boolean isRead = Boolean.parseBoolean(sch.dictionary.get("|opperation"));

        String file_path = (sch.dictionary.get("destination") + ".txt");

        if (isRead) {
            String data = readFile(file_path);
            System.out.println(data);

            OutputStream outToServer = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF(data);
        } else {
            writeDataToFile(sch.dictionary.get("|content"), file_path);
        }
    }

    private static String readFile(String destination) throws FileNotFoundException, IOException {
        FileReader reader = new FileReader("../" + destination); //create reader
        BufferedReader br = new BufferedReader(reader); //buffered reader to read line of text
        StringBuilder out = new StringBuilder();

        String line = br.readLine(); //read line of text
        while (line != null) { //if line is not empty
            out.append(line);
            line = br.readLine(); //read next line
        }
        return out.toString();
    }

    private static String readStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder client_response = new StringBuilder();
        String line;
        boolean state = true;
        while (state) {
            line = reader.readLine();
            client_response.append(line);
            state = false;
        }
        return client_response.toString() + "\n ";
    }

    private static void writeDataToFile(String client_response, String fileName) throws IOException {
        try {
            File file = new File("../" + fileName);
            FileWriter writer = new FileWriter(file, true);
            BufferedWriter bwr = new BufferedWriter(writer);
            bwr.write(client_response);
            bwr.write(" \n ");
            bwr.close();
            System.out.println("succesfully written to a file");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

}
