package comp5216.sydney.edu.au.diarypro.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import comp5216.sydney.edu.au.diarypro.entity.FoodItem;
import comp5216.sydney.edu.au.diarypro.entity.RunWalkItem;

@Dao
public interface FoodDao {

    @Query("select * from foodItem")
    List<FoodItem> getAll();

    @Query("select * from foodItem where id = :id")
    FoodItem getItemById(int id);

    @Insert
    void insertItem(FoodItem foodItem);

    @Query("delete from foodItem")
    void deleteAll();

    @Update
    void update(FoodItem... foodItem);

    @Query("delete from foodItem where id = :id")
    void deleteById(int id);

    @Query("select * from foodItem where dateDiary = :date")
    List<FoodItem> getItemByDate(String date);

}
