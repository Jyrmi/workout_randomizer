package goodcompanyname.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import adapter.ExerciseRecyclerAdapter;
import constant.PreferenceTags;
import constant.TwoTuple;
import sqlite.ExerciseContract;
import sqlite.ExerciseDb;
import sqlite.LogsContract;
import sqlite.LogsDbHelper;

public class WorkoutFragment extends Fragment {
    // todo: smooth replace cards (6)
    // todo: smoothly insert cards (7)
    // todo: center-align card text and generally make them look better (7)
    // todo: move database operations to their respective classes (8)

    private static final String TAG = "WorkoutFragment";
    public static final String ARG_PAGE = "ARG_PAGE";

    RelativeLayout tooltip;
    TextView textViewEmpty;

    protected ArrayList<HashMap<String, String>> exerciseList;
    private RecyclerView recyclerViewExercises;
    ExerciseRecyclerAdapter exerciseAdapter;

    public static WorkoutFragment newInstance(int pageNo) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        WorkoutFragment fragment = new WorkoutFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fetch the saved list of exercises and show them in the view.
        LinkedHashMap<String, Integer> entries;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                PreferenceTags.PREFERENCES_QUEUED_EXERCISES, Context.MODE_PRIVATE);

        LinkedHashMap<String, String> savedExercises = new LinkedHashMap(sharedPreferences.getAll());

        exerciseList = new ArrayList();
        for (Map.Entry<String, String> entry : savedExercises.entrySet()) {
            exerciseList.add(queryExercise(entry.getKey()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        // Hide the "No exercises queued" message if there are queued exercises retrieved from prefs
        textViewEmpty = (TextView) view.findViewById(R.id.text_no_exercises);
        if (!exerciseList.isEmpty()) textViewEmpty.setVisibility(View.INVISIBLE);

//        fab = (FloatingActionButton) view.findViewById(R.id.fab_workout);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });

        if (exerciseList == null) exerciseList = new ArrayList();
        exerciseAdapter = new ExerciseRecyclerAdapter(exerciseList);

        recyclerViewExercises = (RecyclerView) view.findViewById(R.id.exercises_recycler_view);
        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewExercises.setAdapter(exerciseAdapter);

        ItemTouchHelper mIth = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
//                    final int fromPos = viewHolder.getAdapterPosition();
//                    final int toPos = target.getAdapterPosition();
                        // move item in `fromPos` to `toPos` in adapter.
                        return true;// true if moved, false otherwise
                    }
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        String status = "skipped";
                        int position = viewHolder.getAdapterPosition();
                        HashMap<String, String> exercise = exerciseList.get(position);
                        if (direction == ItemTouchHelper.LEFT) { // Skip this exercise and replace it
                            HashMap<String, String> queryResult = pickOneRandomExercise(
                                    exercise.get(ExerciseContract.ExerciseEntry.COLUMN_GROUP));
                            makeToast("Skipped.");
                            if (queryResult == null) {
                                makeToast("No " + exercise.get(ExerciseContract.ExerciseEntry.
                                        COLUMN_GROUP) + " exercises matched your preferences.");
                                exerciseList.remove(position);
                            } else exerciseList.set(position, queryResult);
                        } else {
                            exerciseList.remove(position);
                            status = "completed";
                            if (exerciseList.isEmpty()) textViewEmpty.setVisibility(View.VISIBLE);
                            makeToast("Exercise complete.");
                        }

                        exerciseAdapter.notifyDataSetChanged();
                        exerciseAdapter.notifyItemRemoved(position);
                        exerciseAdapter.notifyItemRangeChanged(position, exerciseList.size());

                        writeLogEntry(exercise, status);
                    }
                }
        );

        mIth.attachToRecyclerView(recyclerViewExercises);

        firstRunSettingsCheck(view);

        return view;
    }

    public void firstRunSettingsCheck(View view) {
        // Show tooltips on first run
        final SharedPreferences defaultPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (defaultPreferences.getBoolean("showtooltips", true)) {
            tooltip = (RelativeLayout) view.findViewById(R.id.workout_tooltip);
            tooltip.setVisibility(View.VISIBLE);
            tooltip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tooltip.setVisibility(View.GONE);
                    SharedPreferences.Editor editor = defaultPreferences.edit();
                    editor.putBoolean("showtooltips", false);
                    editor.apply();
                }
            });
        }
    }

    /**
     * Responds to SelectionFragment's button for generating exercises for selected muscle groups.
     * @param selectedMuscleGroups list of muscle groups selected within SelectionFragment.
     */
    protected void addMuscleGroupSelections(ArrayList<String> selectedMuscleGroups) {
        Log.d(TAG, "WorkoutFragment.addMuscleGroupSelections()");
        if (!selectedMuscleGroups.isEmpty()) {
            ArrayList<String> missedGroups = new ArrayList<>();
            for (String muscleGroup : selectedMuscleGroups) {
                // Query exercise.db in the assets/databases directory for exercises matching
                // the user's settings
                HashMap<String, String> randomExercise = pickOneRandomExercise(muscleGroup);
                if (randomExercise == null) {
                    missedGroups.add(muscleGroup);
                } else {
                    exerciseList.add(randomExercise);
                }
            }
            if (!missedGroups.isEmpty()) {
                Snackbar.make(getView(), "No " + TextUtils.join(", ", missedGroups)
                        + " exercises matched your settings.", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
            if (exerciseAdapter != null) exerciseAdapter.notifyDataSetChanged();
            textViewEmpty.setVisibility(View.INVISIBLE);
        }
    }

    public HashMap<String, String> pickOneRandomExercise(String muscleGroup) {
        ArrayList<HashMap<String, String>> queryResult = queryGroup(muscleGroup);

        if (queryResult.size() > 0) {
            return queryResult.get(new Random().nextInt(queryResult.size()));
        } else return null;
    }

    protected ArrayList<HashMap<String, String>> queryGroup(String muscleGroup) {
        ArrayList<HashMap<String, String>> exercises = new ArrayList<>();
        HashMap<String, String> exerciseDetails;

        ExerciseDb exerciseDb = new ExerciseDb(getActivity());
        SQLiteDatabase db = exerciseDb.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ExerciseContract.ExerciseEntry.COLUMN_NAME,
                ExerciseContract.ExerciseEntry.COLUMN_GROUP,
                ExerciseContract.ExerciseEntry.COLUMN_MALE_VIDEO,
                ExerciseContract.ExerciseEntry.COLUMN_MALE_IMAGES,
                ExerciseContract.ExerciseEntry.COLUMN_FEMALE_VIDEO,
                ExerciseContract.ExerciseEntry.COLUMN_FEMALE_IMAGES
        };

        // Get the settings to create the WHERE clause
        TwoTuple<String, ArrayList<String>> whereClause = generateWhereClause();
        String whereSQL = whereClause.a;
        ArrayList<String> whereArgs = whereClause.b;
        whereArgs.add(0, muscleGroup);

        // WHERE clause
        String selection = ExerciseContract.ExerciseEntry.COLUMN_GROUP + "=? AND " + whereSQL;

        // Arguments to the WHERE clause
        String[] selectionArgs = whereArgs.toArray(new String[0]);

        Log.d(TAG, selection);
        Log.d(TAG, whereArgs.toString());

        Cursor c = db.query(
                ExerciseContract.ExerciseEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                exerciseDetails = new HashMap<>();

                String exercise = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_NAME));
                String group = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_GROUP));
                String mVideo = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_MALE_VIDEO));
                String mImages = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_MALE_IMAGES));
                String fVideo = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_VIDEO));
                String fImages = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_IMAGES));

                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_NAME, exercise);
                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_GROUP, group);
                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_MALE_VIDEO, mVideo);
                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_MALE_IMAGES, mImages);
                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_VIDEO, fVideo);
                exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_IMAGES, fImages);

                exercises.add(exerciseDetails);

                c.moveToNext();
            }
        }

        c.close();
        db.close();

        return exercises;
    }

    protected HashMap<String, String> queryExercise(String exerciseName) {
        HashMap<String, String> exerciseDetails = new HashMap<>();

        ExerciseDb exerciseDb = new ExerciseDb(getActivity());
        SQLiteDatabase db = exerciseDb.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ExerciseContract.ExerciseEntry.COLUMN_NAME,
                ExerciseContract.ExerciseEntry.COLUMN_GROUP,
                ExerciseContract.ExerciseEntry.COLUMN_MALE_VIDEO,
                ExerciseContract.ExerciseEntry.COLUMN_MALE_IMAGES,
                ExerciseContract.ExerciseEntry.COLUMN_FEMALE_VIDEO,
                ExerciseContract.ExerciseEntry.COLUMN_FEMALE_IMAGES
        };

        // WHERE clause
        String selection = ExerciseContract.ExerciseEntry.COLUMN_NAME + "=?";

        // Arguments to the WHERE clause
        String[] selectionArgs = {exerciseName};

        Cursor c = db.query(
                ExerciseContract.ExerciseEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if (c.moveToFirst()) {
            exerciseDetails = new HashMap<>();

            String exercise = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_NAME));
            String group = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_GROUP));
            String mVideo = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_MALE_VIDEO));
            String mImages = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_MALE_IMAGES));
            String fVideo = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_VIDEO));
            String fImages = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_IMAGES));

            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_NAME, exercise);
            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_GROUP, group);
            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_MALE_VIDEO, mVideo);
            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_MALE_IMAGES, mImages);
            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_VIDEO, fVideo);
            exerciseDetails.put(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_IMAGES, fImages);

            c.moveToNext();
        }

        c.close();
        db.close();

        return exerciseDetails;
    }

    public TwoTuple<String, ArrayList<String>> generateWhereClause() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                PreferenceTags.PREFERENCES_SETTINGS, Context.MODE_PRIVATE);

        ArrayList<String> whereClauseSections = new ArrayList<>();
        ArrayList<String> queryArgs = new ArrayList<>();

        HashMap<String, String> settings = (HashMap) sharedPreferences.getAll();
        HashMap<String, ArrayList<String>> categories = new HashMap<>();

        for (Map.Entry<String, String> entry : settings.entrySet()) {
            String category = entry.getValue();

            if (!category.equals("Gender")) { // Gender is not a column, so it shouldn't be in the where clause.
                if (!categories.containsKey(category)) {
                    categories.put(category, new ArrayList<String>());
                }
                categories.get(category).add(entry.getKey());
            }
        }

        for (Map.Entry<String, ArrayList<String>> entry : categories.entrySet()) {
            ArrayList<String> whereStatements = new ArrayList<>();
            for (String setting : entry.getValue()) {
                whereStatements.add(entry.getKey() + "=?");
                queryArgs.add(setting);
            }
            whereClauseSections.add("(" + TextUtils.join(" OR ", whereStatements) + ")");
        }

        String fullWhereClause = TextUtils.join(" AND ", whereClauseSections);

        return new TwoTuple<>(fullWhereClause, queryArgs);
    }

    private void writeLogEntry(HashMap<String, String> exercise, String status) {
        // Gets the data repository in write mode
        LogsDbHelper mDbHelper = new LogsDbHelper(getActivity());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        String dateAndTime = Calendar.getInstance().getTime().toString();
        ContentValues values = new ContentValues();
        values.put(LogsContract.LogEntry.COLUMN_EXERCISE,
                exercise.get(ExerciseContract.ExerciseEntry.COLUMN_NAME));
        values.put(LogsContract.LogEntry.COLUMN_GROUP,
                exercise.get(ExerciseContract.ExerciseEntry.COLUMN_GROUP));
        values.put(LogsContract.LogEntry.COLUMN_DATE, dateAndTime);
        values.put(LogsContract.LogEntry.COLUMN_STATUS, status);

        // Insert the new row, returning the primary key value of the new row
        db.insert(LogsContract.LogEntry.TABLE_NAME, null, values);

        db.close();

//        MySharedPrefs.putDefaultBool(getActivity(), "newlogs", true);
    }

    public void updatePreferences() {
        // Remember the current list of queued exercises for the next time the app is started.
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                PreferenceTags.PREFERENCES_QUEUED_EXERCISES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        for (HashMap<String, String> exercise : exerciseList) {
            editor.putString(exercise.get(ExerciseContract.ExerciseEntry.COLUMN_NAME),
                    exercise.get(ExerciseContract.ExerciseEntry.COLUMN_GROUP));
        }
        editor.apply();
    }

    /** Make a toast */
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
