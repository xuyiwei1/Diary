package comp5216.sydney.edu.au.diarypro.entity;

import java.util.Date;

public class DiaryItem {
    private int id;
    // the name of diary item
    private String name;
    // the added date of this item
    private String date;

    public DiaryItem() {
    }

    public DiaryItem(int id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
