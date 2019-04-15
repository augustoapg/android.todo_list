package sheridan.araujope.mytodo.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import sheridan.araujope.mytodo.database.AppRepository;
import sheridan.araujope.mytodo.database.TaskEntity;

public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<TaskEntity> mLiveTask = new MutableLiveData<>();
    public AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(int taskId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                TaskEntity task = mRepository.getTaskById(taskId);
                mLiveTask.postValue(task);
            }
        });
    }

    public void saveTask(String taskTitle, String taskDueDate, String taskText) {
        TaskEntity task = mLiveTask.getValue();
        Date dueDate = null;

        if (task == null) {
            if(TextUtils.isEmpty(taskTitle.trim()) &&
                    TextUtils.isEmpty(taskDueDate.trim()) &&
                    TextUtils.isEmpty(taskText.trim())) {
                return;
            }
            if(TextUtils.isEmpty(taskDueDate.trim())) {
                try {
                    dueDate = new SimpleDateFormat("MMMM dd, yyyy").parse(taskDueDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            task = new TaskEntity(taskTitle, taskText, dueDate);

        } else {
            try {
                dueDate = new SimpleDateFormat("MMMM dd, yyyy").parse(taskDueDate);
                task.setTitle(taskTitle.trim());
                task.setDueDate(dueDate);
                task.setDescription(taskText.trim());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        mRepository.insertTask(task);
    }

    public void deleteTask() {
        mRepository.deleteTask(mLiveTask.getValue());
    }
}
