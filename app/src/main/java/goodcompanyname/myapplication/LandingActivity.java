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
import java.util.HashSet;

public class LandingActivity extends AppCompatActivity {

    ImageView imageAbs;
    ImageView imageBase;
    ImageView imageBiceps;
    ImageView imageChest;
    ImageView imageForearms;
    ImageView imageFrontCalves;
    ImageView imageFrontHamstrings;
    ImageView imageFrontShoulders;
    ImageView imageFrontTraps;
    ImageView imageNeck;
    ImageView imageQuads;

    Button buttonAbsLower;
    Button buttonAbsUpper;
    Button buttonBicepsLeft;
    Button buttonBicepsRight;
    Button buttonChest;
    Button buttonForearmsLeft;
    Button buttonForearmsRight;
    Button buttonFrontCalves;
    Button buttonFrontHamstrings;
    Button buttonFrontShouldersLeft;
    Button buttonFrontShouldersRight;
    Button buttonFrontTrapsLeft;
    Button buttonFrontTrapsRight;
    Button buttonNeck;
    Button buttonQuadsLeft;
    Button buttonQuadsMiddle;
    Button buttonQuadsRight;

    ArrayList<MuscleGroup> selectedMuscleGroups;
    RequestQueue queue;
    String url;

    // A list to display picked items
    private HashSet<MuscleGroup> muscleGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        imageAbs = (ImageView) findViewById(R.id.image_abs);
        imageBase = (ImageView) findViewById(R.id.image_base);
        imageBiceps = (ImageView) findViewById(R.id.image_biceps);
        imageChest = (ImageView) findViewById(R.id.image_chest);
        imageForearms = (ImageView) findViewById(R.id.image_forearms);
        imageFrontCalves = (ImageView) findViewById(R.id.image_front_calves);
        imageFrontHamstrings = (ImageView) findViewById(R.id.image_front_hamstrings);
        imageFrontShoulders = (ImageView) findViewById(R.id.image_front_shoulders);
        imageFrontTraps = (ImageView) findViewById(R.id.image_front_traps);
        imageNeck = (ImageView) findViewById(R.id.image_neck);
        imageQuads = (ImageView) findViewById(R.id.image_quads);

        buttonAbsLower = (Button) findViewById(R.id.button_abs_lower);
        buttonAbsUpper = (Button) findViewById(R.id.button_abs_upper);
        buttonBicepsLeft = (Button) findViewById(R.id.button_biceps_left);
        buttonBicepsRight = (Button) findViewById(R.id.button_biceps_right);
        buttonChest = (Button) findViewById(R.id.button_chest);
        buttonForearmsLeft = (Button) findViewById(R.id.button_forearms_left);
        buttonForearmsRight = (Button) findViewById(R.id.button_forearms_right);
        buttonFrontCalves = (Button) findViewById(R.id.button_front_calves);
        buttonFrontHamstrings = (Button) findViewById(R.id.button_front_hamstrings);
        buttonFrontShouldersLeft = (Button) findViewById(R.id.button_front_shoulders_left);
        buttonFrontShouldersRight = (Button) findViewById(R.id.button_front_shoulders_right);
        buttonFrontTrapsLeft = (Button) findViewById(R.id.button_front_traps_left);
        buttonFrontTrapsRight = (Button) findViewById(R.id.button_front_traps_right);
        buttonNeck = (Button) findViewById(R.id.button_neck);
        buttonQuadsLeft = (Button) findViewById(R.id.button_quads_left);
        buttonQuadsMiddle = (Button) findViewById(R.id.button_quads_middle);
        buttonQuadsRight = (Button) findViewById(R.id.button_quads_right);

