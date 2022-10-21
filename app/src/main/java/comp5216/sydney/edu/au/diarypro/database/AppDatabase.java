package comp5216.sydney.edu.au.diarypro.database;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import comp5216.sydney.edu.au.diarypro.dao.DiaryItemDao;
import comp5216.sydney.edu.au.diarypro.dao.UserItemDao;
import comp5216.sydney.edu.au.diarypro.dao.WorkStudyEventDao;
import comp5216.sydney.edu.au.diarypro.entity.DiaryItem;
import comp5216.sydney.edu.au.diarypro.entity.WorkStudyEventItem;
import comp5216.sydney.edu.au.diarypro.entity.UserItem;

// declare the entities to the database
@Database(entities = {DiaryItem.class, WorkStudyEventItem.class, UserItem.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "diaryProDB";
    private static AppDatabase DBINSTANCE;

    // add the dao to the database
    public abstract DiaryItemDao diaryItemDao();

    public abstract WorkStudyEventDao workStudyEventItemDao();

    public abstract UserItemDao userItemDao();

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
