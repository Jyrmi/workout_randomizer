package goodcompanyname.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import adapter.StringIntAdapter;
import adapter.TwoTuple;
import constant.MuscleGroup;
import formatter.RadarChartFormatter;
import sqlite.ExerciseContract;
import sqlite.ExerciseDbHelper;

/**
 * Created by jeremy on 8/11/16.
 */
public class ResultsFragment extends Fragment {
    // todo: more graphs of relevant information
    // todo: why write preferences here? They can be written where they are selected in SelectionFragment
    // todo: and accessed here. Don't pass it between fragments needlessly. Same goes for other data.
    // todo: same goes for sqlite.

    private final static String TAG = "ResultsFragment";
    public static final String ARG_PAGE = "ARG_PAGE";
    private final static String PREFERENCES_SELECTED_MUSCLE_GROUPS =
            "goodcompanyname.myapplication.workout_randomizer.selected_muscle_groups";

    FloatingActionButton fab;

    SharedPreferences sharedPreferences;
    ArrayList<String> selectedMuscleGroups;
    ArrayList<String> completedExercises;
    ArrayList<String> skippedExercises;
    DialogInterface.OnClickListener dialogClickListener;

    RadarChart muscleGroupsChart;
    RadarDataSet radarDataSet;
    RadarData radarData;

    public static ResultsFragment newInstance(int pageNo) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        ResultsFragment fragment = new ResultsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Responds to SelectionFragment's button for generating exercises for selected muscle groups.
     * @param selectedMuscleGroups list of muscle groups selected within SelectionFragment.
     */
    protected void addMuscleGroupSelections(ArrayList<MuscleGroup> selectedMuscleGroups) {
        Log.d(TAG, "ResultsFragment.addMuscleGroupSelections()");
        updatePreferences(PREFERENCES_SELECTED_MUSCLE_GROUPS, selectedMuscleGroups);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        selectedMuscleGroups = new ArrayList<>();
        completedExercises = new ArrayList<>();
        skippedExercises = new ArrayList<>();

        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        clearPreferences(PREFERENCES_SELECTED_MUSCLE_GROUPS);
                        clearTable();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        fab = (FloatingActionButton) view.findViewById(R.id.fab_clear_data);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Clear all data?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                /* WTFF DONT DO THIS, NOTIFYHASCHANGED() EXISTS */

//                updateListView(PREFERENCES_SELECTED_MUSCLE_GROUPS, R.id.list_view_muscle_group_stats);
//                updateListView(PREFERENCES_SELECTED_EXERCISES, R.id.list_view_exercise_stats);
            }
        });

                /* WTFF DONT DO THIS, NOTIFYHASCHANGED() EXISTS */

