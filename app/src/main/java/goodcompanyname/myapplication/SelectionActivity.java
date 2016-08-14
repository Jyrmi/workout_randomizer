package goodcompanyname.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class SelectionActivity extends AppCompatActivity {

    ImageView imageAbs;
    ImageView imageBase;
    ImageView imageBiceps;
    ImageView imageChest;
    ImageView imageForearms;
    ImageView imageFrontCalves;
    ImageView imageFrontHamstrings;
    ImageView imageFrontLats;
    ImageView imageFrontShoulders;
    ImageView imageFrontTraps;
    ImageView imageNeck;
    ImageView imageQuads;

    Button buttonAbsLower;
    Button buttonAbsUpper;
    Button buttonBiceps;
    Button buttonChest;
    Button buttonForearms;
    Button buttonFrontCalves;
    Button buttonFrontHamstrings;
    Button buttonFrontLatsLeft;
    Button buttonFrontLatsRight;
    Button buttonFrontShoulders;
    Button buttonFrontTraps;
    Button buttonNeck;
    Button buttonQuads;

    ArrayList<MuscleGroup> selectedMuscleGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        Button buttonGenerate = (Button) findViewById(R.id.button_generate_workout);
        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToWorkout();
            }
        });

        imageAbs = (ImageView) findViewById(R.id.image_abs);
        imageBase = (ImageView) findViewById(R.id.image_base);
        imageBiceps = (ImageView) findViewById(R.id.image_biceps);
        imageChest = (ImageView) findViewById(R.id.image_chest);
        imageForearms = (ImageView) findViewById(R.id.image_forearms);
        imageFrontCalves = (ImageView) findViewById(R.id.image_front_calves);
        imageFrontHamstrings = (ImageView) findViewById(R.id.image_front_hamstrings);
        imageFrontLats = (ImageView) findViewById(R.id.image_front_lats);
        imageFrontShoulders = (ImageView) findViewById(R.id.image_front_shoulders);
        imageFrontTraps = (ImageView) findViewById(R.id.image_front_traps);
        imageNeck = (ImageView) findViewById(R.id.image_neck);
        imageQuads = (ImageView) findViewById(R.id.image_quads);

        buttonAbsLower = (Button) findViewById(R.id.button_abs_lower);
        buttonAbsUpper = (Button) findViewById(R.id.button_abs_upper);
        buttonBiceps = (Button) findViewById(R.id.button_biceps);
        buttonChest = (Button) findViewById(R.id.button_chest);
        buttonForearms = (Button) findViewById(R.id.button_forearms);
        buttonFrontCalves = (Button) findViewById(R.id.button_front_calves);
        buttonFrontHamstrings = (Button) findViewById(R.id.button_front_hamstrings);
        buttonFrontLatsLeft = (Button) findViewById(R.id.button_front_lats_left);
        buttonFrontLatsRight = (Button) findViewById(R.id.button_front_lats_right);
        buttonFrontShoulders = (Button) findViewById(R.id.button_front_shoulders);
        buttonFrontTraps = (Button) findViewById(R.id.button_front_traps);
        buttonNeck = (Button) findViewById(R.id.button_neck);
        buttonQuads = (Button) findViewById(R.id.button_quads);

        selectedMuscleGroups = new ArrayList<MuscleGroup>();
        setButtonListener(buttonAbsLower, MuscleGroup.ABS, imageAbs,
                R.drawable.abs, R.drawable.abs_colorized);
        setButtonListener(buttonAbsUpper, MuscleGroup.ABS, imageAbs,
                R.drawable.abs, R.drawable.abs_colorized);
        setButtonListener(buttonBiceps, MuscleGroup.BICEPS, imageBiceps,
                R.drawable.biceps, R.drawable.biceps_colorized);
        setButtonListener(buttonChest, MuscleGroup.CHEST, imageChest,
                R.drawable.chest, R.drawable.chest_colorized);
        setButtonListener(buttonForearms, MuscleGroup.FOREARMS, imageForearms,
                R.drawable.forearms, R.drawable.forearms_colorized);
        setButtonListener(buttonFrontCalves, MuscleGroup.CALVES, imageFrontCalves,
                R.drawable.front_calves, R.drawable.front_calves_colorized);
        setButtonListener(buttonFrontHamstrings, MuscleGroup.HAMSTRINGS, imageFrontHamstrings,
                R.drawable.front_hamstrings, R.drawable.front_hamstrings_colorized);
        setButtonListener(buttonFrontLatsLeft, MuscleGroup.LATS, imageFrontLats,
                R.drawable.front_lats, R.drawable.front_lats_colorized);
        setButtonListener(buttonFrontLatsRight, MuscleGroup.LATS, imageFrontLats,
                R.drawable.front_lats, R.drawable.front_lats_colorized);
        setButtonListener(buttonFrontShoulders, MuscleGroup.SHOULDERS, imageFrontShoulders,
                R.drawable.front_shoulders, R.drawable.front_shoulders_colorized);
        setButtonListener(buttonFrontTraps, MuscleGroup.TRAPS, imageFrontTraps,
                R.drawable.front_traps, R.drawable.front_traps_colorized);
        setButtonListener(buttonNeck, MuscleGroup.NECK, imageNeck,
                R.drawable.neck, R.drawable.neck_colorized);
        setButtonListener(buttonQuads, MuscleGroup.QUADS, imageQuads,
                R.drawable.quads, R.drawable.quads_colorized);
    }

    /**
     * Sets listeners for this activity's buttons.
     * @param button the button.
     * @param imageView the ImageView associated with the button.
     * @param imageViewAssetoffResId the resource id (ex. R.drawable.abs resolves to an int) of the
     *                               image resource in it's 'off' state.
     * @param imageViewAssetOnResId opposite of above.
     */
    private void setButtonListener(Button button, final MuscleGroup muscleGroup,
                                   final ImageView imageView, final int imageViewAssetoffResId,
                                   final int imageViewAssetOnResId) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedMuscleGroups.contains(muscleGroup)) {
                    selectedMuscleGroups.remove(muscleGroup);
                    imageView.setImageResource(imageViewAssetoffResId);
                } else {
                    selectedMuscleGroups.add(muscleGroup);
                    imageView.setImageResource(imageViewAssetOnResId);
                }
            }
        });
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
    public void navigateToWorkout() {
        // Do something in response to button
        Intent intent = new Intent(this, WorkoutActivity.class);
        intent.putExtra("EXTRA_MUSCLE_GROUPS", selectedMuscleGroups);
        startActivity(intent);
        finish();
    }
}
