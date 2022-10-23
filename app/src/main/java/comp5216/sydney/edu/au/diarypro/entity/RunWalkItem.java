package comp5216.sydney.edu.au.diarypro.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "runWalkItem")
public class RunWalkItem {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    //the content of work study and event
    @ColumnInfo(name = "runDistance")
    private int runDistance;

    @ColumnInfo(name = "runTime")
    private int runTime;

    @ColumnInfo(name = "runCalories")
    private String runCalories;

    //the type of diary item(work study or event)
    @ColumnInfo(name = "type")
    private String type;

    // the date of user save a diary
    @ColumnInfo(name = "dateDiary")
    private String dateDiary;

    // the path(drawable folder) of image that show in the home page
    @ColumnInfo(name = "imageInHomePage")
    private int imageInHomePage;

    public RunWalkItem() {
    }

    @Ignore
    public RunWalkItem(String runTime,String runDistance,String runCalories, String type, String dateDiary,int imageInHomePage) {
        this.runTime = runTime.equals("")?0:Integer.parseInt(runTime);
        this.runDistance = runDistance.equals("")?0:Integer.parseInt(runDistance);
        this.runCalories = runCalories;
        this.type = type;
        this.dateDiary = dateDiary;
        this.imageInHomePage = imageInHomePage;
    }

    @Ignore
    public RunWalkItem(int id,String runTime,String runDistance,String runCalories, String type,String dateDiary,int imageInHomePage) {
        this.id = id;
        this.runTime = runTime.equals("")?0:Integer.parseInt(runTime);
        this.runDistance = runDistance.equals("")?0:Integer.parseInt(runDistance);
        this.runCalories = runCalories;
        this.type = type;
        this.dateDiary = dateDiary;
        this.imageInHomePage = imageInHomePage;
    }

    @Ignore
    public RunWalkItem(int id, String runTime,String runDistance,String runCalories, String type) {
        this.id = id;
        this.runTime = runTime.equals("")?0:Integer.parseInt(runTime);
        this.runDistance = runDistance.equals("")?0:Integer.parseInt(runDistance);
        this.runCalories = runCalories;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRunCalories() {return runCalories;}

    public int getRunDistance() {return runDistance;}

    public int getRunTime() {return runTime;}

    public void setRunCalories(String runCalories) {this.runCalories = runCalories;}

    public void setRunDistance(int runDistance) {this.runDistance = runDistance;}

    public void setRunTime(int runTime) {this.runTime = runTime;}

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
}

