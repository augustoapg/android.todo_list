/**
 * Project: My To Do
 * Author: Augusto A P Goncalez
 * Date: Apr. 17, 2019
 *
 * Description: This app allows a user to create new tasks with title, due date and description. It
 * also allows the user to edit any of the fields, as well as deleting a task.
 */

package sheridan.araujope.mytodo.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import sheridan.araujope.mytodo.EditorActivity;
import sheridan.araujope.mytodo.R;
import sheridan.araujope.mytodo.database.TaskEntity;

import static sheridan.araujope.mytodo.utilities.Constants.TASK_ID_KEY;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private final List<TaskEntity> mTasks;
    private final Context mContext;

    public TasksAdapter(List<TaskEntity> mTasks, Context mContext) {
        this.mTasks = mTasks;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.todo_list_item, parent, false);
        return new ViewHolder(view);
    }

    // called each time a row is refresh with the data object
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TaskEntity task = mTasks.get(position);
        holder.mTextView.setText(task.getTitle());

        holder.mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditorActivity.class);
                intent.putExtra(TASK_ID_KEY, task.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.task_text)
        TextView mTextView;

        @BindView(R.id.fab)
        FloatingActionButton mFab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
