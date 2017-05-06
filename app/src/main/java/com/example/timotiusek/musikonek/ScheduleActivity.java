package com.example.timotiusek.musikonek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleActivity extends AppCompatActivity {
    @BindView(R.id.schedule_list_view)
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
    }
}
