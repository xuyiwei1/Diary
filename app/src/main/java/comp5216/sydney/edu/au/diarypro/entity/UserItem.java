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

    // the username of user item
    @ColumnInfo(name="username")
    private String username;

    // the password of user item
    @ColumnInfo(name="password")
    private String password;

    @ColumnInfo(name="nickname")
    private String nickname;

    @ColumnInfo(name="imagePath")
    private String imagePath;

    @ColumnInfo(name="weight")
    private int weight;


    public UserItem() {
    }

    @Ignore
    public UserItem(String username, String password) {
        this.username = username;
        this.password = password;
        this.nickname = "default";
        this.imagePath = null;
        this.weight = 60;
    }

    public int getId() {
        return id;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {return nickname;}

    public void setNickname(String nickname) {this.nickname = nickname;}

    public String getImagePath() {return imagePath;}

    public void setImagePath(String imagePath) {this.imagePath = imagePath;}

    public int getWeight() {return weight;}

    public void setWeight(int weight) {this.weight = weight;}
}
