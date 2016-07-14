package goodcompanyname.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;


public class ActivityWorkout extends ActionBarActivity {

    private ArrayList<String> exercisesList;
    private ArrayAdapter<String> arrayAdapter;
    private int i;



    // A list to display picked items
    private ArrayList<String> muscleGroups = new ArrayList<String>();
    private HashMap<String, ArrayList<String>> exercises = new HashMap<String, ArrayList<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);




        //add the view via xml or programmatically
        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        exercisesList = new ArrayList<String>();

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

        ArrayList<String> bicepsExercises = new ArrayList<String>();
        bicepsExercises.add("bicep curl 1");
        bicepsExercises.add("bicep curl 2");

        ArrayList<String> forearmExercises = new ArrayList<String>();
        forearmExercises.add("climb stuff 1");
        forearmExercises.add("climb moar stuff");

        ArrayList<String> latsExercises = new ArrayList<String>();
        latsExercises.add("lat ex 1");
        latsExercises.add("lat ex 2");

        ArrayList<String> tricepsExercises = new ArrayList<String>();
        tricepsExercises.add("tri ex 1");
        tricepsExercises.add("tri ex 2");

        exercises.put("Neck", neckExercises);
        exercises.put("Traps", trapsExercises);
        exercises.put("Shoulders", shoulderExercises);
        exercises.put("Chest", chestExercises);
        exercises.put("Biceps", bicepsExercises);
        exercises.put("Forearm", forearmExercises);
        exercises.put("Lats", latsExercises);
        exercises.put("Triceps", tricepsExercises);

        // Add exercises to the list of exercises TextView
        for (String muscleGroup : muscleGroups) {
            ArrayList<String> exerciseGroup = exercises.get(muscleGroup);
            if (exerciseGroup != null){
                for (String exercise : exerciseGroup) {
                    exercisesList.add(exercise);
                }
            }
        }
        //choose your favorite adapter
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.item, R.id.helloText, exercisesList );

        //set the listener and the adapter
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {

            @Override
            public void onScroll(float randomNumber) {
                //this is what runs when you click the button
            }

            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                exercisesList.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(ActivityWorkout.this, "Left!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(ActivityWorkout.this, "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here

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



