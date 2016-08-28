package goodcompanyname.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import constant.PreferenceTags;
import sqlite.ExerciseContract;

/**
 * Created by jeremy on 8/26/16.
 */
public class ExerciseDetailsActivity extends AppCompatActivity {
    // todo: remember video time on pause, and move the video controls up to the video
    // todo: check for internet connectivity and hide the video/images if not connected

    public static final String TAG = "ExerciseDetailsActivity";

    VideoView exerciseVideo;

    TextView textViewName;
    TextView textViewGroup;
    TextView textViewOtherGroups;
    TextView textViewDifficulty;
    TextView textViewType;
    TextView textViewMechanics;
    TextView textViewEquipment;
    TextView textViewForce;
    TextView textViewSport;
    TextView textViewRating;

    ImageView exerciseImage1;
    ImageView exerciseImage2;
    ImageView exerciseImage3;
    ImageView exerciseImage4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);

        textViewName = (TextView) findViewById(R.id.details_name);
        textViewGroup = (TextView) findViewById(R.id.details_group);
        textViewOtherGroups = (TextView) findViewById(R.id.details_other_groups);
        textViewDifficulty = (TextView) findViewById(R.id.details_difficulty);
        textViewType = (TextView) findViewById(R.id.details_type);
        textViewMechanics = (TextView) findViewById(R.id.details_mechanics);
        textViewEquipment = (TextView) findViewById(R.id.details_equipment);
        textViewForce = (TextView) findViewById(R.id.details_force);
        textViewSport = (TextView) findViewById(R.id.details_sport);
        textViewRating = (TextView) findViewById(R.id.details_rating);

        textViewName.setText(getIntent().getExtras().getString(ExerciseContract.ExerciseEntry.COLUMN_NAME));
        textViewGroup.setText(getIntent().getExtras().getString(ExerciseContract.ExerciseEntry.COLUMN_GROUP));
        String otherGroups = getIntent().getExtras().getString(ExerciseContract.ExerciseEntry.COLUMN_OTHER_GROUPS);
        if (otherGroups != null)
            textViewOtherGroups.setText(otherGroups.replace(",", "/"));
        else textViewOtherGroups.setText("None");
        textViewDifficulty.setText(getIntent().getExtras().getString(ExerciseContract.ExerciseEntry.COLUMN_DIFFICULTY));
        textViewType.setText(getIntent().getExtras().getString(ExerciseContract.ExerciseEntry.COLUMN_TYPE));
        textViewMechanics.setText(getIntent().getExtras().getString(ExerciseContract.ExerciseEntry.COLUMN_MECHANICS));
        textViewEquipment.setText(getIntent().getExtras().getString(ExerciseContract.ExerciseEntry.COLUMN_EQUIPMENT));
        textViewForce.setText(getIntent().getExtras().getString(ExerciseContract.ExerciseEntry.COLUMN_FORCE));
        textViewSport.setText(getIntent().getExtras().getString(ExerciseContract.ExerciseEntry.COLUMN_SPORT));
        textViewRating.setText(getIntent().getExtras().getString(ExerciseContract.ExerciseEntry.COLUMN_RATING));

        exerciseVideo = (VideoView) findViewById(R.id.details_video);
        exerciseImage1 = (ImageView) findViewById(R.id.exercise_image_1);
        exerciseImage2 = (ImageView) findViewById(R.id.exercise_image_2);
        exerciseImage3 = (ImageView) findViewById(R.id.exercise_image_3);
        exerciseImage4 = (ImageView) findViewById(R.id.exercise_image_4);


        String images = getIntent().getExtras().getString("EXTRA_IMAGES");
        if (images != null) setImages(images);
        String videoUrl = getIntent().getExtras().getString("EXTRA_VIDEO");
        if (videoUrl != null) setVideo(videoUrl);
    }

    /**
     * Remember the position in the video.
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * Remember the position in the video.
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    public Boolean setImages(String images) {
        if (images == null) return false;

        String[] imageUrls = TextUtils.split(images , ",");

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
                exerciseImage1.setImageBitmap(bmp);
            } else if (imagesSet == 1) {
                exerciseImage2.setImageBitmap(bmp);
            } else if (imagesSet == 2) {
                exerciseImage3.setImageBitmap(bmp);
            } else {
                exerciseImage4.setImageBitmap(bmp);
            }

            imagesSet++;
        }

        return true;
    }

    private class RetrieveImageTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
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

    public Boolean setVideo(String videoUrl) {

        if (videoUrl == null) return false;

        // Execute StreamVideo AsyncTask
        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(this);
            mediacontroller.setAnchorView(exerciseVideo);
            // Get the URL from String VideoURL
            Uri video = Uri.parse("http:" + videoUrl);
            exerciseVideo.setMediaController(mediacontroller);
            exerciseVideo.setVideoURI(video);
        } catch (Exception e) {
            e.printStackTrace();
        }

        exerciseVideo.requestFocus();
        exerciseVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                exerciseVideo.start();
            }
        });

        return true;
    }
}
