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
 * Created by TimotiusEk on 8/5/2017.
 */

public class CourseAdapter extends BaseAdapter {
    private ArrayList<Course> courses;
    private Context mContext;
    private LayoutInflater inflater;

    @BindView(R.id.teacher_image__course_rl)
    ImageView teacherImg;

    @BindView(R.id.teacher_name__course_rl)
    TextView teacherName;

    @BindView(R.id.course_name__course_rl)
    TextView courseName;

    @BindView(R.id.course_description__course_rl)
    TextView courseDesc;

    @BindView(R.id.how_many_meetings__course_rl)
    TextView duration;

    @BindView(R.id.course_price__course_rl)
    TextView coursePrice;

    public CourseAdapter(ArrayList<Course> courses, Context c){
        this.courses = courses;
        mContext = c;
    }
    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Object getItem(int position) {
        return courses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Course course = (Course) getItem(position);
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_layout_course, parent, false);
            ButterKnife.bind(this, convertView);
            String durationToShow = course.getCourseDuration() + " kali pertemuan";

            teacherImg.setImageResource(course.getTeacherImg());
            teacherName.setText(course.getTeacherName());
            courseName.setText(course.getCourseName());
            courseDesc.setText(course.getCourseDesc());
            duration.setText(durationToShow);
            coursePrice.setText(course.getCoursePrice());
        }
        return convertView;
    }
}
