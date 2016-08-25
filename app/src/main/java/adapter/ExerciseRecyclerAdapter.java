package adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import goodcompanyname.myapplication.R;

/**
 * Created by josephchoi on 8/20/16.
 */
public class ExerciseRecyclerAdapter extends RecyclerView.Adapter<ExerciseRecyclerAdapter.ViewHolder> {
    private ArrayList<TwoTuple<String, String>> exercises;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ExerciseRecyclerAdapter(ArrayList<TwoTuple<String, String>> exercises) {
        this.exercises = exercises;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ExerciseRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView textViewExercise = (TextView) holder.cardView.findViewById(R.id.card_exercise);
        TextView textViewMuscleGoup = (TextView) holder.cardView.findViewById(R.id.card_muscle);

        textViewExercise.setText(exercises.get(position).a);
        textViewMuscleGoup.setText(exercises.get(position).b.toString());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return exercises.size();
    }
}
