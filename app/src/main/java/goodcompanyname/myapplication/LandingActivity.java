package goodcompanyname.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;



public class LandingActivity extends ActionBarActivity {

    // A list to display picked items
    private ArrayList<String> muscleGroups = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Button button_neck = (Button) findViewById(R.id.button_neck);
        Button button_traps = (Button) findViewById(R.id.button_traps);
        Button button_shoulders = (Button) findViewById(R.id.button_shoulders);
        Button button_chest = (Button) findViewById(R.id.button_chest);
        Button button_biceps = (Button) findViewById(R.id.button_biceps);
        Button button_forearm = (Button) findViewById(R.id.button_forearm);

        setButtonListener(button_neck, "Neck");
        setButtonListener(button_traps, "Traps");
        setButtonListener(button_shoulders, "Shoulders");
        setButtonListener(button_chest, "Chest");
        setButtonListener(button_biceps, "Biceps");
        setButtonListener(button_forearm, "Forearm");

        Button button_login = (Button) findViewById(R.id.button_generate_workout);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToWorkout();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landing, menu);
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

    /** Called when the user clicks the Send button */
    public void setButtonListener(Button button, final String bodyPart) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMuscleGroup(bodyPart);
            }
        });
    }

    /** Called when the user clicks the Send button */
    public void addMuscleGroup(String muscleGroup) {
        // Add the muscle group to the muscleGroups array
        if (muscleGroups != null && !muscleGroups.contains(muscleGroup)) {
            muscleGroups.add(muscleGroup);
        }

        updateTextView();
    }

    public void updateTextView() {
        TextView muscleGroupList = (TextView) findViewById(R.id.list_muscle_group);
        muscleGroupList.setText(muscleGroups.toString());
    }

    /** Called when the user clicks the Send button */
    public void navigateToWorkout() {
        // Do something in response to button
        Intent intent = new Intent(this, ActivityWorkout.class);
        intent.putExtra("EXTRA_MUSCLE_GROUPS", muscleGroups);
        startActivity(intent);
    }
}
