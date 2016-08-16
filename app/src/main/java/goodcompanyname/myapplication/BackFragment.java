package goodcompanyname.myapplication;

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

public class BackFragment extends Fragment {

    ImageView imageGroupE;
    ImageView imageGroupF;
    ImageView imageGroupG;
    ImageView imageGroupH;

    Button buttonRearTraps;
    Button buttonRearShoulders;
    Button buttonTriceps;
    Button buttonRearForearms;
    Button buttonMiddleBack;
    Button buttonLowerBack;
    Button buttonGlutes;
    Button buttonRearHamstrings;
    Button buttonRearCalves;

    ArrayList<MuscleGroup> selectedMuscleGroups;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.back_fragment, container, false);

        imageGroupE = (ImageView) myFragmentView.findViewById(R.id.image_group_e);
        imageGroupF = (ImageView) myFragmentView.findViewById(R.id.image_group_f);
        imageGroupG = (ImageView) myFragmentView.findViewById(R.id.image_group_g);
        imageGroupH = (ImageView) myFragmentView.findViewById(R.id.image_group_h);

        buttonRearTraps = (Button) myFragmentView.findViewById(R.id.button_rear_traps);
        buttonRearShoulders = (Button) myFragmentView.findViewById(R.id.button_rear_shoulders);
        buttonTriceps = (Button) myFragmentView.findViewById(R.id.button_triceps);
        buttonRearForearms = (Button) myFragmentView.findViewById(R.id.button_rear_forearms);
        buttonMiddleBack = (Button) myFragmentView.findViewById(R.id.button_middle_back);
        buttonLowerBack = (Button) myFragmentView.findViewById(R.id.button_lower_back);
        buttonGlutes = (Button) myFragmentView.findViewById(R.id.button_glutes);
        buttonRearHamstrings = (Button) myFragmentView.findViewById(R.id.button_rear_hamstrings);
        buttonRearCalves = (Button) myFragmentView.findViewById(R.id.button_rear_calves);

        selectedMuscleGroups = new ArrayList<>();

        setButtonListener(buttonRearTraps, MuscleGroup.TRAPS, imageGroupE);
        setButtonListener(buttonRearShoulders, MuscleGroup.SHOULDERS, imageGroupE);
        setButtonListener(buttonTriceps, MuscleGroup.TRICEPS, imageGroupF);
        setButtonListener(buttonRearForearms, MuscleGroup.FOREARMS, imageGroupF);
        setButtonListener(buttonMiddleBack, MuscleGroup.MIDDLE_BACK, imageGroupG);
        setButtonListener(buttonLowerBack, MuscleGroup.LOWER_BACK, imageGroupG);
        setButtonListener(buttonGlutes, MuscleGroup.GLUTES, imageGroupH);
        setButtonListener(buttonRearHamstrings, MuscleGroup.HAMSTRINGS, imageGroupH);
        setButtonListener(buttonRearCalves, MuscleGroup.CALVES, imageGroupH);

        return myFragmentView;
    }

    private void setGroupImageResource(ImageView imageView) {
        if (imageView == imageGroupE) {
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
        } else if (imageView == imageGroupF) {
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
        } else if (imageView == imageGroupG) {
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
        } else if (imageView == imageGroupH) {
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
