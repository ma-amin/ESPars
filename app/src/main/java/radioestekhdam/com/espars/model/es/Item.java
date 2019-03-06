package radioestekhdam.com.espars.model.es;

import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("ID")
    private String iD;

    @SerializedName("position")
    private String position;

    @SerializedName("content")
    private String content;

    /**
     * No args constructor for use in serialization
     *
     */
    public Item() {
    }

    /**
     *
     * @param content
     * @param position
     * @param iD
     */
    public Item(String iD, String position, String content) {
        super();
        this.iD = iD;
        this.position = position;
        this.content = content;
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
}
