package com.example.timotiusek.musikonek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AttendanceVerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_verification);
        getSupportActionBar().setTitle("Verifikasi Kehadiran");
    }
}
