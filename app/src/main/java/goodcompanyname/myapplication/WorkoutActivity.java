package goodcompanyname.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import adapter.ExerciseRecyclerAdapter;
import adapter.TwoTuple;
import constant.MuscleGroup;
import sqlite.WorkoutContract;
import sqlite.WorkoutDbHelper;

public class WorkoutActivity extends AppCompatActivity {
    // todo: expand cardviews to show tooltips and guides
    // todo: change "finish" button to circle with + symbol

    private static final String TAG = "WorkoutActivity";

    private ArrayList<MuscleGroup> selectedMuscleGroups;
    private ArrayList<String> completedExercises;
    private ArrayList<String> skippedExercises;

    private RecyclerView recyclerViewExercises;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        selectedMuscleGroups = (ArrayList<MuscleGroup>) bundle.get("EXTRA_MUSCLE_GROUPS");
        completedExercises = new ArrayList<>();
        skippedExercises = new ArrayList<>();

        final ArrayList<TwoTuple<String, MuscleGroup>> exerciseList = new ArrayList();
        for (MuscleGroup muscleGroup : selectedMuscleGroups) {
            // Get one random exercise from this muscle group
            exerciseList.add(muscleGroup.pickOneRandomExercise());
        }

        final ExerciseRecyclerAdapter exerciseAdapter = new ExerciseRecyclerAdapter(exerciseList);

        recyclerViewExercises = (RecyclerView) findViewById(R.id.exercises_recycler_view);
        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewExercises.setAdapter(exerciseAdapter);

        ItemTouchHelper mIth = new ItemTouchHelper(
            new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                public boolean onMove(RecyclerView recyclerView,
                                      RecyclerView.ViewHolder viewHolder,
                                      RecyclerView.ViewHolder target) {
                    final int fromPos = viewHolder.getAdapterPosition();
                    final int toPos = target.getAdapterPosition();
                    // move item in `fromPos` to `toPos` in adapter.
                    return true;// true if moved, false otherwise
                }
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    TwoTuple<String, MuscleGroup> exercise = exerciseList.get(position);
                    if (direction == ItemTouchHelper.LEFT) { // Skip this exercise and replace it
                        skippedExercises.add(exercise.a);
                        exerciseList.set(position, exercise.b.pickOneRandomExercise());
                        makeToast("Skipped.");
                    } else {
                        completedExercises.add(exercise.a);
                        exerciseList.remove(position);
                        makeToast("Exercise complete.");
                    }
                    exerciseAdapter.notifyDataSetChanged();
                }
            }
        );

        mIth.attachToRecyclerView(recyclerViewExercises);

        fab = (FloatingActionButton) findViewById(R.id.fab_workout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int i;
//                for (i = 0; i < recyclerViewExercises.getChildCount(); i++) {
//                    View cardView = recyclerViewExercises.getChildAt(i);
//                    CheckBox checkBox =
//                            (CheckBox) cardView.findViewById(R.id.card_checkbox);
//
//                    TextView textViewExercise =
//                            (TextView) cardView.findViewById(R.id.card_exercise);
//
//                    String exerciseName = textViewExercise.getText().toString();
//                    if (checkBox.isChecked()) {
//                        completedExercises.add(exerciseName);
//                    }
//                }

                writeWorkoutDbEntry(completedExercises);
                navigateToResults();
            }
        });
    }

    /** Make a toast */
    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void writeWorkoutDbEntry(ArrayList<String> checkedExercises) {
        // Gets the data repository in write mode
        WorkoutDbHelper mDbHelper = new WorkoutDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        String dateAndTime = Calendar.getInstance().getTime().toString();
        ContentValues values = new ContentValues();
        values.put(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT, checkedExercises.toString());
        values.put(WorkoutContract.WorkoutEntry.COLUMN_DATE, dateAndTime);

        // Insert the new row, returning the primary key value of the new row
        db.insert(WorkoutContract.WorkoutEntry.TABLE_NAME, null, values);
    }

    private void navigateToResults() {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("EXTRA_MUSCLE_GROUPS", selectedMuscleGroups);
        intent.putExtra("EXTRA_COMPLETED_EXERCISES", completedExercises);
        intent.putExtra("EXTRA_SKIPPED_EXERCISES", skippedExercises);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
