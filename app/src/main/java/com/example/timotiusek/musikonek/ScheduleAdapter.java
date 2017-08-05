package com.example.timotiusek.musikonek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.timotiusek.musikonek.CustomClass.Schedule;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TimotiusEk on 5/6/2017.
 */

public class ScheduleAdapter extends BaseAdapter {
    private ArrayList<Schedule> schedules;
    private Context mContext;
    private LayoutInflater inflater;
    @BindView(R.id.schedule_name__schedule_rl)
    TextView scheduleName;

    @BindView(R.id.schedule_date_and_time__schedule_rl)
    TextView scheduleDateAndTime;

    @BindView(R.id.schedule_status__schedule_rl)
    TextView scheduleStatus;


    ScheduleAdapter(ArrayList<Schedule> schedules, Context mContext){
        this.schedules = schedules;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Override
    public Object getItem(int position) {
        return schedules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Schedule schedule = (Schedule) getItem(position);
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_layout_schedule, parent, false);
            ButterKnife.bind(this, convertView);

            scheduleName.setText(schedule.getName());
            scheduleDateAndTime.setText(schedule.getDateAndTime());
            scheduleStatus.setText(schedule.getStatus());
        }
        return convertView;
    }
}
