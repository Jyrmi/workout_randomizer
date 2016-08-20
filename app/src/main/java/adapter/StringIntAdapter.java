package adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import goodcompanyname.myapplication.R;

/**
 * Created by jeremy on 8/18/16.
 */
public class StringIntAdapter extends BaseAdapter {
    Activity activity;
    TextView textViewKey;
    TextView textViewCount;
    ArrayList<TwoTuple<String, Integer>> histogram;

    public StringIntAdapter(Activity activity, ArrayList<TwoTuple<String, Integer>> histogram) {
        super();
        this.activity = activity;
        this.histogram = histogram;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return histogram.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = activity.getLayoutInflater();

        if (rowView == null) {
            rowView = inflater.inflate(R.layout.histogram_row, null);

            textViewKey = (TextView) rowView.findViewById(R.id.list_view_key);
            textViewCount = (TextView) rowView.findViewById(R.id.list_view_count);
        }

        textViewKey.setText(histogram.get(position).a);
        textViewCount.setText(histogram.get(position).b.toString());

        return rowView;
    }
}