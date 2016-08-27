package goodcompanyname.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jeremy on 8/26/16.
 */
public class ExerciseDetailsFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    ImageView exerciseImage1;
    ImageView exerciseImage2;
    ImageView exerciseImage3;
    ImageView exerciseImage4;

    public static ExerciseDetailsFragment newInstance(int pageNo) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        ExerciseDetailsFragment fragment = new ExerciseDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_details, container, false);

        exerciseImage1 = (ImageView) view.findViewById(R.id.exercise_image_1);
        exerciseImage2 = (ImageView) view.findViewById(R.id.exercise_image_2);
        exerciseImage3 = (ImageView) view.findViewById(R.id.exercise_image_3);
        exerciseImage4 = (ImageView) view.findViewById(R.id.exercise_image_4);

        exerciseImage1.setImageResource(R.drawable.ic_accessibility_white_selected_24dp);
        exerciseImage2.setImageResource(R.drawable.ic_accessibility_white_selected_24dp);
        exerciseImage3.setImageResource(R.drawable.ic_accessibility_white_selected_24dp);
        exerciseImage4.setImageResource(R.drawable.ic_accessibility_white_selected_24dp);

        return view;
    }
}
