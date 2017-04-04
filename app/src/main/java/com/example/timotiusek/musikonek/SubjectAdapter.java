package com.example.timotiusek.musikonek;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TimotiusEk on 4/2/2017.
 */

public class SubjectAdapter extends BaseAdapter {
    @Nullable
    @BindView(R.id.subject_bg_all) ImageView subjectBgAll;
    @Nullable
    @BindView(R.id.subject_name_all) TextView subjectNameAll;

    @Nullable
    @BindView(R.id.subject_bg_existing) ImageView subjectBgExisting;
    @Nullable
    @BindView(R.id.subject_name_existing) TextView subjectNameExisting;
    @Nullable
    @BindView(R.id.subject_teacher_existing) TextView subjectTeacherExisting;

    @Nullable
    @BindView(R.id.subject_bg_graduated) ImageView subjectBgGraduated;
    @Nullable
    @BindView(R.id.subject_name_graduated) TextView subjectNameGraduated;
    @Nullable
    @BindView(R.id.subject_teacher_graduated) TextView subjectTeacherGraduated;
    @Nullable
    @BindView(R.id.student_image) ImageView studentImage;
    @Nullable
    @BindView(R.id.how_many_course) TextView howManyCourse;
    @Nullable
    @BindView(R.id.date_graduated) TextView dateGraduated;

    private ArrayList<Subject> subjects;
    private Context mContext;
    private LayoutInflater inflater;
    private String whichView;
    private View view;

    public SubjectAdapter(ArrayList<Subject> subjects, Context c, String whichView){
        this.subjects = subjects;
        mContext = c;
        this.whichView = whichView;
    }

    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Object getItem(int i) {
        return subjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup container) {
        final Subject subject = (Subject) getItem(i);
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            if(whichView.equals("ALL")) {
                setLayout(R.layout.row_layout_subject_all,inflater, container);

                subjectBgAll.setImageResource(subject.getImage());
                subjectNameAll.setText(subject.getName());

                if (subject.getName().length() > 6) {
                    subjectNameAll.setTextSize(20);
                }

                if (subject.getName().length() >= 9) {
                    subjectNameAll.setTextSize(16);
                }
            } else if(whichView.equals("EXISTING")){
                setLayout(R.layout.row_layout_subject_existing,inflater, container);
                subjectBgExisting.setImageResource(subject.getImage());
                subjectNameExisting.setText(subject.getName());
                subjectTeacherExisting.setText(subject.getTeacher());
            } else if(whichView.equals("GRADUATED")){
                setLayout(R.layout.row_layout_subject_graduated,inflater, container);
                subjectBgGraduated.setImageResource(subject.getImage());
                subjectNameGraduated.setText(subject.getName());
                subjectTeacherGraduated.setText(subject.getTeacher());
                studentImage.setImageResource(subject.getStudentImage());
                String howManyCourseStr = "Paket "+subject.getCoursePackage() + " Kali Pertemuan";
                howManyCourse.setText(howManyCourseStr);
                String dateGraduatedStr = "selesai pada " + subject.getDateGraduated();
                dateGraduated.setText(dateGraduatedStr);
            }
        }

        return view;
    }

    public void setLayout(int layout, LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(layout, container, false);
        ButterKnife.bind(this, view);
    }
}
