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
 * A client's connection to the server. Runs as a thread in the background listening for messages
 * from the server
 *
 * @author jon
 */
public class ChatConnection extends Observable implements Runnable {

    private ChatPerson self;
    // Send raw objects to the server
    private ObjectOutputStream oOut;
    // Receive raw objects from the server
    private ObjectInputStream oIn;
    private String uname;
    // Main connection to the server
    private Socket server;
    
    
    /**
     * Init the connection and tell the server about us. to notify to other users.
     * @param address
     * @param port
     * @throws UnknownHostException
     * @throws IOException 
     */
    public ChatConnection(String address, int port) throws UnknownHostException, IOException {
        server = new Socket(address, port);
        oOut = new ObjectOutputStream(server.getOutputStream());
        oIn = new ObjectInputStream(server.getInputStream());
        self = new ChatPerson(uname);
        oOut.writeObject(self);
        oOut.writeObject(new ServerMessage(2));
    }

    @Override
    /**
     * Start running in the background
     */
    public void run() {
        while (true) {
            try {
                // It'll wait here til we get something.
                Object o = oIn.readObject();
                System.out.println(o);
                if (o instanceof ChatMessage) {
                    System.out.println("changed");
                    this.setChanged();
                    this.notifyObservers(o);
                } else if (o instanceof ChatPerson) {
                    //System.out.println(((ChatPerson)o).getName());
                    this.setChanged();
                    this.notifyObservers(o);
                } else if (o instanceof ServerMessage) {
                    if (((ServerMessage) o).getServerCode() == 1) {
                        this.setChanged();
                        this.notifyObservers(o);
                    }
                }
            } catch (EOFException ex) {
                // Server disconnected. Stop running.
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

    /**
     * Sends an object to the server
     *
     * @param o
     * @throws IOException
     */
    public void sendObject(Object o) throws IOException {
        System.out.println("Sending message");
        oOut.writeObject(o);
    }

    /**
     * Gets the ChatPerson associated with with this connection
     *
     * @return
     */
    public ChatPerson getSelf() {
        return self;
    }

    /**
     * Sets the ChatPerson on the client and the server
     *
     * @param self
     * @throws IOException
     */
    public void setSelf(ChatPerson self) throws IOException {
        this.self = self;
        this.sendObject(self);
    }

    /**
     * Changes the server connected to
     *
     * @param address
     * @param port
     * @throws UnknownHostException
     * @throws IOException
     */
    public void setServer(String address, int port) throws UnknownHostException, IOException {
        oOut.close();
        oIn.close();
        server.close();
        server = new Socket(address, port);
        oOut = new ObjectOutputStream(server.getOutputStream());
        oIn = new ObjectInputStream(server.getInputStream());
        oOut.writeObject(self);
    }
/**
 * gets the hostname of the server
 * @return 
 */
    public String getHost() {
        return server.getInetAddress().getCanonicalHostName();
    }
}
