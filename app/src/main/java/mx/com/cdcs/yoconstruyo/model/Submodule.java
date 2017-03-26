package mx.com.cdcs.yoconstruyo.model;

public class Submodule {

    private int id;
    private int index;
    private String title;
    private String thumbnail;
    private boolean completed;

    public Submodule(int id, int index, String title, String thumbnail, boolean completed) {
        this.id = id;
        this.index = index;
        this.title = title;
        this.thumbnail = thumbnail;
        this.completed = completed;
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
}
