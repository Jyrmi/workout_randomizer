package goodcompanyname.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class FrontFragment extends Fragment {

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

    ArrayList<MuscleGroup> selectedMuscleGroups;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.front_fragment, container, false);

        imageGroupA = (ImageView) myFragmentView.findViewById(R.id.image_group_a);
        imageGroupB = (ImageView) myFragmentView.findViewById(R.id.image_group_b);
        imageGroupC = (ImageView) myFragmentView.findViewById(R.id.image_group_c);
        imageGroupD = (ImageView) myFragmentView.findViewById(R.id.image_group_d);

        buttonAbsLower = (Button) myFragmentView.findViewById(R.id.button_abs_lower);
        buttonAbsUpper = (Button) myFragmentView.findViewById(R.id.button_rear_traps);
        buttonBiceps = (Button) myFragmentView.findViewById(R.id.button_biceps);
        buttonChest = (Button) myFragmentView.findViewById(R.id.button_chest);
        buttonForearms = (Button) myFragmentView.findViewById(R.id.button_forearms);
        buttonFrontCalves = (Button) myFragmentView.findViewById(R.id.button_front_calves);
        buttonFrontHamstrings = (Button) myFragmentView.findViewById(R.id.button_front_hamstrings);
        buttonFrontLatsLeft = (Button) myFragmentView.findViewById(R.id.button_front_lats_left);
        buttonFrontLatsRight = (Button) myFragmentView.findViewById(R.id.button_front_lats_right);
        buttonFrontShoulders = (Button) myFragmentView.findViewById(R.id.button_front_shoulders);
        buttonFrontTraps = (Button) myFragmentView.findViewById(R.id.button_front_traps);
        buttonNeck = (Button) myFragmentView.findViewById(R.id.button_neck);
        buttonQuads = (Button) myFragmentView.findViewById(R.id.button_quads);

        selectedMuscleGroups = new ArrayList<>();

        setButtonListener(buttonAbsLower, MuscleGroup.ABS, imageGroupC);
        setButtonListener(buttonAbsUpper, MuscleGroup.ABS, imageGroupC);
        setButtonListener(buttonBiceps, MuscleGroup.BICEPS, imageGroupB);
        setButtonListener(buttonChest, MuscleGroup.CHEST, imageGroupA);
        setButtonListener(buttonForearms, MuscleGroup.FOREARMS, imageGroupB);
        setButtonListener(buttonFrontCalves, MuscleGroup.CALVES, imageGroupD);
        setButtonListener(buttonFrontHamstrings, MuscleGroup.HAMSTRINGS, imageGroupC);
        setButtonListener(buttonFrontLatsLeft, MuscleGroup.LATS, imageGroupC);
        setButtonListener(buttonFrontLatsRight, MuscleGroup.LATS, imageGroupC);
        setButtonListener(buttonFrontShoulders, MuscleGroup.SHOULDERS, imageGroupB);
        setButtonListener(buttonFrontTraps, MuscleGroup.TRAPS, imageGroupA);
        setButtonListener(buttonNeck, MuscleGroup.NECK, imageGroupA);
        setButtonListener(buttonQuads, MuscleGroup.QUADS, imageGroupD);

        return myFragmentView;
    }

    private void setGroupImageResource(ImageView imageView) {
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

    /**
     * Sets listeners for this activity's buttons.
     * @param button the button.
     * @param imageView the ImageView associated with the button.
     */
    private void setButtonListener(Button button, final MuscleGroup muscleGroup, final ImageView imageView) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedMuscleGroups.contains(muscleGroup)) {
                    selectedMuscleGroups.remove(muscleGroup);
                } else {
                    selectedMuscleGroups.add(muscleGroup);
                }

                setGroupImageResource(imageView);
            }
        });
    }

    /**
     * Getter for the selected muscle groups.
     * @return
     */
    public ArrayList<MuscleGroup> getSelectedMuscleGroups() {
        return selectedMuscleGroups;
    }
}
