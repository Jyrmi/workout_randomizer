package adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import constant.PreferenceTags;
import constant.Setting;
import constant.SettingsCategory;
import goodcompanyname.myapplication.R;
import sqlite.ExerciseContract;
import sqlite.ExerciseDb;

/**
 * Created by josephchoi on 8/20/16.
 */
public class ExerciseRecyclerAdapter extends RecyclerView.Adapter<ExerciseRecyclerAdapter.ViewHolder> {
    // todo: keep video/image links to cards as parameters, don't set them on click (super buggy)
    // todo: fix video stream
    // todo: query media based on gender
    private ArrayList<HashMap<String, String>> exerciseList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        CardView cardView;
        ImageView cardImage1;
        ImageView cardImage2;
        ImageView cardImage3;
        ImageView cardImage4;
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

            setMedia(cardView);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            LinearLayout detailsView = (LinearLayout) v.findViewById(R.id.card_details);
            if (detailsView.getVisibility() == View.GONE) {
                detailsView.setVisibility(View.VISIBLE);
            } else {
                detailsView.setVisibility(View.GONE);
            }
        }

        public void setMedia(View v) {
            cardImage1 = (ImageView) v.findViewById(R.id.card_image_1);
            cardImage2 = (ImageView) v.findViewById(R.id.card_image_2);
            cardImage3 = (ImageView) v.findViewById(R.id.card_image_3);
            cardImage4 = (ImageView) v.findViewById(R.id.card_image_4);

            cardImage1.setImageResource(0);
            cardImage2.setImageResource(0);
            cardImage3.setImageResource(0);
            cardImage4.setImageResource(0);

            String videoUrl;
            String[] imageUrls;
            if (isMale(v.getContext())) {
                videoUrl = exercise.get(ExerciseContract.ExerciseEntry.COLUMN_MALE_VIDEO);
                imageUrls = TextUtils.split(
                        exercise.get(ExerciseContract.ExerciseEntry.COLUMN_MALE_IMAGES), ",");
            } else {
                videoUrl = exercise.get(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_VIDEO);
                imageUrls = TextUtils.split(
                        exercise.get(ExerciseContract.ExerciseEntry.COLUMN_FEMALE_IMAGES), ",");
            }

//            // Setup the VideoView with the exercise's video
//            VideoView cardVideo = (VideoView) v.findViewById(R.id.card_video);
//            MediaController mediaController = new MediaController(v.getContext());
//            mediaController.setAnchorView(cardVideo);
//            cardVideo.setMediaController(mediaController);
//            cardVideo.setVideoURI(Uri.parse("http:" + videoUrl));
//            cardVideo.start();

//                    try {
//                        cardVideo.setVideoURI(new RetrieveVideoTask().execute(queryResult.a).get());
//                        cardVideo.start();
//                        Log.d("ViewHolder.onClick()", "started video at http:" + queryResult.a);
//                    } catch (InterruptedException | ExecutionException e) {
//                        e.printStackTrace();
//                    }

            // Get Images of the exercise
            int imagesSet = 0;
            for (String imageUrlString : imageUrls) {

                Bitmap bmp = null;
                try {
                    bmp = new RetrieveImageTask().execute(imageUrlString).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                if (imagesSet == 0) {
                    cardImage1.setImageBitmap(bmp);
                } else if (imagesSet == 1) {
                    cardImage2.setImageBitmap(bmp);
                } else if (imagesSet == 2) {
                    cardImage3.setImageBitmap(bmp);
                } else {
                    cardImage4.setImageBitmap(bmp);
                }

                imagesSet++;
            }
        }

        public Boolean isMale(Context context) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(
                    PreferenceTags.PREFERENCES_SETTINGS, Context.MODE_PRIVATE);

            if (sharedPreferences.getString("Male", null) == null) {
                return false;
            } else return true;
        }

        private class RetrieveImageTask extends AsyncTask<String, Void, Bitmap> {
            protected Bitmap doInBackground(String... urls) {
                return loadImageFromNetwork(urls[0]);
            }
        }

        private class RetrieveVideoTask extends AsyncTask<String, Void, Uri> {
            protected Uri doInBackground(String... urls) {
                return Uri.parse("http:" + urls[0]);
            }
        }

        private Bitmap loadImageFromNetwork(String url) {
            try {
                URL imageUrl = new URL(url);
                try {
                    return BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
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
        cardView.findViewById(R.id.card_details).setVisibility(View.GONE);

        return new ViewHolder(cardView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindViews(exerciseList.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return exerciseList.size();
    }
}
