package radioestekhdam.com.espars.model.chat;

import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import radioestekhdam.com.espars.model.es.Item;

public class Message implements IMessage {

    private String id;
    private String text;
    private List<Item> items = null;
    private Date createdAt;
    private User user;
    private int command;

    public Message(String id, User user, String text) {
        this(id, user, text, new ArrayList<Item>());
    }

    public Message(String id, User user, String text, Date createdAt) {
        this.id = id;
        this.text = text;
        this.items = new ArrayList<>();
        this.user = user;
        this.createdAt = createdAt;
    }

    public Message(String id, User user, String text, List<Item> items) {
        this.id = id;
        this.text = text;
        this.items = items;
        this.user = user;
        this.createdAt = new Date();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getCommand() {
        return command;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setCommand(int command) {
        this.command = command;
    }
}
