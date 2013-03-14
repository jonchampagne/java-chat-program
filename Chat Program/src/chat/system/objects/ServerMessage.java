/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system.objects;

import java.io.Serializable;

/**
 * A message returned by the server. Usually a reply to a request.
 *
 * @author jon
 */
public class ServerMessage implements Serializable {

    /*
     * Server Codes:
     * 1 - Client Disconnected
     * 2 - Request Client List
     * 
     */
    
    private int serverCode;
    private String message;
    private Object data;

    public ServerMessage(int serverCode) {
        this.serverCode = serverCode;
    }

    public ServerMessage(int serverCode, String message) {
        this(serverCode);
        this.message = message;
    }

    public ServerMessage(int serverCode, Object data) {
        this(serverCode);
        this.data = data;
    }

    public ServerMessage(int serverCode, String message, Object data) {
        this(serverCode, data);
        this.message = message;
    }

    public int getServerCode() {
        return serverCode;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
