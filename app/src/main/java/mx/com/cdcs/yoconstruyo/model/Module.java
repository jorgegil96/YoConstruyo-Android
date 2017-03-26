package mx.com.cdcs.yoconstruyo.model;

public class Module {

    private int id;
    private int index;
    private String title;
    private String thumbnail;
    private boolean completed;
    private boolean visible;

    public Module(int id, int index, String title, String thumbnail, boolean completed, boolean visible) {
        this.id = id;
        this.index = index;
        this.title = title;
        this.thumbnail = thumbnail;
        this.completed = completed;
        this.visible = visible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
