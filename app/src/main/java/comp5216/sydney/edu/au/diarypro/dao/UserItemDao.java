package comp5216.sydney.edu.au.diarypro.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import comp5216.sydney.edu.au.diarypro.entity.UserItem;

@Dao
public interface UserItemDao {

    @Query("select * from userItem")
    List<UserItem> getAll();

    @Insert
    void insertItem(UserItem diaryItem);

    @Query("delete from UserItem")
    void deleteAll();

    @Query("delete from userItem where id = :id")
    void deleteById(int id);

    @Update
    void update(UserItem... userItem);
}