//        // Update completed muscle group and exercise counts in their shared preferences files.
//        updatePreferences(PREFERENCES_SELECTED_MUSCLE_GROUPS, selectedMuscleGroups);
//        updatePreferences(PREFERENCES_SELECTED_EXERCISES, completedExercises);
//
//        // Update the list views in the window with the selections and their counts
//        updateListView(PREFERENCES_SELECTED_MUSCLE_GROUPS, R.id.list_view_muscle_group_stats);
//        updateListView(PREFERENCES_SELECTED_EXERCISES, R.id.list_view_exercise_stats);

        setRadarChart(view);

        return view;
    }

    /**
     * Clears a shared preference file of the specified tag.
     * @param sharedPreferencesTag the string representing the preferences file.
     */
    private void clearPreferences(String sharedPreferencesTag) {
        sharedPreferences = getActivity().getSharedPreferences(sharedPreferencesTag, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private ArrayList<TwoTuple<MuscleGroup, Integer>> getPreferences(String sharedPreferencesTag) {
        ArrayList<TwoTuple<MuscleGroup, Integer>> entries = new ArrayList<>();

        sharedPreferences = getActivity().getSharedPreferences(sharedPreferencesTag, Context.MODE_PRIVATE);

        int count;
        for (MuscleGroup muscleGroup : MuscleGroup.values()) {
            count = sharedPreferences.getInt(muscleGroup.toString(), 0);
            if (count > 0) {
                entries.add(new TwoTuple(muscleGroup, count));
            }
        }

        return entries;
    }

    private void updatePreferences(String sharedPreferencesTag, ArrayList<?> selections) {
        sharedPreferences = getActivity().getSharedPreferences(sharedPreferencesTag, Context.MODE_PRIVATE);
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
    private void updateListView(View view, String sharedPreferencesTag, int listViewResId) {
        LinkedHashMap<String, Integer> databaseEntries;

        sharedPreferences = getActivity().getSharedPreferences(sharedPreferencesTag, Context.MODE_PRIVATE);
        databaseEntries = new LinkedHashMap(sharedPreferences.getAll());

        ArrayList<TwoTuple<String, Integer>> histogram = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : databaseEntries.entrySet()) {
            histogram.add(new TwoTuple(entry.getKey(), entry.getValue()));
        }

        StringIntAdapter counterAdapter = new StringIntAdapter(getActivity(), histogram);

        ListView listViewStats = (ListView) view.findViewById(listViewResId);
        listViewStats.setAdapter(counterAdapter);
    }

    private void readTable() {
        ExerciseDbHelper mDbHelper = new ExerciseDbHelper(getActivity());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ExerciseContract.ExerciseEntry._ID,
                ExerciseContract.ExerciseEntry.COLUMN_EXERCISE,
                ExerciseContract.ExerciseEntry.COLUMN_GROUP,
                ExerciseContract.ExerciseEntry.COLUMN_DATE,
                ExerciseContract.ExerciseEntry.COLUMN_STATUS
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ExerciseContract.ExerciseEntry.COLUMN_DATE + " DESC";

        Cursor c = db.query(
                ExerciseContract.ExerciseEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String exercise = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_EXERCISE));
                String group = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_GROUP));
                String date = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_DATE));
                String status = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_STATUS));

                Log.d(TAG, date + " " + status + " " + group + " " + exercise);

                c.moveToNext();
            }
        }

        c.close();
        db.close();
    }

    private void clearTable() {
        ExerciseDbHelper mDbHelper = new ExerciseDbHelper(getActivity());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.delete(ExerciseContract.ExerciseEntry.TABLE_NAME, null, null);
        db.close();
    }

    private void setRadarChart(View view) {
        muscleGroupsChart = (RadarChart) view.findViewById(R.id.muscle_radar_chart);
        ArrayList<MuscleGroup> correspondingMuscleGroups = new ArrayList<>();

        muscleGroupsChart.setDescription("");
//
//        muscleGroupsChart.setWebLineWidth(1f);
//        muscleGroupsChart.setWebLineWidthInner(1f);
//        muscleGroupsChart.setWebAlpha(100);

        ArrayList<RadarEntry> radarChartEntries = new ArrayList<>();
        for (TwoTuple<MuscleGroup, Integer> entry :
                getPreferences(PREFERENCES_SELECTED_MUSCLE_GROUPS)) {
            radarChartEntries.add(new RadarEntry(entry.b));
            correspondingMuscleGroups.add(entry.a);
        }

        radarDataSet = new RadarDataSet(radarChartEntries, "Selected Muscle Groups");
//        radarDataSet.setColor(Color.rgb(103, 110, 129));
//        radarDataSet.setFillColor(Color.rgb(103, 110, 129));
        radarDataSet.setDrawFilled(true);
        radarDataSet.setFillAlpha(180);
        radarDataSet.setLineWidth(2f);
        radarDataSet.setDrawHighlightCircleEnabled(true);
        radarDataSet.setDrawHighlightIndicators(false);
//
        radarData = new RadarData(radarDataSet);
//        radarData.setValueTextSize(8f);
//        radarData.setDrawValues(false);
//        radarData.setValueTextColor(Color.BLACK);

        muscleGroupsChart.setData(radarData);
        muscleGroupsChart.invalidate();

        muscleGroupsChart.animateXY(1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = muscleGroupsChart.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new RadarChartFormatter(correspondingMuscleGroups));

        YAxis yAxis = muscleGroupsChart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setDrawLabels(false);
        yAxis.setAxisMinValue(0f);

        Legend l = muscleGroupsChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.BLACK);
    }

//    private void clearChart() {
//        ArrayList<RadarEntry> emptyEntryList = new ArrayList<>();
//        radarDataSet = new RadarDataSet(emptyEntryList, "Selected Muscle Groups");
//        radarData = new RadarData(radarDataSet);
//        muscleGroupsChart.invalidate();
//    }

    /** Make a toast */
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
