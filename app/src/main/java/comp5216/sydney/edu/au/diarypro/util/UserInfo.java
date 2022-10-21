package comp5216.sydney.edu.au.diarypro.util;

import android.app.Application;

import comp5216.sydney.edu.au.diarypro.entity.UserItem;

public class UserInfo extends Application {
    private int id;
    private UserItem userItem;

    public UserInfo(){

    }

    public UserInfo(int id,UserItem userItem){
        this.id = id;
        this.userItem = userItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserItem getUserItem() {
        return userItem;
    }

    public void setUserItem(UserItem userItem) {
        this.userItem = userItem;
    }
}
