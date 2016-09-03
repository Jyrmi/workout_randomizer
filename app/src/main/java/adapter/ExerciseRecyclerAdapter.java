package adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import constant.PreferenceTags;
import goodcompanyname.workout_randomizer.ExerciseDetailsActivity;
import goodcompanyname.workout_randomizer.R;
import sqlite.ExerciseContract;

/**
 * Created by josephchoi on 8/20/16.
 */
public class ExerciseRecyclerAdapter extends RecyclerView.Adapter<ExerciseRecyclerAdapter.ViewHolder> {
    private ArrayList<HashMap<String, String>> exerciseList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        HashMap<String, String> exercise;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

        public void bindViews(HashMap<String, String> exercise) {
            this.exercise = exercise;

            TextView textViewExercise = (TextView) cardView.findViewById(R.id.card_exercise);
            TextView textViewMuscleGroup = (TextView) cardView.findViewById(R.id.card_muscle);

            textViewExercise.setText(exercise.get(ExerciseContract.ExerciseEntry.COLUMN_NAME));
            textViewMuscleGroup.setText(exercise.get(ExerciseContract.ExerciseEntry.COLUMN_GROUP));

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            startDetailsActivity(view);
        }

        public Boolean isMale(Context context) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(
                    PreferenceTags.PREFERENCES_SETTINGS, Context.MODE_PRIVATE);

            if (sharedPreferences.getString("Male", null) == null) {
                return false;
            } else return true;
        }

        /**
         * Checks whether the device is connected to the internet.
         * @return whether connected.
         */
        public boolean isOnline(Context context) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }

        private void startDetailsActivity(View view) {
            Intent intent = new Intent(view.getContext(), ExerciseDetailsActivity.class);
            intent.putExtra(ExerciseContract.ExerciseEntry.COLUMN_NAME,
                    exercise.get(ExerciseContract.ExerciseEntry.COLUMN_NAME));
            intent.putExtra(ExerciseContract.ExerciseEntry.COLUMN_GROUP,
                    exercise.get(ExerciseContract.ExerciseEntry.COLUMN_GROUP));
            intent.putExtra(ExerciseContract.ExerciseEntry.COLUMN_OTHER_GROUPS,
                    exercise.get(ExerciseContract.ExerciseEntry.COLUMN_OTHER_GROUPS));
            intent.putExtra(ExerciseContract.ExerciseEntry.COLUMN_DIFFICULTY,
                    exercise.get(ExerciseContract.ExerciseEntry.COLUMN_DIFFICULTY));
            intent.putExtra(ExerciseContract.ExerciseEntry.COLUMN_TYPE,
                    exercise.get(ExerciseContract.ExerciseEntry.COLUMN_TYPE));
            intent.putExtra(ExerciseContract.ExerciseEntry.COLUMN_MECHANICS,
                    exercise.get(ExerciseContract.ExerciseEntry.COLUMN_MECHANICS));
            intent.putExtra(ExerciseContract.ExerciseEntry.COLUMN_EQUIPMENT,
                    exercise.get(ExerciseContract.ExerciseEntry.COLUMN_EQUIPMENT));
            intent.putExtra(ExerciseContract.ExerciseEntry.COLUMN_FORCE,
                    exercise.get(ExerciseContract.ExerciseEntry.COLUMN_FORCE));
            intent.putExtra(ExerciseContract.ExerciseEntry.COLUMN_SPORT,
                    exercise.get(ExerciseContract.ExerciseEntry.COLUMN_SPORT));
            intent.putExtra(ExerciseContract.ExerciseEntry.COLUMN_RATING,
                    exercise.get(ExerciseContract.ExerciseEntry.COLUMN_RATING));
            if (isMale(view.getContext()) ||
                    exercise.get(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_VIDEO) == null) {
                intent.putExtra("EXTRA_IMAGES",
                        exercise.get(ExerciseContract.ExerciseEntry.COLUMN_MALE_IMAGES));
                intent.putExtra("EXTRA_VIDEO",
                        exercise.get(ExerciseContract.ExerciseEntry.COLUMN_MALE_VIDEO));
            } else {
                intent.putExtra("EXTRA_IMAGES",
                        exercise.get(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_IMAGES));
                intent.putExtra("EXTRA_VIDEO",
                        exercise.get(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_VIDEO));
            }
            view.getContext().startActivity(intent);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ExerciseRecyclerAdapter(ArrayList<HashMap<String, String>> exerciseList) {
        this.exerciseList = exerciseList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ExerciseRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(cardView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindViews(exerciseList.get(position));

//        setAnimation(holder.cardView, position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return exerciseList.size();
    }
}
