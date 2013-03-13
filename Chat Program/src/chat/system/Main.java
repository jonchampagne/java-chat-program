/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system;

import chat.system.gui.MainGUI;
import chat.system.server.Server;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jon
 */
public class Main {

    /**
     *
     * Starts the server, then the client.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Server.main(new String[0]);
            }
        });
        t.start();
        Thread.sleep(2500);
        new MainGUI().setVisible(true);
    }
}
