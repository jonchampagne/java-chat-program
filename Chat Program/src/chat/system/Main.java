/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system;

import chat.system.gui.MainGUI;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 *
 * @author jon
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, IOException{
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        new MainGUI().setVisible(true);
    }
}
