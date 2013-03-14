/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system.gui;

import chat.system.objects.ChatMessage;
import chat.system.objects.ChatPerson;
import chat.system.objects.ServerMessage;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 *
 * @author jon
 */
public class ChatLog {

    OutputStream o;
    PrintWriter out;

    public ChatLog(OutputStream o) {
        this.o = o;
        this.out = new PrintWriter(o);
    }

    public void messageReceived(ChatMessage message) {
        out.println("MESSAGE " + message.getSender() + ": " + message.getMessage());
        out.flush();
    }

    public void personLoggedIn(ChatPerson person) {
        out.println("LOGIN " + person.getName());
        out.flush();
    }

    public void personLoggedOut(ChatPerson person) {
        out.println("LOGOUT " + person.getName());
        out.flush();
    }

    public void serverMessageReceived(ServerMessage message) {
        if (message.getServerCode() == 1) {
            out.println("SERVER Client Disconnected");
        } else if(message.getServerCode()==2) {
            out.println("SERVER Client list requested");
        } else {
            out.println("SERVER Unknown message: " + message.getServerCode() + " " + message.getMessage() + " " + message.getData());
        }
        out.flush();
    }

    public void otherMessage(String message) {
        out.println(message);
        out.flush();
    }
}
