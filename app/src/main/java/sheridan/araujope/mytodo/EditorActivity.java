package sheridan.araujope.mytodo;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import sheridan.araujope.mytodo.database.TaskEntity;
import sheridan.araujope.mytodo.ui.DatePickerFragment;
import sheridan.araujope.mytodo.viewmodel.EditorViewModel;

import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static sheridan.araujope.mytodo.utilities.Constants.DATE_PICKER_FRAGMENT;
import static sheridan.araujope.mytodo.utilities.Constants.TASK_ID_KEY;

public class EditorActivity extends AppCompatActivity implements DatePickerFragment.DateSetListener{

    @BindView(R.id.task_text)
    TextView mTextView;

    @BindView(R.id.task_title)
    TextView mTaskTitle;

    @BindView(R.id.task_due_date)
    TextView mTaskDueDate;

    Date mDate = new Date();

    @BindView(R.id.pick_date_button)
    ImageButton mEditDateButton;

    private EditorViewModel mViewModel;
    private boolean mNewTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        initViewModel();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);

        mViewModel.mLiveTask.observe(this, new Observer<TaskEntity>() {
            @Override
            public void onChanged(TaskEntity taskEntity) {
                if(taskEntity != null) {
                    mTaskTitle.setText(taskEntity.getTitle());
                    mTextView.setText(taskEntity.getDescription());
                    if(taskEntity.getDueDate() != null) {
                        mTaskDueDate.setText(DateFormat.getLongDateFormat(EditorActivity.this)
                                .format(taskEntity.getDueDate()));
                        mDate = taskEntity.getDueDate();
                    }
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            mDate = new Date();
            mNewTask = true;
            setTitle(R.string.new_task);
        } else {
            setTitle(R.string.edit_task);
            int taskId = extras.getInt(TASK_ID_KEY);
            mViewModel.loadData(taskId);
        }

        mEditDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment = DatePickerFragment.getInstance(mDate);
                fragment.show(getSupportFragmentManager(), DATE_PICKER_FRAGMENT);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!mNewTask) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_editor, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            mViewModel.deleteTask();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        mViewModel.saveTask(mTaskTitle.getText().toString(),
                mTaskDueDate.getText().toString(),
                mTextView.getText().toString());
        finish();
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        calendar.set(year, month, day, hour, minute);
        mDate = calendar.getTime();
        mTaskDueDate.setText(DateFormat.getLongDateFormat(this).format(mDate));
    }
}
