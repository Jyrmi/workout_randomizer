package goodcompanyname.myapplication;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectionActivity extends AppCompatActivity {

    private static final String TAG = "SelectionActivity";

    ImageView imageGroupA;
    ImageView imageGroupB;
    ImageView imageGroupC;
    ImageView imageGroupD;

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

    Button buttonRearTraps;
    Button buttonRearShoulders;
    Button buttonTriceps;
    Button buttonRearForearms;
    Button buttonMiddleBack;
    Button buttonLowerBack;
    Button buttonGlutes;
    Button buttonRearHamstrings;
    Button buttonRearCalves;

    ImageButton buttonTurn;

    Boolean forward;
    ArrayList<MuscleGroup> selectedMuscleGroups;
    ArrayList<Button> frontButtons;
    ArrayList<Button> rearButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "begin onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        forward = true;
        selectedMuscleGroups = new ArrayList();

        imageGroupA = (ImageView) findViewById(R.id.image_group_a);
        imageGroupB = (ImageView) findViewById(R.id.image_group_b);
        imageGroupC = (ImageView) findViewById(R.id.image_group_c);
        imageGroupD = (ImageView) findViewById(R.id.image_group_d);

        Log.d(TAG, "all image groups found");

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

        Log.d(TAG, "all front buttons found");

        buttonRearTraps = (Button) findViewById(R.id.button_rear_traps);
        buttonRearShoulders = (Button) findViewById(R.id.button_rear_shoulders);
        buttonTriceps = (Button) findViewById(R.id.button_triceps);
        buttonRearForearms = (Button) findViewById(R.id.button_rear_forearms);
        buttonMiddleBack = (Button) findViewById(R.id.button_middle_back);
        buttonLowerBack = (Button) findViewById(R.id.button_lower_back);
        buttonGlutes = (Button) findViewById(R.id.button_glutes);
        buttonRearHamstrings = (Button) findViewById(R.id.button_rear_hamstrings);
        buttonRearCalves = (Button) findViewById(R.id.button_rear_calves);

        Log.d(TAG, "all rear buttons found");


        selectedMuscleGroups = new ArrayList<>();

        setButtonListenerFront(buttonAbsLower, MuscleGroup.ABS, imageGroupC);
        setButtonListenerFront(buttonAbsUpper, MuscleGroup.ABS, imageGroupC);
        setButtonListenerFront(buttonBiceps, MuscleGroup.BICEPS, imageGroupB);
        setButtonListenerFront(buttonChest, MuscleGroup.CHEST, imageGroupA);
        setButtonListenerFront(buttonForearms, MuscleGroup.FOREARMS, imageGroupB);
        setButtonListenerFront(buttonFrontCalves, MuscleGroup.CALVES, imageGroupD);
        setButtonListenerFront(buttonFrontHamstrings, MuscleGroup.HAMSTRINGS, imageGroupC);
        setButtonListenerFront(buttonFrontLatsLeft, MuscleGroup.LATS, imageGroupC);
        setButtonListenerFront(buttonFrontLatsRight, MuscleGroup.LATS, imageGroupC);
        setButtonListenerFront(buttonFrontShoulders, MuscleGroup.SHOULDERS, imageGroupB);
        setButtonListenerFront(buttonFrontTraps, MuscleGroup.TRAPS, imageGroupA);
        setButtonListenerFront(buttonNeck, MuscleGroup.NECK, imageGroupA);
        setButtonListenerFront(buttonQuads, MuscleGroup.QUADS, imageGroupD);

        setButtonListenerRear(buttonRearTraps, MuscleGroup.TRAPS, imageGroupA);
        setButtonListenerRear(buttonRearShoulders, MuscleGroup.SHOULDERS, imageGroupA);
        setButtonListenerRear(buttonTriceps, MuscleGroup.TRICEPS, imageGroupB);
        setButtonListenerRear(buttonRearForearms, MuscleGroup.FOREARMS, imageGroupB);
        setButtonListenerRear(buttonMiddleBack, MuscleGroup.MIDDLE_BACK, imageGroupC);
        setButtonListenerRear(buttonLowerBack, MuscleGroup.LOWER_BACK, imageGroupC);
        setButtonListenerRear(buttonGlutes, MuscleGroup.GLUTES, imageGroupD);
        setButtonListenerRear(buttonRearHamstrings, MuscleGroup.HAMSTRINGS, imageGroupD);
        setButtonListenerRear(buttonRearCalves, MuscleGroup.CALVES, imageGroupD);

        Log.d(TAG, "all button listeners set");

        frontButtons = new ArrayList();
        rearButtons = new ArrayList();

        frontButtons.add(buttonAbsLower);
        frontButtons.add(buttonAbsUpper);
        frontButtons.add(buttonBiceps);
        frontButtons.add(buttonChest);
        frontButtons.add(buttonForearms);
        frontButtons.add(buttonFrontCalves);
        frontButtons.add(buttonFrontHamstrings);
        frontButtons.add(buttonFrontLatsLeft);
        frontButtons.add(buttonFrontLatsRight);
        frontButtons.add(buttonFrontShoulders);
        frontButtons.add(buttonFrontTraps);
        frontButtons.add(buttonNeck);
        frontButtons.add(buttonQuads);

        rearButtons.add(buttonRearTraps);
        rearButtons.add(buttonRearShoulders);
        rearButtons.add(buttonTriceps);
        rearButtons.add(buttonRearForearms);
        rearButtons.add(buttonMiddleBack);
        rearButtons.add(buttonLowerBack);
        rearButtons.add(buttonGlutes);
        rearButtons.add(buttonRearHamstrings);
        rearButtons.add(buttonRearCalves);

        toggleButtonsVisibility(frontButtons, View.VISIBLE);

        buttonTurn = (ImageButton) findViewById(R.id.button_turn_body);
        buttonTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnBody();
            }
        });

        Button buttonGenerate = (Button) findViewById(R.id.button_generate);
        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedMuscleGroups.isEmpty()) {
                    toastInvalidSelection();
                } else {
                    navigateToWorkout();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Turns the body around by changing image assets.
     */
    public void turnBody() {
        if (forward) { // Turn to the back
            forward = false; // Switch parity of forward facing boolean
            setGroupImageResourceRear(imageGroupA); // Update image resources
            setGroupImageResourceRear(imageGroupB);
            setGroupImageResourceRear(imageGroupC);
            setGroupImageResourceRear(imageGroupD);
            toggleButtonsVisibility(frontButtons, View.INVISIBLE); // Hide and reveal buttons
            toggleButtonsVisibility(rearButtons, View.VISIBLE);
        } else { // Turn to the front
            forward = true; // Switch parity of forward facing boolean
            setGroupImageResourceFront(imageGroupA); // Update image resources
            setGroupImageResourceFront(imageGroupB);
            setGroupImageResourceFront(imageGroupC);
            setGroupImageResourceFront(imageGroupD);
            toggleButtonsVisibility(frontButtons, View.VISIBLE); // Hide and reveal buttons
            toggleButtonsVisibility(rearButtons, View.INVISIBLE);
        }
    }

    public void toggleButtonsVisibility(ArrayList<Button> list, int visibility) {
        for (Button button : list) {
            button.setVisibility(visibility);
        }
    }

    /**
     * Sets listeners for this activity's buttons.
     * @param button the button.
     * @param imageView the ImageView associated with the button.
     */
    private void setButtonListenerFront(Button button, final MuscleGroup muscleGroup,
                                        final ImageView imageView) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedMuscleGroups.contains(muscleGroup)) {
                    selectedMuscleGroups.remove(muscleGroup);
                } else {
                    selectedMuscleGroups.add(muscleGroup);
                }

                setGroupImageResourceFront(imageView);
            }
        });
    }

    /**
     * Sets listeners for this activity's buttons.
     * @param button the button.
     * @param imageView the ImageView associated with the button.
     */
    private void setButtonListenerRear(Button button, final MuscleGroup muscleGroup,
                                       final ImageView imageView) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedMuscleGroups.contains(muscleGroup)) {
                    selectedMuscleGroups.remove(muscleGroup);
                } else {
                    selectedMuscleGroups.add(muscleGroup);
                }

                setGroupImageResourceRear(imageView);
            }
        });
    }

    private void setGroupImageResourceFront(ImageView imageView) {
        if (imageView == imageGroupA) {
            if (selectedMuscleGroups.contains(MuscleGroup.NECK) &&
                    selectedMuscleGroups.contains(MuscleGroup.TRAPS) &&
                    selectedMuscleGroups.contains(MuscleGroup.CHEST)) {
                imageView.setImageResource(R.drawable.a_all);
            } else if (selectedMuscleGroups.contains(MuscleGroup.NECK) &&
                    selectedMuscleGroups.contains(MuscleGroup.TRAPS)) {
                imageView.setImageResource(R.drawable.a_neck_traps);
            } else if (selectedMuscleGroups.contains(MuscleGroup.NECK) &&
                    selectedMuscleGroups.contains(MuscleGroup.CHEST)) {
                imageView.setImageResource(R.drawable.a_neck_chest);
            } else if (selectedMuscleGroups.contains(MuscleGroup.TRAPS) &&
                    selectedMuscleGroups.contains(MuscleGroup.CHEST)) {
                imageView.setImageResource(R.drawable.a_traps_chest);
            } else if (selectedMuscleGroups.contains(MuscleGroup.NECK)) {
                imageView.setImageResource(R.drawable.a_neck);
            } else if (selectedMuscleGroups.contains(MuscleGroup.TRAPS)) {
                imageView.setImageResource(R.drawable.a_traps);
            } else if (selectedMuscleGroups.contains(MuscleGroup.CHEST)) {
                imageView.setImageResource(R.drawable.a_chest);
            } else {
                imageView.setImageResource(R.drawable.a_none);
            }
        } else if (imageView == imageGroupB) {
            if (selectedMuscleGroups.contains(MuscleGroup.SHOULDERS) &&
                    selectedMuscleGroups.contains(MuscleGroup.BICEPS) &&
                    selectedMuscleGroups.contains(MuscleGroup.FOREARMS)) {
                imageView.setImageResource(R.drawable.b_all);
            } else if (selectedMuscleGroups.contains(MuscleGroup.SHOULDERS) &&
                    selectedMuscleGroups.contains(MuscleGroup.BICEPS)) {
                imageView.setImageResource(R.drawable.b_shoulders_biceps);
            } else if (selectedMuscleGroups.contains(MuscleGroup.SHOULDERS) &&
                    selectedMuscleGroups.contains(MuscleGroup.FOREARMS)) {
                imageView.setImageResource(R.drawable.b_shoulders_forearms);
            } else if (selectedMuscleGroups.contains(MuscleGroup.BICEPS) &&
                    selectedMuscleGroups.contains(MuscleGroup.FOREARMS)) {
                imageView.setImageResource(R.drawable.b_biceps_forearms);
            } else if (selectedMuscleGroups.contains(MuscleGroup.SHOULDERS)) {
                imageView.setImageResource(R.drawable.b_shoulders);
            } else if (selectedMuscleGroups.contains(MuscleGroup.BICEPS)) {
                imageView.setImageResource(R.drawable.b_biceps);
            } else if (selectedMuscleGroups.contains(MuscleGroup.FOREARMS)) {
                imageView.setImageResource(R.drawable.b_forearms);
            } else {
                imageView.setImageResource(R.drawable.b_none);
            }
        } else if (imageView == imageGroupC) {
            if (selectedMuscleGroups.contains(MuscleGroup.LATS) &&
                    selectedMuscleGroups.contains(MuscleGroup.ABS) &&
                    selectedMuscleGroups.contains(MuscleGroup.HAMSTRINGS)) {
                imageView.setImageResource(R.drawable.c_all);
            } else if (selectedMuscleGroups.contains(MuscleGroup.LATS) &&
                    selectedMuscleGroups.contains(MuscleGroup.ABS)) {
                imageView.setImageResource(R.drawable.c_lats_abs);
            } else if (selectedMuscleGroups.contains(MuscleGroup.LATS) &&
                    selectedMuscleGroups.contains(MuscleGroup.HAMSTRINGS)) {
                imageView.setImageResource(R.drawable.c_lats_hamstrings);
            } else if (selectedMuscleGroups.contains(MuscleGroup.ABS) &&
                    selectedMuscleGroups.contains(MuscleGroup.HAMSTRINGS)) {
                imageView.setImageResource(R.drawable.c_abs_hamstrings);
            } else if (selectedMuscleGroups.contains(MuscleGroup.LATS)) {
                imageView.setImageResource(R.drawable.c_lats);
            } else if (selectedMuscleGroups.contains(MuscleGroup.ABS)) {
                imageView.setImageResource(R.drawable.c_abs);
            } else if (selectedMuscleGroups.contains(MuscleGroup.HAMSTRINGS)) {
                imageView.setImageResource(R.drawable.c_hamstrings);
            } else {
                imageView.setImageResource(R.drawable.c_none);
            }
        } else if (imageView == imageGroupD) {
            if (selectedMuscleGroups.contains(MuscleGroup.QUADS) &&
                    selectedMuscleGroups.contains(MuscleGroup.CALVES)) {
                imageView.setImageResource(R.drawable.d_all);
            } else if (selectedMuscleGroups.contains(MuscleGroup.QUADS)) {
                imageView.setImageResource(R.drawable.d_quads);
            } else if (selectedMuscleGroups.contains(MuscleGroup.CALVES)) {
                imageView.setImageResource(R.drawable.d_calves);
            } else {
                imageView.setImageResource(R.drawable.d_none);
            }
        }
    }

    private void setGroupImageResourceRear(ImageView imageView) {
        if (imageView == imageGroupA) {
            if (selectedMuscleGroups.contains(MuscleGroup.TRAPS) &&
                    selectedMuscleGroups.contains(MuscleGroup.SHOULDERS)) {
                imageView.setImageResource(R.drawable.e_all);
            } else if (selectedMuscleGroups.contains(MuscleGroup.TRAPS)) {
                imageView.setImageResource(R.drawable.e_traps);
            } else if (selectedMuscleGroups.contains(MuscleGroup.SHOULDERS)) {
                imageView.setImageResource(R.drawable.e_shoulders);
            } else {
                imageView.setImageResource(R.drawable.e_none);
            }
        } else if (imageView == imageGroupB) {
            if (selectedMuscleGroups.contains(MuscleGroup.TRICEPS) &&
                    selectedMuscleGroups.contains(MuscleGroup.FOREARMS)) {
                imageView.setImageResource(R.drawable.f_all);
            } else if (selectedMuscleGroups.contains(MuscleGroup.TRICEPS)) {
                imageView.setImageResource(R.drawable.f_triceps);
            } else if (selectedMuscleGroups.contains(MuscleGroup.FOREARMS)) {
                imageView.setImageResource(R.drawable.f_forearms);
            } else {
                imageView.setImageResource(R.drawable.f_none);
            }
        } else if (imageView == imageGroupC) {
            if (selectedMuscleGroups.contains(MuscleGroup.MIDDLE_BACK) &&
                    selectedMuscleGroups.contains(MuscleGroup.LOWER_BACK)) {
                imageView.setImageResource(R.drawable.g_all);
            } else if (selectedMuscleGroups.contains(MuscleGroup.MIDDLE_BACK)) {
                imageView.setImageResource(R.drawable.g_middle_back);
            } else if (selectedMuscleGroups.contains(MuscleGroup.LOWER_BACK)) {
                imageView.setImageResource(R.drawable.g_lower_back);
            } else {
                imageView.setImageResource(R.drawable.g_none);
            }
        } else if (imageView == imageGroupD) {
            if (selectedMuscleGroups.contains(MuscleGroup.GLUTES) &&
                    selectedMuscleGroups.contains(MuscleGroup.HAMSTRINGS) &&
                    selectedMuscleGroups.contains(MuscleGroup.CALVES)) {
                imageView.setImageResource(R.drawable.h_all);
            } else if (selectedMuscleGroups.contains(MuscleGroup.GLUTES) &&
                    selectedMuscleGroups.contains(MuscleGroup.HAMSTRINGS)) {
                imageView.setImageResource(R.drawable.h_glutes_hamstrings);
            } else if (selectedMuscleGroups.contains(MuscleGroup.GLUTES) &&
                    selectedMuscleGroups.contains(MuscleGroup.CALVES)) {
                imageView.setImageResource(R.drawable.h_glutes_calves);
            } else if (selectedMuscleGroups.contains(MuscleGroup.HAMSTRINGS) &&
                    selectedMuscleGroups.contains(MuscleGroup.CALVES)) {
                imageView.setImageResource(R.drawable.h_hamstrings_calves);
            } else if (selectedMuscleGroups.contains(MuscleGroup.GLUTES)) {
                imageView.setImageResource(R.drawable.h_glutes);
            } else if (selectedMuscleGroups.contains(MuscleGroup.HAMSTRINGS)) {
                imageView.setImageResource(R.drawable.h_hamstrings);
            } else if (selectedMuscleGroups.contains(MuscleGroup.CALVES)) {
                imageView.setImageResource(R.drawable.h_calves);
            } else {
                imageView.setImageResource(R.drawable.h_none);
            }
        }
    }

    /** Called when the user clicks the Send button */
    public void navigateToWorkout() {
        // Do something in response to button
        Intent intent = new Intent(this, WorkoutActivity.class);
        intent.putExtra("EXTRA_MUSCLE_GROUPS", selectedMuscleGroups);
        startActivity(intent);
    }

    /** Called when the user clicks the Send button */
    public void toastInvalidSelection() {
        Toast.makeText(this, R.string.invalid_selection, Toast.LENGTH_SHORT).show();
    }
}
