/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system.objects;

import java.io.Serializable;

/**
 *
 * @author jon
 */
public class ServerError implements Serializable {

    private int errorCode;
    private String message;

    public ServerError(int errorCode) {
        this.errorCode = errorCode;
    }

    public ServerError(int errorCode, String message) {
        this(errorCode);
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
