package comp5216.sydney.edu.au.diarypro.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import comp5216.sydney.edu.au.diarypro.dao.DiaryItemDao;
import comp5216.sydney.edu.au.diarypro.entity.DiaryItem;

// declare the entities to the database
@Database(entities = {DiaryItem.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "diaryProDB";
    private static AppDatabase DBINSTANCE;
    // add the dao to the database
    public abstract DiaryItemDao diaryItemDao();

    // using the singleton design pattern, to make sure the database only has one
    public static AppDatabase getDatabase(Context context) {
        if(DBINSTANCE == null) {
            synchronized (AppDatabase.class) {
                // allow the main thread visit the database
                DBINSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DATABASE_NAME).allowMainThreadQueries().build();
            }
        }

        return DBINSTANCE;
    }

    public static void destroyInstance() {
        DBINSTANCE = null;
    }
}
