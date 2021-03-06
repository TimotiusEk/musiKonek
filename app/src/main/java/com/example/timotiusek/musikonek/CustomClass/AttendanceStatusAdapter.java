package com.example.timotiusek.musikonek.CustomClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.timotiusek.musikonek.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TimotiusEk on 4/4/2017.
 */

public class AttendanceStatusAdapter extends BaseAdapter {

    private ArrayList<Subject> subjects;
    private Context mContext;
    private LayoutInflater inflater;

    @BindView(R.id.teacher_image__attendance_status_rl)
    ImageView teacherImage;

    @BindView(R.id.course_image__attendance_status_rl)
    ImageView courseImage;

    @BindView(R.id.course_name__attendance_status_rl)
    TextView courseName;

    @BindView(R.id.course_package__attendance_status_rl)
    TextView coursePackage;

    @BindView(R.id.course_time__attendance_status_rl)
    TextView courseTime;

    @BindView(R.id.teacher_name__attendance_status_rl)
    TextView attendanceTeacher;

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
        final Subject subject = (Subject) getItem(position);
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_layout_attendance_status, parent, false);
            ButterKnife.bind(this, convertView);

            teacherImage.setImageResource(subject.getTeacherImage());
            courseImage.setImageResource(subject.getImage());
            courseName.setText(subject.getName());
            String coursePackageStr = "Paket " + subject.getCoursePackage() + " kali pertemuan";
            coursePackage.setText(coursePackageStr);
            courseTime.setText(subject.getTime());
            attendanceTeacher.setText(subject.getTeacher());

        }
        return convertView;
    }
}
