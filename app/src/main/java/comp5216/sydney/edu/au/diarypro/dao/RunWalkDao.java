package comp5216.sydney.edu.au.diarypro.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import comp5216.sydney.edu.au.diarypro.entity.RunWalkItem;

@Dao
public interface RunWalkDao {

    @Query("select * from runWalkItem")
    List<RunWalkItem> getAll();

    @Query("select * from runWalkItem where id = :id")
    RunWalkItem getItemById(int id);

    @Insert
    void insertItem(RunWalkItem runWalkItem);

    @Query("delete from runWalkItem")
    void deleteAll();

    @Update
    void update(RunWalkItem... runWalkItem);

    @Query("delete from runWalkItem where id = :id")
    void deleteById(int id);

    @Query("select * from runWalkItem where dateDiary = :date")
    List<RunWalkItem> getItemByDate(String date);

}
