package goodcompanyname.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by jeremy on 8/11/16.
 */
public class ResultsActivity extends AppCompatActivity {

    private final static String TAG = "ResultsActivity";
    private final static String PREFERENCES_SELECTED_EXERCISES =
            "goodcompanyname.myapplication.workout_randomizer.selected_exercises";
    private final static String PREFERENCES_SELECTED_MUSCLE_GROUPS =
            "goodcompanyname.myapplication.workout_randomizer.selected_muscle_groups";

    Button buttonRestart;
    Button buttonClearData;

    SharedPreferences sharedPreferences;
    ArrayList<String> completedExercises;
    ArrayList<String> selectedMuscleGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Log.d(TAG, "ResultsActivity.onCreate() begin");
        readDb();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        selectedMuscleGroups = bundle.getStringArrayList("EXTRA_MUSCLE_GROUPS");
        completedExercises = bundle.getStringArrayList("EXTRA_COMPLETED_EXERCISES");

        buttonRestart = (Button) findViewById(R.id.restart_button);
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SelectionActivity.class);
                startActivity(intent);
            }
        });

        buttonClearData = (Button) findViewById(R.id.clear_data_button);
        buttonClearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearPreferences(PREFERENCES_SELECTED_MUSCLE_GROUPS);
                clearPreferences(PREFERENCES_SELECTED_EXERCISES);
                updateListView(PREFERENCES_SELECTED_MUSCLE_GROUPS, R.id.list_view_muscle_group_stats);
                updateListView(PREFERENCES_SELECTED_EXERCISES, R.id.list_view_exercise_stats);
            }
        });

        // Update completed muscle group and exercise counts in their shared preferences files.
        updatePreferences(PREFERENCES_SELECTED_MUSCLE_GROUPS, selectedMuscleGroups);
        updatePreferences(PREFERENCES_SELECTED_EXERCISES, completedExercises);

        // Update the list views in the window with the selections and their counts
        updateListView(PREFERENCES_SELECTED_MUSCLE_GROUPS, R.id.list_view_muscle_group_stats);
        updateListView(PREFERENCES_SELECTED_EXERCISES, R.id.list_view_exercise_stats);
    }

    /**
     * Clears a shared preference file of the specified tag.
     * @param sharedPreferencesTag the string representing the preferences file.
     */
    public void clearPreferences(String sharedPreferencesTag) {
        sharedPreferences = getSharedPreferences(sharedPreferencesTag, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void updatePreferences(String sharedPreferencesTag, ArrayList<?> selections) {
        sharedPreferences = getSharedPreferences(sharedPreferencesTag, Context.MODE_PRIVATE);
        if (!selections.isEmpty()) {
            int count;
            SharedPreferences.Editor editor = sharedPreferences.edit();

            for (Object key : selections) {
                count = sharedPreferences.getInt(key.toString(), 0);
                editor.putInt(key.toString(), count + 1);
            }

            editor.commit();
        }
    }

    /**
     * Update the list view to reflect exercises and their counts.
     */
    public void updateListView(String sharedPreferencesTag, int listViewResId) {
        LinkedHashMap<String, Integer> histogram;

        sharedPreferences = getSharedPreferences(sharedPreferencesTag, Context.MODE_PRIVATE);
        histogram = new LinkedHashMap(sharedPreferences.getAll());

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<Integer> counts = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : histogram.entrySet()) {
            keys.add(entry.getKey());
            counts.add(entry.getValue());
        }

        StringIntAdapter counterAdapter = new StringIntAdapter(this, keys, counts);

        ListView listViewStats = (ListView) findViewById(listViewResId);
        listViewStats.setAdapter(counterAdapter);
    }

    private void readDb() {
        Log.d(TAG, "readDB() begin");

        DbHelper mHelper = new DbHelper(this);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(WorkoutContract.TaskEntry.TABLE,
                new String[]{WorkoutContract.TaskEntry._ID, WorkoutContract.TaskEntry.COL_EXERCISES},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(WorkoutContract.TaskEntry.COL_EXERCISES);
//            int idy = cursor.getColumnIndex(WorkoutContract.TaskEntry.COL_TIME);
//            Log.d(TAG, cursor.getString(idx) + " " + cursor.getString(idy));
            Log.d(TAG, cursor.getString(idx));
        }

        cursor.close();
        db.close();
    }

    private class StringIntAdapter extends BaseAdapter {
        Activity activity;
        TextView textViewKey;
        TextView textViewCount;
        ArrayList<String> keys;
        ArrayList<Integer> counts;

        public StringIntAdapter(Activity activity, ArrayList<String> keys, ArrayList<Integer> counts) {
            super();
            this.activity = activity;
            this.keys = keys;
            this.counts = counts;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return keys.size();
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
                rowView = inflater.inflate(R.layout.histogram_row, null);

                textViewKey = (TextView) rowView.findViewById(R.id.list_view_key);
                textViewCount = (TextView) rowView.findViewById(R.id.list_view_count);
            }

            textViewKey.setText(keys.get(position));
            textViewCount.setText(counts.get(position).toString());

            return rowView;
        }
    }
}
