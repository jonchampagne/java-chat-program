/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system.objects;

/**
 *
 * @author jon
 */
public class ChatPerson {

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
}
