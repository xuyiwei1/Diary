package comp5216.sydney.edu.au.diarypro.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import comp5216.sydney.edu.au.diarypro.entity.UserItem;
import comp5216.sydney.edu.au.diarypro.entity.WorkStudyEventItem;

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
}
