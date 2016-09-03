package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import goodcompanyname.workout_randomizer.R;
import sqlite.LogsContract;

/**
 * Created by jeremy on 8/21/16.
 */
public class LogsRecyclerAdapter extends RecyclerView.Adapter<LogsRecyclerAdapter.ViewHolder> {
    private ArrayList<HashMap<String, String>> exerciseLog;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout logRow;
        HashMap<String, String> exercise;
        TextView date, name, group, status;


        public ViewHolder(LinearLayout logRow) {
            super(logRow);
            this.logRow = logRow;
        }

        public void setItem(HashMap<String, String> exercise) {
            this.exercise = exercise;

            date = (TextView) logRow.findViewById(R.id.log_row_date);
            name = (TextView) logRow.findViewById(R.id.log_row_exercise);
            group = (TextView) logRow.findViewById(R.id.log_row_muscle_group);
            status = (TextView) logRow.findViewById(R.id.log_row_status);

            date.setText(exercise.get(LogsContract.LogEntry.COLUMN_DATE));
            name.setText(exercise.get(LogsContract.LogEntry.COLUMN_EXERCISE));
            group.setText(exercise.get(LogsContract.LogEntry.COLUMN_GROUP));
            status.setText(exercise.get(LogsContract.LogEntry.COLUMN_STATUS));
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public LogsRecyclerAdapter(ArrayList<HashMap<String, String>> exerciseLog) {
        this.exerciseLog = exerciseLog;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LogsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout logsRow = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.logs_row, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(logsRow);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(exerciseLog.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return exerciseLog.size();
    }
}
