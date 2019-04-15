/**
 * Project: My To Do
 * Author: Augusto A P Goncalez
 * Date: Apr. 17, 2019
 *
 * Description: This app allows a user to create new tasks with title, due date and description. It
 * also allows the user to edit any of the fields, as well as deleting a task.
 */

package sheridan.araujope.mytodo;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import sheridan.araujope.mytodo.database.TaskEntity;
import sheridan.araujope.mytodo.ui.DatePickerFragment;
import sheridan.araujope.mytodo.ui.DeleteConfirmationFragment;
import sheridan.araujope.mytodo.viewmodel.EditorViewModel;

import static sheridan.araujope.mytodo.utilities.Constants.DATE_KEY;
import static sheridan.araujope.mytodo.utilities.Constants.DATE_PICKER_FRAGMENT;
import static sheridan.araujope.mytodo.utilities.Constants.DELETE_CONFIRM_FRAGMENT;
import static sheridan.araujope.mytodo.utilities.Constants.EDITING_KEY;
import static sheridan.araujope.mytodo.utilities.Constants.TASK_ID_KEY;

public class EditorActivity extends AppCompatActivity
        implements DatePickerFragment.DateSetListener,DeleteConfirmationFragment.ConfirmListener{

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
    private boolean mNewTask, mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if(savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
            mDate = (Date) savedInstanceState.getSerializable(DATE_KEY);
            mTaskDueDate.setText(DateFormat.getLongDateFormat(this).format(mDate));
        }

        initViewModel();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);

        mViewModel.mLiveTask.observe(this, new Observer<TaskEntity>() {
            @Override
            public void onChanged(TaskEntity taskEntity) {
                if(taskEntity != null && !mEditing) {
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
            mNewTask = true;
            setTitle(R.string.new_task);
        } else {
            setTitle(R.string.edit_task);
            mNewTask = false;
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
            DeleteConfirmationFragment confirmFragment
                    = DeleteConfirmationFragment.newInstance(0, getString(R.string.delete_confirm_message));
            confirmFragment.show(getSupportFragmentManager(), DELETE_CONFIRM_FRAGMENT);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfirmed(int dialogID) {
        mViewModel.deleteTask();
        finish();
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        outState.putSerializable(DATE_KEY, mDate);
        super.onSaveInstanceState(outState);
    }
}
