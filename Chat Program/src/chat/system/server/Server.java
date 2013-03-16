/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system.server;

import chat.system.objects.ChatLog;
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
 * Chat server
 *
 * @author jon
 */
public class Server extends Observable implements Observer {

    ServerSocket ss;
    ArrayList<ChatPerson> people;
    ChatLog log;

    /**
     * Starts the server
     *
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) {
        System.out.println("main");
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        new Server();
    }

    /**
     * A chat server
     */
    public Server() {
        //System.out.println("server");
        //System.out.println("Server starting");
        try {
            ss = new ServerSocket(3191);
        } catch (IOException ex) {
            System.err.println("Could not start server. Could not open port");
        }
        people = new ArrayList<>();
        log = new ChatLog(System.out);
        log.otherMessage("START Server Started");
        listenForClients();
    }

    @Override
    public void update(Observable o, Object arg) {
        //System.out.println("update");
        if (o instanceof ClientConnection) {
            // Echo to all clients
            if (arg instanceof ChatMessage) {
                this.setChanged();
                log.messageReceived((ChatMessage) arg);
                this.notifyObservers(arg);
            } else if (arg instanceof ServerMessage) {
                log.serverMessageReceived((ServerMessage) arg);
                parseRequest((ServerMessage) arg);
            } else if (arg instanceof ChatPerson) {
                people.add((ChatPerson) arg);
                log.personLoggedIn((ChatPerson) arg);
                this.setChanged();
                this.notifyObservers(arg);
            }
        }
    }

    private void listenForClients() {
        //System.out.println("listen for clients");
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
        if (serverMessage.getServerCode() == 1) {
            // Send it to all clients
            this.setChanged();
            this.notifyObservers(serverMessage);
        } else if (serverMessage.getServerCode() == 2) {
            returnPeople(serverMessage);
        }
    }

    private void returnPeople(ServerMessage serverMessage) {
        ChatPerson returnee = ((ChatPerson) serverMessage.getData());
        for (int i = 0; i < people.size(); i++) {
            this.setChanged();
            this.notifyObservers(new ServerMessage(3,returnee.getName(),people.get(i)));
        }
    }
}
