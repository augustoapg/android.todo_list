/**
 * Project: My To Do
 * Author: Augusto A P Goncalez
 * Date: Apr. 17, 2019
 *
 * Description: This app allows a user to create new tasks with title, due date and description. It
 * also allows the user to edit any of the fields, as well as deleting a task.
 */

package sheridan.araujope.mytodo.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import sheridan.araujope.mytodo.database.AppRepository;
import sheridan.araujope.mytodo.database.TaskEntity;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<TaskEntity>> mTasks;
    private AppRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mTasks = mRepository.mTasks;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllTasks() {
        mRepository.deleteAllTasks();
    }
}
