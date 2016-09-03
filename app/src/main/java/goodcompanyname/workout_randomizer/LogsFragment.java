package goodcompanyname.workout_randomizer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import adapter.LogsRecyclerAdapter;
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

    /**
     * Update all charts/logs when the user switches to this tab.
     * @param isVisibleToUser whether the tab has come into view. I think.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            refreshLogs();
        }
    }

    public void refreshLogs() {
        ArrayList<HashMap<String, String>> logs = LogsDbHelper.readLogs(getContext());
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
}
