/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system;

import chat.system.gui.MainGUI;
import chat.system.server.Server;
import java.io.IOException;
import java.net.UnknownHostException;

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
        // Print out all args
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }

        // Run the server in a background thread
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Server.main(new String[0]);
            }
        });
        t.start();
        Thread.sleep(2500);
        
        
        // Show the main gui
        new MainGUI().setVisible(true);
    }
}
