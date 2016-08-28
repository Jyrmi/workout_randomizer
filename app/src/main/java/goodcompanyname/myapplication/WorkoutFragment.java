package goodcompanyname.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import adapter.ExerciseRecyclerAdapter;
import constant.PreferenceTags;
import constant.TwoTuple;
import preferences.MySharedPrefs;
import sqlite.ExerciseContract;
import sqlite.ExerciseDb;
import sqlite.LogsContract;
import sqlite.LogsDbHelper;

public class WorkoutFragment extends Fragment {
    // todo: smooth replace cards (6)
    // todo: smoothly insert cards (7)
    // todo: center-align card text and generally make them look better (7)
    // todo: better indicate that cards can be swiped, or add buttons on either side of the cards
    // that skip/complete
    // todo: consider making each entry a "workout", a box containing multiple exercises
    // generating exercises multiple times in a short time frame would put all into the same workout

    private static final String TAG = "WorkoutFragment";
    public static final String ARG_PAGE = "ARG_PAGE";

    RelativeLayout tooltip;
    TextView textViewEmpty;

    LinkedList<String> selectionQueue = new LinkedList<>();

    ArrayList<HashMap<String, String>> exerciseList;
    RecyclerView recyclerViewExercises;
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

        recallQueuedExercises();
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
                        return true; // true if moved, false otherwise
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible() && !selectionQueue.isEmpty()) {
            addExercisesToView();
        }
    }

    private void firstRunSettingsCheck(View view) {
        // Show tooltips on first run
        final SharedPreferences defaultPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (defaultPreferences.getBoolean("firstrun", true)) {
            tooltip = (RelativeLayout) view.findViewById(R.id.workout_tooltip);
            tooltip.setVisibility(View.VISIBLE);
            tooltip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tooltip.setVisibility(View.GONE);
                    MySharedPrefs.finishFirstRun(view.getContext());
                }
            });
        }
    }

    private void recallQueuedExercises() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                PreferenceTags.PREFERENCES_QUEUED_EXERCISES, Context.MODE_PRIVATE);

        LinkedHashMap<String, String> savedExercises = new LinkedHashMap(sharedPreferences.getAll());

        exerciseList = new ArrayList();
        for (Map.Entry<String, String> entry : savedExercises.entrySet()) {
            exerciseList.add(ExerciseDb.queryExercise(getActivity(), entry.getKey()));
        }
    }

    /**
     * Responds to SelectionFragment's button for generating exercises for selected muscle groups.
     * @param selectedMuscleGroups list of muscle groups selected within SelectionFragment.
     */
    protected void addMuscleGroupSelections(ArrayList<String> selectedMuscleGroups) {
        Log.d(TAG, "WorkoutFragment.addMuscleGroupSelections()");
        if (!selectedMuscleGroups.isEmpty()) {
            for (String muscleGroup : selectedMuscleGroups) {
                selectionQueue.add(muscleGroup);
            }
        }
    }

    protected void addExercisesToView() {
        ArrayList<String> missedGroups = new ArrayList<>();
        while (!selectionQueue.isEmpty()) {
            String muscleGroup = selectionQueue.removeFirst();
            // Query exercise.db in the assets/databases directory for exercises matching
            // the user's settings
            HashMap<String, String> randomExercise = pickOneRandomExercise(muscleGroup);
            if (randomExercise == null) {
                missedGroups.add(muscleGroup);
            } else {
//                // Smoothly add the new exercise
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                exerciseList.add(randomExercise);
//                exerciseAdapter.notifyDataSetChanged();
            }
        }
        if (!missedGroups.isEmpty()) {
            Snackbar.make(getView(), "No " + TextUtils.join(", ", missedGroups)
                    + " exercises matched your settings.", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
        exerciseAdapter.notifyDataSetChanged();
        textViewEmpty.setVisibility(View.INVISIBLE);
    }

    public HashMap<String, String> pickOneRandomExercise(String muscleGroup) {
        ArrayList<HashMap<String, String>> queryResult =
                ExerciseDb.queryGroup(getActivity(),muscleGroup);

        if (queryResult.size() > 0) {
            return queryResult.get(new Random().nextInt(queryResult.size()));
        } else return null;
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
