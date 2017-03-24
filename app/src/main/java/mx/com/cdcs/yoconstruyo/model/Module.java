package mx.com.cdcs.yoconstruyo.model;

public class Module {

    private int id;
    private String title;
    private String image;
    private boolean complete;

    public Module(int id, String title, String image, boolean complete) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.complete = complete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
