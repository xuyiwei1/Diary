package comp5216.sydney.edu.au.diarypro.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import comp5216.sydney.edu.au.diarypro.entity.UserItem;

@Dao
public interface UserItemDao {

    @Query("select * from userItem where id = :id")
    UserItem getById(int id);

    @Query("select id from userItem where username = :username")
    int getId(String username);

    @Insert
    void insertItem(UserItem UserItem);

    @Query("delete from UserItem")
    void deleteAll();

    @Query("delete from userItem where id = :id")
    void deleteById(int id);

    @Update
    void update(UserItem... userItem);

    @Query("select count(*) from UserItem where username = :username")
    int checkUserExist(String username);

    @Query("select password from UserItem where username = :username")
    String getPassword(String username);

    @Query("select password from UserItem where id = :id")
    String getPasswordById(int id);

    @Query("update  UserItem set nickname = :nickname where id = :id")
    void changeNickname(int id,String nickname);

    @Query("update  UserItem set imagePath = :imagePath where id = :id")
    void changeImagePath(int id,String imagePath);

    @Query("update  UserItem set password = :password where id = :id")
    void changePassword(int id,String password);
}
