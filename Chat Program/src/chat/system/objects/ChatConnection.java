/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system.objects;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jon
 */
public class ChatConnection extends Observable implements Runnable {

    private ChatPerson self;
    private ObjectOutputStream oOut;
    private ObjectInputStream oIn;
    private String uname;

    public ChatConnection(String address, int port) throws UnknownHostException, IOException {
        Socket server = new Socket(address, port);
        oOut = new ObjectOutputStream(server.getOutputStream());
        oIn = new ObjectInputStream(server.getInputStream());
        self=new ChatPerson(uname);
        oOut.writeObject(self);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object o = oIn.readObject();
                if (o instanceof ChatMessage) {
                    System.out.println("changed");
                    this.setChanged();
                    this.notifyObservers(o);
                }
            } catch(EOFException ex) {
                break;
            } catch (IOException ex) {
                Logger.getLogger(ChatConnection.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ChatConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JOptionPane.showMessageDialog(null, "Server connection lost. Closing.");
        System.exit(0);
    }

    public void sendObject(Object o) throws IOException {
        System.out.println("Sending message");
        oOut.writeObject(o);
    }

    public ChatPerson getSelf() {
        return self;
    }
    
    public void setSelf(ChatPerson self) throws IOException
    {
        this.self=self;
        this.sendObject(self);
    }
}
