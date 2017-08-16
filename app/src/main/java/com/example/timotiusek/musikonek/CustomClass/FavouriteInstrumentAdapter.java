package com.example.timotiusek.musikonek.CustomClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.timotiusek.musikonek.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TimotiusEk on 5/6/2017.
 */

public class FavouriteInstrumentAdapter extends BaseAdapter {
    private ArrayList<String> instruments;
    private Context mContext;
    private LayoutInflater inflater;
    @BindView(R.id.favourite_instrument__favorite_instrument_rl)
    TextView favouriteInstrumentLabel;

    public FavouriteInstrumentAdapter(ArrayList<String> instruments, Context c) {
        this.instruments = instruments;
        mContext = c;
    }

    @Override
    public int getCount() {
        return instruments.size();
    }

    @Override
    public Object getItem(int position) {
        return instruments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String instrument = (String) getItem(position);
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_layout_favorite_instrument, parent, false);
            ButterKnife.bind(this, convertView);

            favouriteInstrumentLabel.setText(instrument);
        }
        return convertView;
    }
}
