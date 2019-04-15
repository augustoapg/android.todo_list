/**
 * Project: My To Do
 * Author: Augusto A P Goncalez
 * Date: Apr. 17, 2019
 *
 * Description: This app allows a user to create new tasks with title, due date and description. It
 * also allows the user to edit any of the fields, as well as deleting a task.
 */

package sheridan.araujope.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sheridan.araujope.mytodo.database.TaskEntity;
import sheridan.araujope.mytodo.ui.AboutFragment;
import sheridan.araujope.mytodo.ui.TasksAdapter;
import sheridan.araujope.mytodo.utilities.Constants;
import sheridan.araujope.mytodo.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @OnClick(R.id.fab)
    void fabClickHandler() {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    private List<TaskEntity> tasksData = new ArrayList<>();
    private TasksAdapter mAdapter;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {
        final Observer<List<TaskEntity>> tasksObserver = new Observer<List<TaskEntity>>() {
            @Override
            public void onChanged(List<TaskEntity> taskEntities) {
                tasksData.clear();
                tasksData.addAll(taskEntities);

                if(mAdapter == null) {
                    mAdapter = new TasksAdapter(tasksData, MainActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.mTasks.observe(this, tasksObserver);
    }

    private void initRecyclerView() {
        // make each item on the list the same height
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_sample_data) {
            addSampleData();
            return true;
        } else if (id == R.id.action_delete_all) {
            deleteAllTasks();
            return true;
        } else if (id == R.id.about) {
            AboutFragment aboutFragment = AboutFragment.newInstance();
            aboutFragment.show(getSupportFragmentManager(), Constants.ABOUT_FRAGMENT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllTasks() {
        mViewModel.deleteAllTasks();
    }

    private void addSampleData() {
        mViewModel.addSampleData();
    }
}
