package goodcompanyname.workout_randomizer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import preferences.MySharedPrefs;
import sqlite.LogsContract;
import sqlite.LogsDbHelper;

/**
 * Created by root on 8/28/16.
 */
public class SummaryFragment extends Fragment {
    public static final String TAG = "SummaryFragment";
    public static final String ARG_PAGE = "ARG_PAGE";

    TextView textViewFavoriteGroup;
    TextView textViewFavoriteExercise;
    TextView textViewLeastFavoriteExercise;
    TextView textViewCompleted;
    TextView textViewSkipped;
    TextView textViewCurrentStreak;
    TextView textViewLongestStreak;

    public static SummaryFragment newInstance(int pageNo) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        SummaryFragment fragment = new SummaryFragment();
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
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        textViewFavoriteGroup = (TextView) view.findViewById(R.id.summary_group);
        textViewFavoriteExercise = (TextView) view.findViewById(R.id.summary_favorite_exercise);
        textViewLeastFavoriteExercise =
                (TextView) view.findViewById(R.id.summary_least_favorite_exercise);
        textViewCompleted = (TextView) view.findViewById(R.id.summary_completed);
        textViewSkipped = (TextView) view.findViewById(R.id.summary_skipped);
        textViewCurrentStreak = (TextView) view.findViewById(R.id.summary_current_streak);
        textViewLongestStreak = (TextView) view.findViewById(R.id.summary_longest_streak);

        return view;
    }

    /**
     * Update all charts/logs when the user switches to this tab.
     * @param isVisibleToUser whether the tab has come into view. I think.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            refreshSummary();
        }
    }

    public void refreshSummary() {
        HashMap<String, String> summaryResults;

        textViewFavoriteGroup.setText(TextUtils.join(", ",
                MySharedPrefs.getFavoriteGroups(getContext())));

        ArrayList<HashMap<String, String>> logs = LogsDbHelper.readLogs(getContext());
        summaryResults = getSummary(logs);
        textViewFavoriteExercise.setText(summaryResults.get("favorite_exercise"));
        textViewLeastFavoriteExercise.setText(summaryResults.get("least_favorite_exercise"));
        textViewCompleted.setText(summaryResults.get("completed"));
        textViewSkipped.setText(summaryResults.get("skipped"));
        textViewCurrentStreak.setText(summaryResults.get("current"));
        textViewLongestStreak.setText(summaryResults.get("longest"));
    }

    private HashMap<String, String> getSummary(ArrayList<HashMap<String, String>> logs) {
        HashMap<String, String> summaryResults = new HashMap<>();
        int count;
        int completed = 0;
        int skipped = 0;
        int currentStreak = 0;
        boolean currentStreakEnded = false;
        String name;
        String status;
        HashMap<String, Integer> completionCounts = new HashMap<>();
        HashMap<String, Integer> skipCounts = new HashMap<>();

        summaryResults.put("current", "0");
        summaryResults.put("longest", "0");

        for (HashMap<String, String> logEntry : logs) {
            name = logEntry.get(LogsContract.LogEntry.COLUMN_EXERCISE);
            status = logEntry.get(LogsContract.LogEntry.COLUMN_STATUS);

            if (LogsContract.STATUS_COMPLETE.equals(status)) {
                completed++;

                currentStreak++;
                if ((!currentStreakEnded) && (summaryResults.get("current") == null ||
                        currentStreak > Integer.parseInt(summaryResults.get("current")))) {
                    summaryResults.put("current", Integer.toString(currentStreak));
                    summaryResults.put("longest", Integer.toString(currentStreak));
                }
                if ((currentStreakEnded) && (summaryResults.get("longest") == null ||
                        currentStreak > Integer.parseInt(summaryResults.get("longest")))) {
                    summaryResults.put("longest", Integer.toString(currentStreak));
                }

                if (!completionCounts.containsKey(name)) {
                    completionCounts.put(name, 1);
                } else completionCounts.put(name, completionCounts.get(name) + 1);
            } else {
                skipped++;
                currentStreak = 0;
                currentStreakEnded = true;

                if (!skipCounts.containsKey(name)) {
                    skipCounts.put(name, 1);
                } else skipCounts.put(name, skipCounts.get(name) + 1);
            }
        }
        summaryResults.put("completed", Integer.toString(completed));
        summaryResults.put("skipped", Integer.toString(skipped));

        // Find most commonly completed of the exercises
        count = 0;
        name = "None";
        for (Map.Entry<String, Integer> entry : completionCounts.entrySet()) {
            if (entry.getValue() > count) {
                name = entry.getKey();
                count = entry.getValue();
            }
        }
        summaryResults.put("favorite_exercise", name);

        // Find most commonly skipped of the exercises
        count = 0;
        name = "None";
        for (Map.Entry<String, Integer> entry : skipCounts.entrySet()) {
            if (entry.getValue() > count) {
                name = entry.getKey();
                count = entry.getValue();
            }
        }
        summaryResults.put("least_favorite_exercise", name);

        return summaryResults;
    }
}
