package goodcompanyname.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jeremy on 8/20/16.
 */
public class SettingsFragment extends Fragment {
    // todo: create options for isolation/compound, difficulty, and syncing exercise db via web scrape and "about" information

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;

    public static SettingsFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNo = getArguments().getInt(ARG_PAGE);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
//        TextView textView = (TextView) view;
//        textView.setText("Fragment #" + mPageNo);
        return view;
    }
}
