package sheridan.araujope.mytodo.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import sheridan.araujope.mytodo.database.TaskEntity;
import sheridan.araujope.mytodo.utilities.SampleData;

public class MainViewModel extends AndroidViewModel {

    public List<TaskEntity> mTasks = SampleData.getTasks();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }
}
