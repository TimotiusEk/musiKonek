package com.example.timotiusek.musikonek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TimotiusEk on 4/4/2017.
 */

public class AttendanceStatusAdapter extends BaseAdapter {

    private ArrayList<Subject> subjects;
    private Context mContext;
    private LayoutInflater inflater;

    @BindView(R.id.teacher_image)
    ImageView teacherImage;

    public AttendanceStatusAdapter(ArrayList<Subject> subjects, Context c){
        this.subjects = subjects;
        mContext = c;
    }

    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Object getItem(int position) {
        return subjects.get(position);
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
            convertView = inflater.inflate(R.layout.row_layout_attendance_status, parent, false);
            ButterKnife.bind(this, convertView);


        }
        return convertView;
    }
}
