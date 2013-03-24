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
public class ChatRoom extends Observable implements Observer {

    private ServerSocket ss;
    private ArrayList<ChatPerson> people;
    private ChatLog log;
    private String name;

    /**
     * A chat server
     */
    public ChatRoom(String name, ChatLog log) {
        //System.out.println("server");
        //System.out.println("Server starting");
        this.name = name;
        try {
            ss = new ServerSocket(3191);
        } catch (IOException ex) {
            System.err.println("Could not start server. Could not open port");
        }
        people = new ArrayList<>();
        this.log = log;
        log.otherMessage("CHATROOM " + name + " Started");
        listenForClients();
    }

    @Override
    public void update(Observable o, Object arg) {
        //System.out.println("update");
        if (o instanceof ClientConnection) {
            // Echo to all clients
            if (arg instanceof ChatMessage) {
                this.setChanged();
                getLog().messageReceived((ChatMessage) arg);
                this.notifyObservers(arg);
            } else if (arg instanceof ServerMessage) {
                getLog().serverMessageReceived((ServerMessage) arg);
                parseRequest((ServerMessage) arg);
            } else if (arg instanceof ChatPerson) {
                getPeople().add((ChatPerson) arg);
                getLog().personLoggedIn((ChatPerson) arg);
                this.setChanged();
                this.notifyObservers(arg);
            }
        }
    }

    private void listenForClients(){}

    private void parseRequest(ServerMessage serverMessage) {
        if (serverMessage.getServerCode() == 1) {
            // Send it to all clients
            this.setChanged();
            this.notifyObservers(serverMessage);
        } else if (serverMessage.getServerCode() == 2) {
            returnPeople(serverMessage);
        } else if (serverMessage.getServerCode() == 4) {
            this.setChanged();
            this.notifyObservers(serverMessage);
        }
    }

    private void returnPeople(ServerMessage serverMessage) {
        ChatPerson returnee = ((ChatPerson) serverMessage.getData());
        for (int i = 0; i < getPeople().size(); i++) {
            this.setChanged();
            this.notifyObservers(new ServerMessage(3, returnee.getName(), getPeople().get(i)));
        }
    }

    public ArrayList<ChatPerson> getPeople() {
        return people;
    }

    public ChatLog getLog() {
        return log;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
