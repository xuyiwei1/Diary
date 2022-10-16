package comp5216.sydney.edu.au.diarypro.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "workStudyEventItem")
public class WorkStudyEventItem {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    //the content of work study and event
    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "imagePath")
    private String imagePath;

    //the type of diary item(work study or event)
    @ColumnInfo(name = "type")
    private String type;

    // the date of user save a diary
    @ColumnInfo(name = "dateDiary")
    private String dateDiary;

    public WorkStudyEventItem() {
    }

    @Ignore
    public WorkStudyEventItem(String content, String imagePath, String type, String dateDiary) {
        this.content = content;
        this.imagePath = imagePath;
        this.type = type;
        this.dateDiary = dateDiary;
    }

    @Ignore
    public WorkStudyEventItem(int id,String content, String imagePath, String type,String dateDiary) {
        this.id = id;
        this.content = content;
        this.imagePath = imagePath;
        this.type = type;
        this.dateDiary = dateDiary;
    }

    @Ignore
    public WorkStudyEventItem(int id, String content, String imagePath, String type) {
        this.id = id;
        this.content = content;
        this.imagePath = imagePath;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
}
