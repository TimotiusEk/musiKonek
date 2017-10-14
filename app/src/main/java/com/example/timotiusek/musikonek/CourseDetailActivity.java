package com.example.timotiusek.musikonek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class CourseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ButterKnife.bind(this);


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar__course_detail_act);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle params = getIntent().getExtras();

        getSupportActionBar().setTitle(params.getString("course_name"));
        toolbar.setTitle(params.getString("course_name"));

        TextView teacherName = (TextView) findViewById(R.id.teacher_name__course_detail_act);
        teacherName.setText(params.getString("teacher_name"));

        TextView courseDetail = (TextView) findViewById(R.id.course_description__course_detail_act);
        courseDetail.setText(params.getString("description"));

        CircleImageView imageView = (CircleImageView) findViewById(R.id.teacher_image__course_detail_act) ;
        imageView.setImageResource(params.getInt("image"));

        TextView appointments = (TextView) findViewById(R.id.how_many_meeting__course_detail_act);
        appointments.setText(String.valueOf(params.getInt("appointments")+" kali pertemuan"));

        TextView price = (TextView) findViewById(R.id.course_price__course_detail_act);
        price.setText(params.getString("price"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if(menuItem.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @OnClick(R.id.order_course_btn__course_detail_act)
    public void orderCourse(){
//        Intent intent = new Intent(this, SetScheduleActivity.class);
        Intent intent = new Intent(this, PlanAppointmentActivity.class);
        intent.putExtras(this.getIntent().getExtras());
        startActivity(intent);
    }
}
