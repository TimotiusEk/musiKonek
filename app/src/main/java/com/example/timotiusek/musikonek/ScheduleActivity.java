package com.example.timotiusek.musikonek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.timotiusek.musikonek.CustomClass.Schedule;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleActivity extends AppCompatActivity {
    @BindView(R.id.schedule_lv__schedule_act)
    ListView scheduleListView;

    ArrayList<Schedule> schedules;
    ScheduleAdapter scheduleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        getSupportActionBar().setTitle("Jadwal");
        ButterKnife.bind(this);
        schedules = new ArrayList<>();
        schedules.add(new Schedule("Pertemuan 1", "28 April 2017, 17:30 WIB", "Selesai"));
        schedules.add(new Schedule("Pertemuan 2", "5 Mei 2017, 17:30 WIB", "Selesai"));
        schedules.add(new Schedule("Pertemuan 3", "12 Mei 2017, 17:30 WIB", "Berjalan"));
        schedules.add(new Schedule("Pertemuan 4", "19 Mei 2017, 17:30 WIB", "Belum Mulai"));
        scheduleAdapter = new ScheduleAdapter(schedules, this);

        scheduleListView.setAdapter(scheduleAdapter);
        scheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Schedule scheduleDataToBeSent = (Schedule) scheduleAdapter.getItem(position);

                /**
                 * set bundle and send data
                 */
                startActivity(new Intent(ScheduleActivity.this, CourseReportDetailActivity.class));
            }
        });
    }
}
