package goodcompanyname.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import adapter.ExerciseRecyclerAdapter;
import adapter.FourTuple;
import adapter.TwoTuple;
import constant.MuscleGroup;
import constant.PreferenceTags;
import constant.Settings;
import sqlite.ExerciseContract;
import sqlite.ExerciseDb;
import sqlite.LogsContract;
import sqlite.LogsDbHelper;

public class WorkoutFragment extends Fragment {
    // todo: expand cardviews or flip them on click to show tooltips and guides

    private static final String TAG = "WorkoutFragment";
    public static final String ARG_PAGE = "ARG_PAGE";

    TextView textViewEmpty;

    FloatingActionButton fab;

    protected ArrayList<TwoTuple<String, String>> exerciseList;
    private RecyclerView recyclerViewExercises;
    ExerciseRecyclerAdapter exerciseAdapter;

    public static WorkoutFragment newInstance(int pageNo) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        WorkoutFragment fragment = new WorkoutFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Responds to SelectionFragment's button for generating exercises for selected muscle groups.
     * @param selectedMuscleGroups list of muscle groups selected within SelectionFragment.
     */
    protected void addMuscleGroupSelections(ArrayList<String> selectedMuscleGroups) {
        Log.d(TAG, "WorkoutFragment.addMuscleGroupSelections()");
        if (!selectedMuscleGroups.isEmpty()) {
            for (String muscleGroup : selectedMuscleGroups) {
                // Query exercise.db in the assets/databases directory for exercises matching
                // the user's settings
                TwoTuple<String, String> queryResult = pickOneRandomExercise(muscleGroup);
                if (queryResult == null) {
                    makeToast("No exercises matched your preferences.");
                } else {
                    exerciseList.add(queryResult);
                }

                // OLD CODE FOR HARDCODED EXERCISES
                // Get one random exercise from this muscle group
//                exerciseList.add(muscleGroup.pickOneRandomExercise());
            }
            if (exerciseAdapter != null) exerciseAdapter.notifyDataSetChanged();
            textViewEmpty.setVisibility(View.INVISIBLE);
        }
    }

    public TwoTuple<String, String> pickOneRandomExercise(String muscleGroup) {
        ArrayList<String> queryResult = queryExercises(muscleGroup);

        if (queryResult.size() > 0) {
            Random gen = new Random();
            int random = gen.nextInt(queryResult.size());
            return new TwoTuple(queryResult.get(random), muscleGroup);
        } else return null;
    }

    protected ArrayList<String> queryExercises(String muscleGroup) {
        ArrayList<String> queryResult = new ArrayList<>();

        ExerciseDb exerciseDb = new ExerciseDb(getActivity());
        SQLiteDatabase db = exerciseDb.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ExerciseContract.ExerciseEntry.COLUMN_NAME,
        };

        // WHERE clause
        String selection = ExerciseContract.ExerciseEntry.COLUMN_GROUP + " = ?";

        // Arguments to the WHERE clause
        String[] selectionArgs = {muscleGroup};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ExerciseContract.ExerciseEntry.COLUMN_NAME + " ASC";

        Cursor c = db.query(
                ExerciseContract.ExerciseEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String exercise = c.getString(c.getColumnIndex(ExerciseContract.ExerciseEntry.COLUMN_NAME));

                queryResult.add(exercise);

                Log.d(TAG, exercise);

                c.moveToNext();
            }
        }

        c.close();
        db.close();

        return queryResult;
    }

    private void writeLogEntry(TwoTuple<String, String> exercise, String status) {
        // Gets the data repository in write mode
        LogsDbHelper mDbHelper = new LogsDbHelper(getActivity());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        String dateAndTime = Calendar.getInstance().getTime().toString();
        ContentValues values = new ContentValues();
        values.put(LogsContract.LogEntry.COLUMN_EXERCISE, exercise.a);
        values.put(LogsContract.LogEntry.COLUMN_GROUP, exercise.b);
        values.put(LogsContract.LogEntry.COLUMN_DATE, dateAndTime);
        values.put(LogsContract.LogEntry.COLUMN_STATUS, status);

        // Insert the new row, returning the primary key value of the new row
        db.insert(LogsContract.LogEntry.TABLE_NAME, null, values);

        db.close();
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
            exerciseList.add(
                    new TwoTuple(entry.getKey(), entry.getValue()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        // Hide the "No exercises queued" message if there are queued exercises retrieved from prefs
        textViewEmpty = (TextView) view.findViewById(R.id.text_no_exercises);
        if (!exerciseList.isEmpty()) textViewEmpty.setVisibility(View.INVISIBLE);

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
                    TwoTuple<String, String> exercise = exerciseList.get(position);
                    if (direction == ItemTouchHelper.LEFT) { // Skip this exercise and replace it
                        TwoTuple<String, String> queryResult = pickOneRandomExercise(exercise.b);
                        makeToast("Skipped.");
                        if (queryResult == null) {
                            makeToast("No exercises matched your preferences.");
                        } else exerciseList.set(position, queryResult);
                    } else {
                        exerciseList.remove(position);
                        status = "completed";
                        if (exerciseList.isEmpty()) textViewEmpty.setVisibility(View.VISIBLE);
                        makeToast("Exercise complete.");
                    }
                    writeLogEntry(exercise, status);
                    exerciseAdapter.notifyDataSetChanged();
                }
            }
        );

        mIth.attachToRecyclerView(recyclerViewExercises);

        fab = (FloatingActionButton) view.findViewById(R.id.fab_workout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                writeWorkoutDbEntry(completedExercises);
                makeToast("supposed to navigate to next page now.");
            }
        });

        return view;
    }

    /** Make a toast */
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
