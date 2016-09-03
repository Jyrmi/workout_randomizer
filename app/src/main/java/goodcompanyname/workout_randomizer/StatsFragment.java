package goodcompanyname.workout_randomizer;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import preferences.MySharedPrefs;
import sqlite.LogsContract;
import sqlite.LogsDbHelper;

/**
 * Created by jeremy on 8/11/16.
 */
public class StatsFragment extends Fragment {
    // todo: more graphs of relevant information (8)

    private final static String TAG = "StatsFragment";
    public static final String ARG_PAGE = "ARG_PAGE";

    TabLayout tabLayout;
    FloatingActionButton fabClearData;
    TextView tooltip;

    RadarFragment radarFragment;
    LogsFragment logsFragment;
    SummaryFragment summaryFragment;

    DialogInterface.OnClickListener dialogClickListener;

    private int[] tabIcons = {
            R.drawable.ic_track_changes_white_selector,
            R.drawable.ic_format_list_bulleted_white_selector,
            R.drawable.ic_done_white_selector
    };

    public static StatsFragment newInstance(int pageNo) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        StatsFragment fragment = new StatsFragment();
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
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        // Setup the viewPager
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager_stats);
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        if (viewPager != null) {
            viewPager.setOffscreenPageLimit(2);
            viewPager.setAdapter(pagerAdapter);
        }

        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_stats);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);

            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(pagerAdapter.getTabView(i));
            }

            tabLayout.getTabAt(0).getCustomView().setSelected(true);
        }

        tooltip = (TextView) view.findViewById(R.id.results_tooltip);

        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        MySharedPrefs.clearAll(getActivity());
                        clearTable();
                        refreshCurrentView();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        fabClearData = (FloatingActionButton) view.findViewById(R.id.fab_clear_data);
        fabClearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Clear all data?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

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
            refreshCurrentView();
        }
    }

    private void refreshCurrentView() {
        switch (tabLayout.getSelectedTabPosition()) {
            case 0: radarFragment.refreshChart(); return;
            case 1: logsFragment.refreshLogs(); return;
            case 2: summaryFragment.refreshSummary(); return;
        }
    }

    private void clearTable() {
        LogsDbHelper mDbHelper = new LogsDbHelper(getActivity());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.delete(LogsContract.LogEntry.TABLE_NAME, null, null);
        db.close();
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public final int PAGE_COUNT = 3;

        private final String[] mTabsTitle = {"Groups", "Logs", "Summary"};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab, null);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            icon.setImageResource(tabIcons[position]);
            return view;
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    radarFragment = RadarFragment.newInstance(1);
                    return radarFragment;
                case 1:
                    logsFragment = LogsFragment.newInstance(2);
                    return logsFragment;
                case 2:
                    summaryFragment = SummaryFragment.newInstance(2);
                    return summaryFragment;

            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabsTitle[position];
        }
    }
}
