package goodcompanyname.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import adapter.LogsRecyclerAdapter;
import sqlite.LogsContract;
import sqlite.LogsDbHelper;

/**
 * Created by jeremy on 8/26/16.
 */
public class LogsFragment extends Fragment{

    private static final String TAG = "LogsFragment";
    private static final String emptyLogs = "Complete or skip some exercises to see an activity log.";
    public static final String ARG_PAGE = "ARG_PAGE";

    TextView tooltip;
    RecyclerView recyclerView;

    LogsRecyclerAdapter logsAdapter;

    public static LogsFragment newInstance(int pageNo) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        LogsFragment fragment = new LogsFragment();
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
        View view = inflater.inflate(R.layout.fragment_logs, container, false);

        tooltip = (TextView) view.findViewById(R.id.logs_tooltip);

        recyclerView = (RecyclerView) view.findViewById(R.id.logs_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    public void refreshLogs() {
        ArrayList<HashMap<String, String>> logs = readTable();
        if (logs.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tooltip.setVisibility(View.VISIBLE);
            tooltip.setText(emptyLogs);
        } else {
            logsAdapter = new LogsRecyclerAdapter(logs);
            recyclerView.setAdapter(logsAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            tooltip.setVisibility(View.GONE);
        }
    }

    private ArrayList<HashMap<String, String>> readTable() {
        ArrayList<HashMap<String, String>> exercises = new ArrayList<>();
        HashMap<String, String> exerciseDetails;

        LogsDbHelper mDbHelper = new LogsDbHelper(getActivity());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                LogsContract.LogEntry._ID,
                LogsContract.LogEntry.COLUMN_EXERCISE,
                LogsContract.LogEntry.COLUMN_GROUP,
                LogsContract.LogEntry.COLUMN_DATE,
                LogsContract.LogEntry.COLUMN_STATUS
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                LogsContract.LogEntry.COLUMN_DATE + " DESC";

        Cursor c = db.query(
                LogsContract.LogEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String exercise = c.getString(c.getColumnIndex(LogsContract.LogEntry.COLUMN_EXERCISE));
                String group = c.getString(c.getColumnIndex(LogsContract.LogEntry.COLUMN_GROUP));
                String date = c.getString(c.getColumnIndex(LogsContract.LogEntry.COLUMN_DATE));
                String status = c.getString(c.getColumnIndex(LogsContract.LogEntry.COLUMN_STATUS));

                exerciseDetails = new HashMap<>();
                exerciseDetails.put(LogsContract.LogEntry.COLUMN_EXERCISE, exercise);
                exerciseDetails.put(LogsContract.LogEntry.COLUMN_GROUP, group);
                exerciseDetails.put(LogsContract.LogEntry.COLUMN_DATE, date);
                exerciseDetails.put(LogsContract.LogEntry.COLUMN_STATUS, status);

                exercises.add(exerciseDetails);

                c.moveToNext();
            }
        }

        c.close();
        db.close();

        return exercises;
    }
}
