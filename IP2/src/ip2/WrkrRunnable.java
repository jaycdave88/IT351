/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ip2;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author jay.dave
 */

public class WrkrRunnable implements Runnable{
    protected Socket clntSocket = null;
    protected String txtFrmSrvr   = null;
 
    public WrkrRunnable(Socket clntSocket, String txtFrmSrvr) {
        this.clntSocket = clntSocket;
        this.txtFrmSrvr   = txtFrmSrvr;
    }
    public void run() {
        try {
            InputStream inputstrm  = clntSocket.getInputStream();
            OutputStream outputstrm = clntSocket.getOutputStream();
            long timetaken = System.currentTimeMillis();
            outputstrm.write(("OK\n\nWrkrRunnable: " + this.txtFrmSrvr + " - " +timetaken +"").getBytes());
            outputstrm.close();
            inputstrm.close();
            System.out.println("Your request has processed in time : " + timetaken);
        } catch (IOException e) {           
            e.printStackTrace();
        }
    }
}
