package comp5216.sydney.edu.au.diarypro.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
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

    public DiaryItem() {
    }

    public DiaryItem(String name, String date) {
        this.name = name;
        this.date = date;
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
