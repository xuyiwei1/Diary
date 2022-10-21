package comp5216.sydney.edu.au.diarypro.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import comp5216.sydney.edu.au.diarypro.entity.DiaryItem;

@Dao
public interface DiaryItemDao {

    @Query("select * from diaryItem")
    List<DiaryItem> getAll();

    @Insert
    void insertItem(DiaryItem diaryItem);

    @Query("delete from diaryItem")
    void deleteAll();

    @Query("delete from diaryItem where id = :id")
    void deleteById(int id);


}
