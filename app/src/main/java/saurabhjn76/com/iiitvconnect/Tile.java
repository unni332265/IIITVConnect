package saurabhjn76.com.iiitvconnect;

/**
 * Created by saurabh on 15/11/16.
 */

/**
 * Created by saurabh on 18/10/16.
 */

public class Tile {

    private  String title;
    private  String description;
    private  int id;
    private String date;
    public Tile(String title,String description,int id,String date) {
        this.date = date;
        this.title=title;
        this.id=id;
        this.description=description;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
