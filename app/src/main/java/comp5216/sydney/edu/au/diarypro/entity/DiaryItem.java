package comp5216.sydney.edu.au.diarypro.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "diaryItem")
public class DiaryItem {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="id")
    private int id;

    // the name of diary item
    @ColumnInfo(name="name")
    private String name;

    // the added date of this item
    @ColumnInfo(name="date")
    private String date;

    // the image show in the font of a diary item which is the same path(in drawable folder) as 'imageInHomePage' in WorkStudyEventItem class
    @ColumnInfo(name = "image")
    private int image;


    public DiaryItem() {
    }

    @Ignore
    public DiaryItem(String name, String date,int image) {
        this.name = name;
        this.date = date;
        this.image = image;
    }

    @Ignore
    public DiaryItem(int id, String name, String date,int image) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.image = image;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
