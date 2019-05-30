/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ip2;

import java.io.IOException;

/**
 *
 * @author jay.dave
 */
public class Client {

    public static void main(String[] agrs) throws IOException {
        Server newServer = new Server(9191);
        new Thread(newServer).start();

        try {
            Thread.sleep(15 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping newServer");
        newServer.stop();
    }
}
