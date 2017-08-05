package com.example.timotiusek.musikonek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseReportDetailActivity extends AppCompatActivity {
    @BindView(R.id.show_date__course_report_detail_act)
    TextView showDate;
    @BindView(R.id.show_time__course_report_detail_act)
    TextView showTime;
    @BindView(R.id.show_homework__course_report_detail_act)
    TextView showHomework;
    @BindView(R.id.show_exercise__course_report_detail_act)
    TextView showExercise;
    @BindView(R.id.show_comment__course_report_detail_act)
    TextView showComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_report_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Detil Pertemuan Kursus");

        /**
         * todo : set date --> showDate.setText("");
         */

        /**
         * todo : set time --> showTime.setText("");
         */

        /**
         * todo : set homework --> showHomework.setText("");
         */

        /**
         * todo : set exercise --> showExercise.setText("");
         */

        /**
         * todo : set comment --> showComment.setText("");
         */
    }

    @OnClick(R.id.close_btn__course_report_detail_act)
    void closeActivity(){
        super.onBackPressed();
    }
}
