/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system.objects;

import java.io.Serializable;

/**
 * A person logged onto the server
 *
 * @author jon
 */
public class ChatPerson implements Serializable {

    private String name;
    private String status;

    public ChatPerson(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public ChatPerson(String name) {
        this(name, "online");
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
