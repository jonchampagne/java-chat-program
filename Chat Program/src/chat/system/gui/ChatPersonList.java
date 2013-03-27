/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system.gui;

import chat.system.objects.ChatPerson;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author jon
 */
public class ChatPersonList extends AbstractListModel {

    ArrayList<ChatPerson> people;
    
    public ChatPersonList()
    {
        people=new ArrayList<>();
    }
    
    @Override
    public int getSize() {
        return people.size();
    }

    @Override
    public Object getElementAt(int index) {
        return people.get(index).getName();
    }

    public void addPerson(ChatPerson chatPerson) {
        System.out.println("Added "+chatPerson.getName());
        people.add(chatPerson);
        this.fireContentsChanged(this, 1, people.size());
    }

    public void removePerson(ChatPerson chatPerson) {
        people.remove(chatPerson);
    }
    
}
