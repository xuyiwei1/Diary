package comp5216.sydney.edu.au.diarypro.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import comp5216.sydney.edu.au.diarypro.entity.WorkStudyEventItem;

@Dao
public interface RunWalkDao {

    @Query("select * from workStudyEventItem")
    List<WorkStudyEventItem> getAll();

    @Query("select * from workStudyEventItem where id = :id")
    WorkStudyEventItem getItemById(int id);

    @Insert
    void insertItem(WorkStudyEventItem workStudyEventItem);

    @Query("delete from workStudyEventItem")
    void deleteAll();

    @Update
    void update(WorkStudyEventItem... workStudyEventItem);

    @Query("delete from workStudyEventItem where id = :id")
    void deleteById(int id);

    @Query("select * from workStudyEventItem where dateDiary = :date")
    List<WorkStudyEventItem> getItemByDate(String date);

}
