/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system.server;

import chat.system.objects.ChatLog;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 *
 * @author jon
 */
public class Server {

    private ArrayList<ChatRoom> rooms;
    private ServerSocket ss;
    private ChatLog log;
    private int defaultRoom;

    public static void main(String args[]) {
        new Server();
    }

    public Server() {
        // If we can't get the connection, go kill ourselves now.
        try {
            ss = new ServerSocket(3191);
        } catch (IOException ex) {
            System.err.println("Could not start server. Could not open port");
            System.exit(1);
        }
        log=new ChatLog(System.out);
        rooms = new ArrayList<>();
        startNewRoom("default");
        for (int i = 0; i < rooms.size(); i++) {
            if(rooms.get(i).getName().equalsIgnoreCase("default")){
                defaultRoom=i;
                break;
            }
        }
    }

    private void startNewRoom(String roomName) {
        ChatRoom theRoom = new ChatRoom(roomName, log);
        rooms.add(theRoom);
    }

    private void listenForClients() {
        while (true) {
        }
    }
}