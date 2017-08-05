package com.example.timotiusek.musikonek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.timotiusek.musikonek.CustomClass.Teacher;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TimotiusEk on 4/3/2017.
 */

public class SubjectViewAllTeacherAdapter extends BaseAdapter {

    private ArrayList<Teacher> teachers;
    private Context mContext;
    private LayoutInflater inflater;

    @BindView(R.id.cost_per_meeting)
    TextView costPerMeeting;

    @BindView(R.id.taught_since)
    TextView taughtSince;

    @BindView(R.id.teacher_name)
    TextView teacherName;

    public SubjectViewAllTeacherAdapter(ArrayList<Teacher> teachers, Context c){
        this.teachers = teachers;
        mContext = c;
    }

    @Override
    public int getCount() {
        return teachers.size();
    }

    @Override
    public Object getItem(int position) {
        return teachers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Teacher teacher = (Teacher) getItem(position);
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_layout_subject_view_all_teacher, parent, false);
            ButterKnife.bind(this, convertView);

            teacherName.setText(teacher.getName());
            String costPerMeetingStr = "Rp " + NumberFormat.getNumberInstance(Locale.GERMANY).format(teacher.getCostPerMeeting());
            costPerMeeting.setText(costPerMeetingStr);
            taughtSince.setText(teacher.getHaveTaughtSince());
        }
        return convertView;
    }
}
