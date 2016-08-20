package goodcompanyname.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import adapter.ExerciseRecyclerAdapter;
import adapter.StringStringAdapter;
import constant.MuscleGroup;
import sqlite.WorkoutContract;
import sqlite.WorkoutDbHelper;

public class WorkoutActivity extends AppCompatActivity {
    // todo: expand cardviews to show tooltips and guides
    // todo: change "finish" button to circle with + symbol

    private static final String TAG = "WorkoutActivity";

//    private ListView listViewExercises;
    private RecyclerView recyclerViewExercises;
    private Button buttonDone;
    FloatingActionButton fab;

    private ArrayList<MuscleGroup> selectedMuscleGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        selectedMuscleGroups = (ArrayList<MuscleGroup>) bundle.get("EXTRA_MUSCLE_GROUPS");

        ArrayList<String> muscleGroupList = new ArrayList();
        ArrayList<String> exerciseList = new ArrayList();
        Random gen = new Random();
        for (MuscleGroup muscleGroup : selectedMuscleGroups) {
//            for (String exercise : muscleGroup.getExercises()) {
//                muscleGroupList.add(muscleGroup.toString());
//                exerciseList.add(exercise);
//
//                double exponentialDistr = -5 * Math.log(gen.nextDouble());
//                Log.d(TAG, Double.toString(exponentialDistr) + " * " + exercise);
//            }

            // Get one random exercise from this muscle group
            muscleGroupList.add(muscleGroup.toString());
            int random = gen.nextInt(muscleGroup.getExercises().size());
            exerciseList.add(muscleGroup.getExercises().get(random));
        }

//        StringStringAdapter exerciseAdapter = new StringStringAdapter(this, muscleGroupList, exerciseList);

        ExerciseRecyclerAdapter exerciseAdapter = new ExerciseRecyclerAdapter(exerciseList, muscleGroupList);

        recyclerViewExercises = (RecyclerView) findViewById(R.id.exercises_recycler_view);
        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewExercises.setAdapter(exerciseAdapter);

        ItemTouchHelper mIth = new ItemTouchHelper(
            new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                    ItemTouchHelper.LEFT) {
                public boolean onMove(RecyclerView recyclerView,
                                      RecyclerView.ViewHolder viewHolder,
                                      RecyclerView.ViewHolder target) {
                    final int fromPos = viewHolder.getAdapterPosition();
                    final int toPos = target.getAdapterPosition();
                    // move item in `fromPos` to `toPos` in adapter.
                    return true;// true if moved, false otherwise
                }
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    // remove from adapter
                }
            }
        );

        mIth.attachToRecyclerView(recyclerViewExercises);


//        listViewExercises = (ListView) findViewById(R.id.exercises_list_view);
//        listViewExercises.setAdapter(exerciseAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab_workout);
        fab.setOnClickListener(new View.OnClickListener() {
            ArrayList<String> checkedExercises = new ArrayList<>();
            ArrayList<String> skippedExercises = new ArrayList<>();

            @Override
            public void onClick(View view) {
                int i;
                for (i = 0; i < recyclerViewExercises.getChildCount(); i++) {
                    View cardView = recyclerViewExercises.getChildAt(i);
                    CheckBox checkBox =
                            (CheckBox) cardView.findViewById(R.id.list_view_checkbox);

                    TextView textViewExercise =
                            (TextView) cardView.findViewById(R.id.list_view_exercise);
                    String exerciseName = textViewExercise.getText().toString();
                    if (checkBox.isChecked()) {
                        checkedExercises.add(exerciseName);
                    } else {
                        skippedExercises.add(exerciseName);
                    }
                }

                writeWorkoutDbEntry(checkedExercises);

                // Start the Results activity
                Intent intent = new Intent(view.getContext(), ResultsActivity.class);
                intent.putExtra("EXTRA_MUSCLE_GROUPS", selectedMuscleGroups);
                intent.putExtra("EXTRA_COMPLETED_EXERCISES", checkedExercises);
                intent.putExtra("EXTRA_SKIPPED_EXERCISES", skippedExercises);
                startActivity(intent);
                finish();
            }
        });
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
        long newRowId;
        newRowId = db.insert(WorkoutContract.WorkoutEntry.TABLE_NAME, null, values);
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
