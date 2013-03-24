/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system.gui;

import chat.system.objects.ChatPerson;
import java.util.ArrayList;
import javax.swing.JList;

/**
 *
 * @author jon
 */
public class PeopleList {

    JList list;
    ArrayList<ChatPerson> contents;

    public PeopleList(JList list) {
        this.list = list;
        contents = new ArrayList<ChatPerson>();
    }

    public Object get(int index) {
        return contents.get(index);
    }

    public void add(ChatPerson o) {
        contents.add(o);
        list.setListData(contents.toArray());
    }

    public Object[] toArray() {
        return contents.toArray();
    }
}
