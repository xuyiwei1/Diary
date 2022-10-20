package comp5216.sydney.edu.au.diarypro.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;



@Entity(tableName = "userItem")
public class UserItem {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="id")
    private int id;

    // the name of diary item
    @ColumnInfo(name="username")
    private String username;

    // the added date of this item
    @ColumnInfo(name="password")
    private String password;

    // the image show in the font of a diary item which is the same path(in drawable folder) as 'imageInHomePage' in WorkStudyEventItem class
    @ColumnInfo(name = "image")
    private int image;


    public UserItem() {
    }

    @Ignore
    public UserItem(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
