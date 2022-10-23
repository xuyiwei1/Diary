package comp5216.sydney.edu.au.diarypro.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "foodItem")
public class FoodItem {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    //the content of work study and event
    @ColumnInfo(name = "breakfastCal")
    private int breakfastCal;

    @ColumnInfo(name = "lunchCal")
    private int lunchCal;

    @ColumnInfo(name = "dinnerCal")
    private int dinnerCal;

    @ColumnInfo(name = "totalCal")
    private String totalCal;

    @ColumnInfo(name = "breakfastName")
    private String breakfastName;

    @ColumnInfo(name = "lunchName")
    private String lunchName;

    @ColumnInfo(name = "dinnerName")
    private String dinnerName;

    //the type of diary item(work study or event)
    @ColumnInfo(name = "type")
    private String type;

    // the date of user save a diary
    @ColumnInfo(name = "dateDiary")
    private String dateDiary;

    // the path(drawable folder) of image that show in the home page
    @ColumnInfo(name = "imageInHomePage")
    private int imageInHomePage;

    public FoodItem() {
    }

    @Ignore
    public FoodItem(String breakfastName,String lunchName,String dinnerName,String breakfastCal,String lunchCal,String dinnerCal,String totalCal,String type, String dateDiary,int imageInHomePage) {
        this.breakfastCal = breakfastCal.equals("")?0:Integer.parseInt(breakfastCal);
        this.lunchCal = lunchCal.equals("")?0:Integer.parseInt(lunchCal);
        this.dinnerCal = dinnerCal.equals("")?0:Integer.parseInt(dinnerCal);
        this.totalCal = totalCal;
        this.breakfastName = breakfastName;
        this.lunchName = lunchName;
        this.dinnerName = dinnerName;
        this.type = type;
        this.dateDiary = dateDiary;
        this.imageInHomePage = imageInHomePage;
    }

    @Ignore
    public FoodItem(int id,String breakfastName,String lunchName,String dinnerName,String breakfastCal,String lunchCal,String dinnerCal,String totalCal,String type,String dateDiary,int imageInHomePage) {
        this.id = id;
        this.breakfastCal = breakfastCal.equals("")?0:Integer.parseInt(breakfastCal);
        this.lunchCal = lunchCal.equals("")?0:Integer.parseInt(lunchCal);
        this.dinnerCal = dinnerCal.equals("")?0:Integer.parseInt(dinnerCal);
        this.totalCal = totalCal;
        this.breakfastName = breakfastName;
        this.lunchName = lunchName;
        this.dinnerName = dinnerName;
        this.type = type;
        this.dateDiary = dateDiary;
        this.imageInHomePage = imageInHomePage;
    }

    @Ignore
    public FoodItem(int id, String breakfastName,String lunchName,String dinnerName,String breakfastCal,String lunchCal,String dinnerCal,String totalCal,String type) {
        this.id = id;
        this.breakfastCal = breakfastCal.equals("")?0:Integer.parseInt(breakfastCal);
        this.lunchCal = lunchCal.equals("")?0:Integer.parseInt(lunchCal);
        this.dinnerCal = dinnerCal.equals("")?0:Integer.parseInt(dinnerCal);
        this.totalCal = totalCal;
        this.breakfastName = breakfastName;
        this.lunchName = lunchName;
        this.dinnerName = dinnerName;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateDiary() {
        return dateDiary;
    }

    public void setDateDiary(String dateDiary) {
        this.dateDiary = dateDiary;
    }

    public int getImageInHomePage() {
        return imageInHomePage;
    }

    public void setImageInHomePage(int imageInHomePage) {
        this.imageInHomePage = imageInHomePage;
    }

    public int getBreakfastCal() {
        return breakfastCal;
    }

    public int getDinnerCal() {
        return dinnerCal;
    }

    public int getLunchCal() {
        return lunchCal;
    }

    public String getBreakfastName() {
        return breakfastName;
    }

    public String getDinnerName() {
        return dinnerName;
    }

    public String getLunchName() {
        return lunchName;
    }

    public void setBreakfastCal(int breakfastCal) {
        this.breakfastCal = breakfastCal;
    }

    public void setBreakfastName(String breakfastName) {
        this.breakfastName = breakfastName;
    }

    public void setDinnerCal(int dinnerCal) {
        this.dinnerCal = dinnerCal;
    }

    public void setDinnerName(String dinnerName) {
        this.dinnerName = dinnerName;
    }

    public void setLunchCal(int lunchCal) {
        this.lunchCal = lunchCal;
    }
    public void setLunchName(String lunchName) {
        this.lunchName = lunchName;
    }

    public String getTotalCal() {
        return totalCal;
    }

    public void setTotalCal(String totalCal) {
        this.totalCal = totalCal;
    }
}

