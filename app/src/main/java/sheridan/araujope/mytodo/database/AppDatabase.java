/**
 * Project: My To Do
 * Author: Augusto A P Goncalez
 * Date: Apr. 17, 2019
 *
 * Description: This app allows a user to create new tasks with title, due date and description. It
 * also allows the user to edit any of the fields, as well as deleting a task.
 */

package sheridan.araujope.mytodo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {TaskEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "AppDatabase.db";
    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();

    public abstract TaskDao taskDao();

    public static AppDatabase getInstance(Context context) {
        if(instance == null) {
            synchronized (LOCK) {
                if(instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