        selectedMuscleGroups = new ArrayList<MuscleGroup>();
        setButtonListener(buttonAbsLower, MuscleGroup.ABS, imageAbs,
                R.drawable.abs, R.drawable.abs_colorized);
        setButtonListener(buttonAbsUpper, MuscleGroup.ABS, imageAbs,
                R.drawable.abs, R.drawable.abs_colorized);
        setButtonListener(buttonBicepsLeft, MuscleGroup.BICEPS, imageBiceps,
                R.drawable.biceps, R.drawable.biceps_colorized);
        setButtonListener(buttonBicepsRight, MuscleGroup.BICEPS, imageBiceps,
                R.drawable.biceps, R.drawable.biceps_colorized);
        setButtonListener(buttonChest, MuscleGroup.CHEST, imageChest,
                R.drawable.chest, R.drawable.chest_colorized);
        setButtonListener(buttonForearmsLeft, MuscleGroup.FOREARMS, imageForearms,
                R.drawable.forearms, R.drawable.forearms_colorized);
        setButtonListener(buttonForearmsRight, MuscleGroup.FOREARMS, imageForearms,
                R.drawable.forearms, R.drawable.forearms_colorized);
        setButtonListener(buttonFrontCalves, MuscleGroup.CALVES, imageFrontCalves,
                R.drawable.front_calves, R.drawable.front_calves_colorized);
        setButtonListener(buttonFrontHamstrings, MuscleGroup.HAMSTRINGS, imageFrontHamstrings,
                R.drawable.front_hamstrings, R.drawable.front_hamstrings_colorized);
        setButtonListener(buttonFrontShouldersLeft, MuscleGroup.SHOULDERS, imageFrontShoulders,
                R.drawable.front_shoulders, R.drawable.front_shoulders_colorized);
        setButtonListener(buttonFrontShouldersRight, MuscleGroup.SHOULDERS, imageFrontShoulders,
                R.drawable.front_shoulders, R.drawable.front_shoulders_colorized);
        setButtonListener(buttonFrontTrapsLeft, MuscleGroup.TRAPS, imageFrontTraps,
                R.drawable.front_traps, R.drawable.front_traps_colorized);
        setButtonListener(buttonFrontTrapsRight, MuscleGroup.TRAPS, imageFrontTraps,
                R.drawable.front_traps, R.drawable.front_traps_colorized);
        setButtonListener(buttonNeck, MuscleGroup.NECK, imageNeck,
                R.drawable.neck, R.drawable.neck_colorized);
        setButtonListener(buttonQuadsLeft, MuscleGroup.QUADS, imageQuads,
                R.drawable.quads, R.drawable.quads_colorized);
        setButtonListener(buttonQuadsMiddle, MuscleGroup.QUADS, imageQuads,
                R.drawable.quads, R.drawable.quads_colorized);
        setButtonListener(buttonQuadsRight, MuscleGroup.QUADS, imageQuads,
                R.drawable.quads, R.drawable.quads_colorized);

//        Button buttonGenerate = (Button) findViewById(R.id.button_generate_workout);
//        buttonGenerate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                navigateToWorkout();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

//        queue = Volley.newRequestQueue(this);
//        url ="http://www.google.com";
//        final TextView mTextView = (TextView) findViewById(R.id.volley_request);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        mTextView.setText("Response is: "+ response.substring(0,500));
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                mTextView.setText("That didn't work!");
//            }
//        });
//        queue.add(stringRequest);
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

//    /** Called when the user clicks the Send button */
//    public void addMuscleGroup(String muscleGroup) {
//        // Add the muscle group to the muscleGroups array
//        if (muscleGroups != null && !muscleGroups.contains(muscleGroup)) {
//            muscleGroups.add(muscleGroup);
//        }
//
//        updateTextView();
//    }
//
//    public void updateTextView() {
//        TextView muscleGroupList = (TextView) findViewById(R.id.list_muscle_group);
//        muscleGroupList.setText(muscleGroups.toString());
//    }

    /** Called when the user clicks the Send button */
    public void navigateToWorkout() {
        // Do something in response to button
        Intent intent = new Intent(this, WorkoutActivity.class);
        intent.putExtra("EXTRA_MUSCLE_GROUPS", muscleGroups);
        startActivity(intent);
    }
}
