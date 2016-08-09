package goodcompanyname.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;


public class WorkoutActivity extends AppCompatActivity {

    private static final String TAG = "WorkoutActivity";

    private Button buttonDone;

    private ArrayList<String> exercisesList;
    private ArrayAdapter<String> exercisesAdapter;

    // A list to display picked items
    private ArrayList<String> muscleGroups = new ArrayList<String>();
    private HashMap<String, ArrayList<String>> exercises = new HashMap<String, ArrayList<String>>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        exercisesList = new ArrayList<String>();

        Intent intent = getIntent();
        muscleGroups = intent.getStringArrayListExtra("EXTRA_MUSCLE_GROUPS");

//        ArrayList<String> neckExercises = new ArrayList<String>();
//        neckExercises.add("NECK A");
//        neckExercises.add("NECK B");
//
//        ArrayList<String> trapsExercises = new ArrayList<String>();
//        trapsExercises.add("TRAPS A");
//        trapsExercises.add("LEVERAGE SHRUG");
//
//        ArrayList<String> shoulderExercises = new ArrayList<String>();
//        shoulderExercises.add("CLEAN AND PRESS");
//        shoulderExercises.add("SHOULDERS B");
//
//        ArrayList<String> chestExercises = new ArrayList<String>();
//        chestExercises.add("PUSHUPS");
//        chestExercises.add("CHEST B");
//
//        ArrayList<String> bicepsExercises = new ArrayList<String>();
//        bicepsExercises.add("bicep curl 1");
//        bicepsExercises.add("bicep curl 2");
//
//        ArrayList<String> forearmExercises = new ArrayList<String>();
//        forearmExercises.add("climb stuff 1");
//        forearmExercises.add("climb moar stuff");
//
//        ArrayList<String> latsExercises = new ArrayList<String>();
//        latsExercises.add("lat ex 1");
//        latsExercises.add("lat ex 2");
//
//        ArrayList<String> tricepsExercises = new ArrayList<String>();
//        tricepsExercises.add("tri ex 1");
//        tricepsExercises.add("tri ex 2");
//
//        ArrayList<String> middlebackExercises = new ArrayList<String>();
//        middlebackExercises.add("mid back 1");
//        middlebackExercises.add("mid back 2");
//
//        ArrayList<String> lowerbackExercises = new ArrayList<String>();
//        lowerbackExercises.add("low back 1");
//        lowerbackExercises.add("low back 2");
//
//        ArrayList<String> glutesExercises = new ArrayList<String>();
//        glutesExercises.add("glutes 1");
//        glutesExercises.add("glutes 2");
//
//        exercises.put("Neck", neckExercises);
//        exercises.put("Traps", trapsExercises);
//        exercises.put("Shoulders", shoulderExercises);
//        exercises.put("Chest", chestExercises);
//        exercises.put("Biceps", bicepsExercises);
//        exercises.put("Forearm", forearmExercises);
//        exercises.put("Lats", latsExercises);
//        exercises.put("Triceps", tricepsExercises);
//        exercises.put("Middle Back", middlebackExercises);
//        exercises.put("Lower Back", lowerbackExercises);
//        exercises.put("Glutes", glutesExercises);
//
//        // Add exercises to the list of exercises TextView
//        for (String muscleGroup : muscleGroups) {
//            ArrayList<String> exerciseGroup = exercises.get(muscleGroup);
//            if (exerciseGroup != null) {
//                for (String exercise : exerciseGroup) {
//                    exercisesList.add(exercise);
//                }
//            }
//        }

        exercisesAdapter = new ArrayAdapter<String>(this, R.layout.card_layout,
                R.id.list_exercises_item, exercisesList);
        final ListView listViewExercises = (ListView) findViewById(R.id.exercises_list_view);
        listViewExercises.setAdapter(exercisesAdapter);

        buttonDone = (Button) findViewById(R.id.button_done);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0;
                for (i = 0; i < listViewExercises.getChildCount(); i++) {
                    View cardView = listViewExercises.getChildAt(i);
                    CheckBox checkBox =
                            (CheckBox) cardView.findViewById(R.id.list_exercises_item_checkbox);

                    if (checkBox.isChecked()) {
                        TextView textViewExercise =
                                (TextView) cardView.findViewById(R.id.list_exercises_item);
                        Log.d(TAG, textViewExercise.getText().toString());
                    }
                }
            }
        });
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



