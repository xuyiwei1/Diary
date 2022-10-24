package comp5216.sydney.edu.au.diarypro.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import comp5216.sydney.edu.au.diarypro.dao.DiaryItemDao;
import comp5216.sydney.edu.au.diarypro.dao.FoodDao;
import comp5216.sydney.edu.au.diarypro.dao.RunWalkDao;
import comp5216.sydney.edu.au.diarypro.dao.UserItemDao;
import comp5216.sydney.edu.au.diarypro.dao.WorkStudyEventDao;
import comp5216.sydney.edu.au.diarypro.entity.DiaryItem;
import comp5216.sydney.edu.au.diarypro.entity.FoodItem;
import comp5216.sydney.edu.au.diarypro.entity.RunWalkItem;
import comp5216.sydney.edu.au.diarypro.entity.WorkStudyEventItem;
import comp5216.sydney.edu.au.diarypro.entity.UserItem;

// declare the entities to the database


@Database(entities = {DiaryItem.class, WorkStudyEventItem.class, RunWalkItem.class, UserItem.class, FoodItem.class}, version = 7, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "diaryProDB";
    private static AppDatabase DBINSTANCE;

    // add the dao to the database
    public abstract DiaryItemDao diaryItemDao();

    public abstract WorkStudyEventDao workStudyEventItemDao();

    public abstract RunWalkDao runWalkItemDao();

    public abstract UserItemDao userItemDao();

    public abstract FoodDao foodItemDao();

    // using the singleton design pattern, to make sure the database only has one
    public static AppDatabase getDatabase(Context context) {
        if (DBINSTANCE == null) {
            synchronized (AppDatabase.class) {
                // allow the main thread visit the database
                DBINSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                        // lose all data use this function to update database
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries().build();
            }
        }

        return DBINSTANCE;
    }

    public static void destroyInstance() {
        DBINSTANCE = null;
    }

    /**
     * if you do not want to lose data when your update the structure or create new table, change this method to update database
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("create table workStudyEventItem(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, content varchar(32),imagePath TEXT, type TEXT)");
        }
    };

}
