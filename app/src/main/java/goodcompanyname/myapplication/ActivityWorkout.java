package goodcompanyname.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class ActivityWorkout extends ActionBarActivity {

    // A list to display picked items
    private ArrayList<String> muscleGroups = new ArrayList<String>();
    private HashMap<String, ArrayList<String>> exercises = new HashMap<String, ArrayList<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        Intent intent = getIntent();
        muscleGroups = intent.getStringArrayListExtra("EXTRA_MUSCLE_GROUPS");

        ArrayList<String> neckExercises = new ArrayList<String>();
        neckExercises.add("NECK A");
        neckExercises.add("NECK B");

        ArrayList<String> trapsExercises = new ArrayList<String>();
        trapsExercises.add("TRAPS A");
        trapsExercises.add("LEVERAGE SHRUG");

        ArrayList<String> shoulderExercises = new ArrayList<String>();
        shoulderExercises.add("CLEAN AND PRESS");
        shoulderExercises.add("SHOULDERS B");

        ArrayList<String> chestExercises = new ArrayList<String>();
        chestExercises.add("PUSHUPS");
        chestExercises.add("CHEST B");

        exercises.put("Neck", neckExercises);
        exercises.put("Traps", trapsExercises);
        exercises.put("Shoulders", shoulderExercises);
        exercises.put("Chest", chestExercises);

        TextView exercisesList = (TextView) findViewById(R.id.list_exercises);

        // Add exercises to the list of exercises TextView
        for (String muscleGroup : muscleGroups) {
            ArrayList<String> exerciseGroup = exercises.get(muscleGroup);
            if (exerciseGroup != null){
                exercisesList.append(exercises.get(muscleGroup).toString());
            }
        }

        // DEBUG
//        System.out.println(muscleGroups);
//        for (int i = 0; i < muscleGroups.size(); i++) {
//            System.out.println(exercises.get(muscleGroups.get(i)));
//        }
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
