package sheridan.araujope.mytodo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import sheridan.araujope.mytodo.R;
import sheridan.araujope.mytodo.model.TaskEntity;

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
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.task_text)
        TextView mTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
