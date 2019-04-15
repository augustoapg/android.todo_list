/**
 * Project: My To Do
 * Author: Augusto A P Goncalez
 * Date: Apr. 17, 2019
 *
 * Description: This app allows a user to create new tasks with title, due date and description. It
 * also allows the user to edit any of the fields, as well as deleting a task.
 */

package sheridan.araujope.mytodo.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(TaskEntity taskEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TaskEntity> tasks);

    @Delete
    void deleteTask(TaskEntity taskEntity);

    @Query("SELECT * FROM tasks WHERE id = :id")
    TaskEntity getTaskById(int id);

    @Query("SELECT * FROM tasks ORDER BY dueDate DESC")
    LiveData<List<TaskEntity>> getAll();

    @Query("DELETE FROM tasks")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM tasks")
    int getCount();
}
