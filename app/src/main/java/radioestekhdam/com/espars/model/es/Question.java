package radioestekhdam.com.espars.model.es;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {

    @SerializedName("ID")
    private String iD;

    @SerializedName("position")
    private String position;

    @SerializedName("content")
    private String content;

    @SerializedName("items")
    private List<Item> items = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Question() {
    }

    /**
     *
     * @param content
     * @param position
     * @param items
     * @param iD
     */
    public Question(String iD, String position, String content, List<Item> items) {
        super();
        this.iD = iD;
        this.position = position;
        this.content = content;
        this.items = items;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
