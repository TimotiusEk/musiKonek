package com.example.timotiusek.musikonek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TimotiusEk on 5/6/2017.
 */

public class ReportPageAdapter extends BaseAdapter{
    private ArrayList<Report> reports;
    private Context mContext;
    private LayoutInflater inflater;
    @BindView(R.id.report_title)
    TextView reportTitle;

    @BindView(R.id.report_date_and_time)
    TextView reportDateAndTime;


    ReportPageAdapter(ArrayList<Report> reports, Context mContext){
        this.reports = reports;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return reports.size();
    }

    @Override
    public Object getItem(int position) {
        return reports.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Report report = (Report) getItem(position);
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_layout_report_page, parent, false);
            ButterKnife.bind(this, convertView);

            reportTitle.setText(report.getTitle());
            reportDateAndTime.setText(report.getDateAndTime());
        }
        return convertView;
    }
}
