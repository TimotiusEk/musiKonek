package com.example.timotiusek.musikonek.CustomClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timotiusek.musikonek.ActiveCourseActivity;
import com.example.timotiusek.musikonek.R;
import com.example.timotiusek.musikonek.ReportDetailActivity;
import com.example.timotiusek.musikonek.ScheduleActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TimotiusEk on 4/2/2017.
 */

public class SubjectAdapter extends BaseAdapter {
    @Nullable
    @BindView(R.id.background_img__subject_all_rl)
    ImageView subjectBgAll;
    @Nullable
    @BindView(R.id.subject_name__subject_all_rl)
    TextView subjectNameAll;

    @Nullable
    @BindView(R.id.background_img__subject_existing_rl)
    ImageView subjectBgExisting;
    @Nullable
    @BindView(R.id.subject_name__subject_existing_rl)
    TextView subjectNameExisting;
    @Nullable
    @BindView(R.id.teacher_name__subject_existing_rl)
    TextView subjectTeacherExisting;

    @Nullable
    @BindView(R.id.background_img__subject_graduated_rl)
    ImageView subjectBgGraduated;
    @Nullable
    @BindView(R.id.subject_name__subject_graduated_rl)
    TextView subjectNameGraduated;
    @Nullable
    @BindView(R.id.teacher_name__subject_graduated_rl)
    TextView subjectTeacherGraduated;
    @Nullable
    @BindView(R.id.student_image__subject_graduated_rl)
    ImageView studentImage;
    @Nullable
    @BindView(R.id.how_many_courses__subject_graduated_rl)
    TextView howManyCourse;
    @Nullable
    @BindView(R.id.date_graduated__subject_graduated_rl)
    TextView dateGraduated;
    @Nullable
    @BindView(R.id.show_popup_btn__subject_existing_rl)
    ImageButton showPopupExistingBtn;

    @Nullable
    @BindView(R.id.show_popup_btn__subject_graduated_rl)
    ImageButton showPopupGraduatedBtn;

    private ArrayList<Subject> subjects;
    private Context mContext;
    private LayoutInflater inflater;
    private String whichView;
    private View view;
    private Activity activity;

    public SubjectAdapter(ArrayList<Subject> subjects, Context c, String whichView, Activity activity) {
        this.subjects = subjects;
        mContext = c;
        this.whichView = whichView;
        this.activity = activity;
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
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            if (whichView.equals("ALL")) {
                setLayout(R.layout.row_layout_subject_all, inflater, container);

                subjectBgAll.setImageResource(subject.getImage());
                subjectNameAll.setText(subject.getName());

                if (subject.getName().length() > 6) {
                    subjectNameAll.setTextSize(20);
                }

                if (subject.getName().length() >= 9) {
                    subjectNameAll.setTextSize(16);
                }
            } else if (whichView.equals("EXISTING")) {
                setLayout(R.layout.row_layout_subject_existing, inflater, container);
                subjectBgExisting.setImageResource(subject.getImage());
                subjectNameExisting.setText(subject.getName());
                subjectTeacherExisting.setText(subject.getTeacher());

                showPopupExistingBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popup = new PopupMenu(mContext, view);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.browse_on_item_click_menu, popup.getMenu());
                        popup.show();
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()) {
                                    case R.id.look_report_menu:
                                    //                startActivity(new Intent(getActivity(), ReportDetailActivity.class));
//                                        Toast.makeText(activity, "Lihat Laporan " + subject.getName(), Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(activity, ReportDetailActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("courseID", subject.getId());

                                        intent.putExtras(bundle);

                                        activity.startActivity(intent);
                                        return true;
                                    case R.id.look_schedule_menu:

                                        Intent intent2 = new Intent(activity, ActiveCourseActivity.class);
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putString("courseID", subject.getId());
                                        bundle2.putString("courseName", subject.getName());


                                        intent2.putExtras(bundle2);

                                        activity.startActivity(intent2);
//                                Toast.makeText(activity, "Lihat Jadwal " + subject.getName(), Toast.LENGTH_LONG).show();
                                return true;
                            }
                                return false;
                            }
                        });
                    }
                });
            } else if (whichView.equals("GRADUATED")) {
                setLayout(R.layout.row_layout_subject_graduated, inflater, container);
                subjectBgGraduated.setImageResource(subject.getImage());
                subjectNameGraduated.setText(subject.getName());
                subjectTeacherGraduated.setText(subject.getTeacher());
                studentImage.setImageResource(subject.getStudentImage());

                String howManyCourseStr = "Paket " + subject.getCoursePackage() + " Kali Pertemuan";
                howManyCourse.setText(howManyCourseStr);
                String dateGraduatedStr = "selesai pada " + subject.getDateGraduated();
                dateGraduated.setText(dateGraduatedStr);

                showPopupGraduatedBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popup = new PopupMenu(mContext, view);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.browse_graduated_on_click_menu, popup.getMenu());
                        popup.show();
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()) {
                                    case R.id.look_report_menu:
                                        //                startActivity(new Intent(getActivity(), ReportDetailActivity.class));
                                        Toast.makeText(activity, "Lihat Laporan " + subject.getName(), Toast.LENGTH_LONG).show();
                                        return true;
                                }
                                return false;
                            }
                        });
                    }
                });
            }
        }

        return view;
    }

    public void setLayout(int layout, LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(layout, container, false);
        ButterKnife.bind(this, view);
    }

}
