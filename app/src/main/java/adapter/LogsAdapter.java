package adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import goodcompanyname.myapplication.R;

/**
 * Created by jeremy on 8/21/16.
 */
public class LogsAdapter extends BaseAdapter {
    Activity activity;
    TextView textViewExercise;
    TextView textViewMuscleGroup;
    TextView textViewDate;
    TextView textViewStatus;
    ArrayList<FourTuple<String, String, String, String>> logEntries;

    public LogsAdapter(Activity activity, ArrayList<FourTuple<String, String, String, String>> logEntries) {
        super();
        this.activity = activity;
        this.logEntries = logEntries;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return logEntries.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = activity.getLayoutInflater();

        if (rowView == null) {
            rowView = inflater.inflate(R.layout.logs_row, null);

            textViewExercise = (TextView) rowView.findViewById(R.id.log_row_exercise);
            textViewMuscleGroup = (TextView) rowView.findViewById(R.id.log_row_muscle_group);
            textViewDate = (TextView) rowView.findViewById(R.id.log_row_date);
            textViewStatus = (TextView) rowView.findViewById(R.id.log_row_status);
        }

        textViewExercise.setText(logEntries.get(position).a);
        textViewMuscleGroup.setText(logEntries.get(position).b);
        textViewDate.setText(logEntries.get(position).c);
        textViewStatus.setText(logEntries.get(position).d);

        return rowView;
    }
}
