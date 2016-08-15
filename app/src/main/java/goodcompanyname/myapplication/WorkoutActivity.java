package goodcompanyname.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class WorkoutActivity extends AppCompatActivity {

    private static final String TAG = "WorkoutActivity";

    private Button buttonDone;
    private HashMap<MuscleGroup, ArrayList<String>> exercises;
    private ArrayList<MuscleGroup> selectedMuscleGroups;
    private HashMap<MuscleGroup, String> muscleGroupNames;
    private ListView listViewExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        selectedMuscleGroups = (ArrayList<MuscleGroup>) bundle.get("EXTRA_MUSCLE_GROUPS");

        // DEBUG
        Log.d(TAG, selectedMuscleGroups.toString());

        muscleGroupNames = new HashMap<>();

        muscleGroupNames.put(MuscleGroup.ABS, "Abs");
        muscleGroupNames.put(MuscleGroup.BICEPS, "Biceps");
        muscleGroupNames.put(MuscleGroup.CHEST, "Chest");
        muscleGroupNames.put(MuscleGroup.FOREARMS, "Forearms");
        muscleGroupNames.put(MuscleGroup.CALVES, "Calves");
        muscleGroupNames.put(MuscleGroup.GLUTES, "Glutes");
        muscleGroupNames.put(MuscleGroup.HAMSTRINGS, "Hamstrings");
        muscleGroupNames.put(MuscleGroup.LATS, "Lats");
        muscleGroupNames.put(MuscleGroup.LOWER_BACK, "Lower Back");
        muscleGroupNames.put(MuscleGroup.MIDDLE_BACK, "Middle Back");
        muscleGroupNames.put(MuscleGroup.SHOULDERS, "Shoulders");
        muscleGroupNames.put(MuscleGroup.TRAPS, "Traps");
        muscleGroupNames.put(MuscleGroup.TRICEPS, "Triceps");
        muscleGroupNames.put(MuscleGroup.NECK, "Neck");
        muscleGroupNames.put(MuscleGroup.QUADS, "Quads");

        ArrayList<String> neckExercises = new ArrayList<>();
        neckExercises.add("LYING FACE DOWN PLATE NECK RESISTANCE");
        neckExercises.add("LYING FACE UP PLATE NECK RESISTANCE");

        ArrayList<String> trapsExercises = new ArrayList<>();
        trapsExercises.add("SMITH MACHINE SHRUG");
        trapsExercises.add("LEVERAGE SHRUG");

        ArrayList<String> shouldersExercises = new ArrayList<>();
        shouldersExercises.add("CLEAN AND PRESS");
        shouldersExercises.add("DUMBBELL REAR DELT ROW");

        ArrayList<String> chestExercises = new ArrayList<>();
        chestExercises.add("PUSHUPS");
        chestExercises.add("BARBELL BENCH PRESS - MEDIUM GRIP");

        ArrayList<String> bicepsExercises = new ArrayList<>();
        bicepsExercises.add("BICEPS CURL TO SHOULDER PRESS");
        bicepsExercises.add("INCLINE HAMMER CURLS");

        ArrayList<String> forearmsExercises = new ArrayList<>();
        forearmsExercises.add("RICKSHAW CARRY");
        forearmsExercises.add("FARMER\'S WALK");

        ArrayList<String> absExercises = new ArrayList<>();
        absExercises.add("LANDMINE 180\'S");
        absExercises.add("ONE-ARM MEDICINE BALL SLAM");

        ArrayList<String> quadsExercises = new ArrayList<>();
        quadsExercises.add("CLEAN FROM BLOCKS");
        quadsExercises.add("SINGLE-LEG PRESS");

        ArrayList<String> calvesExercises = new ArrayList<>();
        calvesExercises.add("SMITH MACHINE CALF RAISE");
        calvesExercises.add("STANDING CALF RAISES");

        ArrayList<String> tricepsExercises = new ArrayList<>();
        tricepsExercises.add("DECLINE EZ BAR TRICEPS EXTENSION");
        tricepsExercises.add("PARALLEL BAR DIP");

        ArrayList<String> latsExercises = new ArrayList<>();
        latsExercises.add("WIDE-GRIP PULL-UP");
        latsExercises.add("WEIGHTED PULL UPS");

        ArrayList<String> middlebackExercises = new ArrayList<>();
        middlebackExercises.add("T-BAR ROW WITH HANDLE");
        middlebackExercises.add("PENDLAY ROWN");

        ArrayList<String> lowerbackExercises = new ArrayList<>();
        lowerbackExercises.add("ATLAS STONES");
        lowerbackExercises.add("DEFICIT DEADLIFT");

        ArrayList<String> glutesExercises = new ArrayList<>();
        glutesExercises.add("BARBELL GLUTE BRIDGE");
        glutesExercises.add("BARBELL HIP THRUST");

        ArrayList<String> hamstringsExercises = new ArrayList<>();
        hamstringsExercises.add("CLEAN DEADLIFT");
        hamstringsExercises.add("HANG SNATCH");

        exercises = new HashMap<>();
        exercises.put(MuscleGroup.NECK, neckExercises);
        exercises.put(MuscleGroup.TRAPS, trapsExercises);
        exercises.put(MuscleGroup.SHOULDERS, shouldersExercises);
        exercises.put(MuscleGroup.CHEST, chestExercises);
        exercises.put(MuscleGroup.BICEPS, bicepsExercises);
        exercises.put(MuscleGroup.FOREARMS, forearmsExercises);
        exercises.put(MuscleGroup.ABS, absExercises);
        exercises.put(MuscleGroup.QUADS, quadsExercises);
        exercises.put(MuscleGroup.CALVES, calvesExercises);
        exercises.put(MuscleGroup.LATS, latsExercises);
        exercises.put(MuscleGroup.TRICEPS, tricepsExercises);
        exercises.put(MuscleGroup.MIDDLE_BACK, middlebackExercises);
        exercises.put(MuscleGroup.LOWER_BACK, lowerbackExercises);
        exercises.put(MuscleGroup.GLUTES, glutesExercises);
        exercises.put(MuscleGroup.HAMSTRINGS, hamstringsExercises);

        ArrayList<HashMap<String, String>> groupExercisePairs = new ArrayList<>();

        for (MuscleGroup muscleGroup : selectedMuscleGroups) {
            ArrayList<String> exerciseGroup = exercises.get(muscleGroup);
            if (exerciseGroup != null) {
                for (String exercise : exerciseGroup) {
                    HashMap<String, String> groupExercisePair = new HashMap<>();
                    groupExercisePair.put("muscleGroup", muscleGroupNames.get(muscleGroup));
                    groupExercisePair.put("exercise", exercise);
                    groupExercisePairs.add(groupExercisePair);
                }
            }
        }

        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(this, groupExercisePairs);

        listViewExercises = (ListView) findViewById(R.id.exercises_list_view);
        listViewExercises.setAdapter(exerciseAdapter);

        buttonDone = (Button) findViewById(R.id.button_done);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            ArrayList<String> checkedExercises = new ArrayList<>();

            @Override
            public void onClick(View view) {
                int i;
                for (i = 0; i < listViewExercises.getChildCount(); i++) {
                    View cardView = listViewExercises.getChildAt(i);
                    CheckBox checkBox =
                            (CheckBox) cardView.findViewById(R.id.list_view_checkbox);

                    if (checkBox.isChecked()) {
                        TextView textViewExercise =
                                (TextView) cardView.findViewById(R.id.list_view_exercise);
                        checkedExercises.add(textViewExercise.getText().toString());
                        Log.d(TAG, textViewExercise.getText().toString());
                    }
                }

                // Start the Results activity
                Intent intent = new Intent(view.getContext(), ResultsActivity.class);
                intent.putExtra("COMPLETED_EXERCISES", checkedExercises);
                startActivity(intent);
                finish();
            }
        });
    }

    private class ExerciseAdapter extends BaseAdapter {
        private ArrayList<HashMap<String, String>> groupExercisePairs;
        Activity activity;
        TextView exercise;
        TextView muscleGroup;

        public ExerciseAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {
            super();
            this.activity = activity;
            this.groupExercisePairs = list;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return groupExercisePairs.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return groupExercisePairs.get(position);
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

                muscleGroup = (TextView) cardView.findViewById(R.id.list_view_muscle_group);
                exercise = (TextView) cardView.findViewById(R.id.list_view_exercise);
            }

            HashMap<String, String> groupExercisePair = groupExercisePairs.get(position);
            muscleGroup.setText(groupExercisePair.get("muscleGroup"));
            exercise.setText(groupExercisePair.get("exercise"));

            return cardView;
        }
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
