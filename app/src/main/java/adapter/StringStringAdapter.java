package adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import goodcompanyname.myapplication.R;

/**
 * Created by jeremy on 8/18/16.
 */
public class StringStringAdapter extends BaseAdapter {
    private ArrayList<String> muscleGroups;
    private ArrayList<String> exercises;
    Activity activity;
    TextView textViewExercise;
    TextView textViewMuscleGroup;

    public StringStringAdapter(Activity activity, ArrayList<String> muscleGroups,
                               ArrayList<String> exercises) {
        super();
        this.activity = activity;
        this.muscleGroups = muscleGroups;
        this.exercises = exercises;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return muscleGroups.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return muscleGroups.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View cardView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = activity.getLayoutInflater();

        if (cardView == null) {
            cardView = inflater.inflate(R.layout.card_view, null);

            textViewExercise = (TextView) cardView.findViewById(R.id.list_view_exercise);
            textViewMuscleGroup = (TextView) cardView.findViewById(R.id.list_view_muscle_group);
        }

        textViewExercise.setText(exercises.get(position));
        textViewMuscleGroup.setText(muscleGroups.get(position));

        return cardView;
    }
}
