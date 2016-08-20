package goodcompanyname.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import adapter.StringIntAdapter;
import adapter.TwoTuple;
import sqlite.WorkoutContract;
import sqlite.WorkoutDbHelper;

/**
 * Created by jeremy on 8/11/16.
 */
public class ResultsActivity extends AppCompatActivity {
    // todo: incorporate graphing api
    // todo: change "finish" button to circle with + symbol

    private final static String TAG = "ResultsActivity";
    private final static String PREFERENCES_SELECTED_EXERCISES =
            "goodcompanyname.myapplication.workout_randomizer.selected_exercises";
    private final static String PREFERENCES_SELECTED_MUSCLE_GROUPS =
            "goodcompanyname.myapplication.workout_randomizer.selected_muscle_groups";

    Button buttonClearData;
    FloatingActionButton fab;

    SharedPreferences sharedPreferences;
    ArrayList<String> selectedMuscleGroups;
    ArrayList<String> completedExercises;
    ArrayList<String> skippedExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        readTable();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        selectedMuscleGroups = bundle.getStringArrayList("EXTRA_MUSCLE_GROUPS");
        completedExercises = bundle.getStringArrayList("EXTRA_COMPLETED_EXERCISES");
        skippedExercises = bundle.getStringArrayList("EXTRA_SKIPPED_EXERCISES");

        fab = (FloatingActionButton) findViewById(R.id.fab_results_restart);
        fab.setOnClickListener(new View.OnClickListener() {
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
                clearTable();
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
        editor.apply();
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

            editor.apply();
        }
    }

    /**
     * Update the list view to reflect exercises and their counts.
     */
    public void updateListView(String sharedPreferencesTag, int listViewResId) {
        LinkedHashMap<String, Integer> databaseEntries;

        sharedPreferences = getSharedPreferences(sharedPreferencesTag, Context.MODE_PRIVATE);
        databaseEntries = new LinkedHashMap(sharedPreferences.getAll());

        ArrayList<TwoTuple<String, Integer>> histogram = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : databaseEntries.entrySet()) {
            histogram.add(new TwoTuple(entry.getKey(), entry.getValue()));
        }

        StringIntAdapter counterAdapter = new StringIntAdapter(this, histogram);

        ListView listViewStats = (ListView) findViewById(listViewResId);
        listViewStats.setAdapter(counterAdapter);
    }

    public void readTable() {
        WorkoutDbHelper mDbHelper = new WorkoutDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                WorkoutContract.WorkoutEntry._ID,
                WorkoutContract.WorkoutEntry.COLUMN_WORKOUT,
                WorkoutContract.WorkoutEntry.COLUMN_DATE
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                WorkoutContract.WorkoutEntry.COLUMN_DATE + " DESC";

        Cursor c = db.query(
                WorkoutContract.WorkoutEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String workout = c.getString(c.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT));
                String date = c.getString(c.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_DATE));

                Log.d(TAG, date + " " + workout);

                c.moveToNext();
            }
        }

        c.close();
    }

    private void clearTable() {
        WorkoutDbHelper mDbHelper = new WorkoutDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.delete(WorkoutContract.WorkoutEntry.TABLE_NAME, null, null);
    }
}
