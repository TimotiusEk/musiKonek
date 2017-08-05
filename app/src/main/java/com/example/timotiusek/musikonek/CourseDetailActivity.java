package com.example.timotiusek.musikonek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ButterKnife.bind(this);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar__course_detail_act);
        toolbar.setTitle("Kursus Piano Pemula");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();

                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @OnClick(R.id.fab__course_detail_act)
    void openTeacherProfile(){
        Intent intent = new Intent(CourseDetailActivity.this, TeacherProfileActivity.class);
        startActivity(intent);
    }
}
