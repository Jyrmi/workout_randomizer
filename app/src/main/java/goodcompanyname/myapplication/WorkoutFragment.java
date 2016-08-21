package goodcompanyname.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import adapter.ExerciseRecyclerAdapter;
import adapter.TwoTuple;
import constant.MuscleGroup;
import sqlite.ExerciseContract;
import sqlite.ExerciseDbHelper;

public class WorkoutFragment extends Fragment {
    // todo: expand cardviews or flip them on click to show tooltips and guides
    // todo: write swiped exercises to sqlite db

    private static final String TAG = "WorkoutFragment";
    public static final String ARG_PAGE = "ARG_PAGE";
    private final static String PREFERENCES_QUEUED_EXERCISES =
            "goodcompanyname.myapplication.workout_randomizer.queued_exercises";

    TextView textViewEmpty;

    FloatingActionButton fab;

    private ArrayList<TwoTuple<String, MuscleGroup>> exerciseList;
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
    protected void addMuscleGroupSelections(ArrayList<MuscleGroup> selectedMuscleGroups) {
        Log.d(TAG, "WorkoutFragment.addMuscleGroupSelections()");
        if (!selectedMuscleGroups.isEmpty()) {
            for (MuscleGroup muscleGroup : selectedMuscleGroups) {
                // Get one random exercise from this muscle group
                exerciseList.add(muscleGroup.pickOneRandomExercise());
            }
            if (exerciseAdapter != null) exerciseAdapter.notifyDataSetChanged();
            textViewEmpty.setVisibility(View.INVISIBLE);
        }
    }

    private void writeExerciseDbEntry(TwoTuple<String, MuscleGroup> exercise, String status) {
        // Gets the data repository in write mode
        ExerciseDbHelper mDbHelper = new ExerciseDbHelper(getActivity());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        String dateAndTime = Calendar.getInstance().getTime().toString();
        ContentValues values = new ContentValues();
        values.put(ExerciseContract.ExerciseEntry.COLUMN_EXERCISE, exercise.a);
        values.put(ExerciseContract.ExerciseEntry.COLUMN_GROUP, exercise.b.toString());
        values.put(ExerciseContract.ExerciseEntry.COLUMN_DATE, dateAndTime);
        values.put(ExerciseContract.ExerciseEntry.COLUMN_STATUS, status);

        // Insert the new row, returning the primary key value of the new row
        db.insert(ExerciseContract.ExerciseEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fetch the saved list of exercises and show them in the view.
        LinkedHashMap<String, Integer> entries;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                PREFERENCES_QUEUED_EXERCISES, Context.MODE_PRIVATE);

        LinkedHashMap<String, String> savedExercises = new LinkedHashMap(sharedPreferences.getAll());

        exerciseList = new ArrayList();
        for (Map.Entry<String, String> entry : savedExercises.entrySet()) {
            exerciseList.add(
                    new TwoTuple(entry.getKey(), MuscleGroup.toMuscleGroup(entry.getValue())));
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
                    TwoTuple<String, MuscleGroup> exercise = exerciseList.get(position);
                    if (direction == ItemTouchHelper.LEFT) { // Skip this exercise and replace it
                        exerciseList.set(position, exercise.b.pickOneRandomExercise());
                        makeToast("Skipped.");
                    } else {
                        exerciseList.remove(position);
                        status = "completed";
                        if (exerciseList.isEmpty()) textViewEmpty.setVisibility(View.VISIBLE);
                        makeToast("Exercise complete.");
                    }
                    writeExerciseDbEntry(exercise, status);
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

    public ArrayList<TwoTuple<String, MuscleGroup>> getQueuedExercises() {
        return exerciseList;
    }

    /** Make a toast */
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
