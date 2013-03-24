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
 * A client's connection to the server
 *
 * @author jon
 */
public class ChatConnection extends Observable implements Runnable {

    private ChatPerson self;
    private ObjectOutputStream oOut;
    private ObjectInputStream oIn;
    private String uname;
    private Socket server;

    public ChatConnection(String address, int port) throws UnknownHostException, IOException {
        server = new Socket(address, port);
        oOut = new ObjectOutputStream(server.getOutputStream());
        oIn = new ObjectInputStream(server.getInputStream());
        self = new ChatPerson(uname);
        oOut.writeObject(self);
        this.requestUserList();
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
                } else if (o instanceof ChatPerson) {
                    this.setChanged();
                    this.notifyObservers(o);
                } else if (o instanceof ServerMessage) {
                    if (((ServerMessage) o).getServerCode() == 1 || ((ServerMessage) o).getServerCode() == 3 || ((ServerMessage) o).getServerCode() == 4) {
                        this.setChanged();
                        this.notifyObservers(o);
                    }
                }
            } catch (EOFException ex) {
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

    public String getHost() {
        return server.getInetAddress().getCanonicalHostName();
    }

    private void requestUserList() throws IOException {
        ServerMessage message = new ServerMessage(2, self);
        this.sendObject(message);
    }

    public void notifyTyping() throws IOException {
        ServerMessage message=new ServerMessage(4,self);
        this.sendObject(message);
    }
}
