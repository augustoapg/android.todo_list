package sheridan.araujope.mytodo;

import android.content.Context;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import sheridan.araujope.mytodo.database.AppDatabase;
import sheridan.araujope.mytodo.database.TaskDao;
import sheridan.araujope.mytodo.database.TaskEntity;
import sheridan.araujope.mytodo.utilities.SampleData;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    public static final String TAG = "Junit";
    private AppDatabase mDb;
    private TaskDao mDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        mDb = Room.inMemoryDatabaseBuilder(context,
                AppDatabase.class).build();
        mDao = mDb.taskDao();
        Log.i(TAG, "createDb");
    }

    @After
    public void closeDb() {
        mDb.close();
        Log.i(TAG, "closeDb");
    }

    @Test
    public void createAndRetrieveTasks() {
        mDao.insertAll(SampleData.getTasks());
        int count = mDao.getCount();
        Log.i(TAG, "createAndRetrieveTasks: count=" + count);
        assertEquals(SampleData.getTasks().size(), count);
    }

    @Test
    public void compareStrings() {
        mDao.insertAll(SampleData.getTasks());
        TaskEntity original = SampleData.getTasks().get(0);
        TaskEntity fromDb = mDao.getTaskById(1);
        assertEquals(original.getDescription(), fromDb.getDescription());
        assertEquals(1, fromDb.getId());
    }
}
