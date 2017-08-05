package com.example.timotiusek.musikonek;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.timotiusek.musikonek.CustomClass.Course;
import com.example.timotiusek.musikonek.CustomClass.CourseAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CourseListSpecificTeacherActivity extends AppCompatActivity {

    @BindView(R.id.course_list_lv__course_list_specific_teacher_act)
    ListView courseListLv;

    ArrayList<Course> courses;
    CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list_specific_teacher);
        ButterKnife.bind(this);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Kursus Tersedia");
        }

        courses = new ArrayList<>();
        courses.add(new Course(R.drawable.avatar, "Budi Sunarto", "Kursus Piano Basic", "Kursus ini cocok bagi mereka yang sedang ingin belajar piano tanpa memiliki konsep dasar tentang musik. Kursus dibagi dalam enam pertemuan singkat.", 6, "Rp 45.000"));
        courses.add(new Course(R.drawable.avatar, "Budi Sunarto", "Kursus Piano Basic", "Kursus ini cocok bagi mereka yang sedang ingin belajar piano tanpa memiliki konsep dasar tentang musik. Kursus dibagi dalam enam pertemuan singkat.", 6, "Rp 45.000"));
        courses.add(new Course(R.drawable.avatar, "Budi Sunarto", "Kursus Piano Basic", "Kursus ini cocok bagi mereka yang sedang ingin belajar piano tanpa memiliki konsep dasar tentang musik. Kursus dibagi dalam enam pertemuan singkat.", 6, "Rp 45.000"));
        courses.add(new Course(R.drawable.avatar, "Budi Sunarto", "Kursus Piano Basic", "Kursus ini cocok bagi mereka yang sedang ingin belajar piano tanpa memiliki konsep dasar tentang musik. Kursus dibagi dalam enam pertemuan singkat.", 6, "Rp 45.000"));

        courseAdapter = new CourseAdapter(courses, this);
        courseListLv.setAdapter(courseAdapter);
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
}
