/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system.server;

import chat.system.objects.ChatMessage;
import chat.system.objects.ChatPerson;
import chat.system.objects.ServerMessage;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jon
 */
public class Server extends Observable implements Observer {

    ServerSocket ss;
    ArrayList<ChatPerson> people;

    public static void main(String args[]) throws IOException {
        System.out.println("main");
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        new Server();
    }

    public Server() throws IOException {
        System.out.println("server");
        System.out.println("Server starting");
        ss = new ServerSocket(3191);
        people = new ArrayList<>();
        System.out.println("Listening for clients");
        listenForClients();
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("update");
        if (o instanceof ClientConnection) {
            // Echo to all clients
            if (arg instanceof ChatMessage) {
                this.setChanged();
                this.notifyObservers(arg);
            } else if (arg instanceof ServerMessage) {
                parseRequest((ServerMessage) arg);
            } else if (arg instanceof ChatPerson) {
                people.add((ChatPerson)arg);
                this.setChanged();
                this.notifyObservers(arg);
            }
        }
    }

    private void listenForClients() {
        System.out.println("listen for clients");
        while (true) {
            try {
                Socket s = ss.accept();
                System.out.println("New Client" + s.getInetAddress());
                ClientConnection cc = new ClientConnection(s);
                cc.addObserver(this);
                this.addObserver(cc);
                people.add(cc.getName());
                Thread t = new Thread(cc);
                t.start();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void parseRequest(ServerMessage serverMessage) {
        System.out.println("parse request");
        if (serverMessage.getServerCode() == 1) {
        }
    }
}
