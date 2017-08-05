package com.example.timotiusek.musikonek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AttendanceVerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_verification);
        getSupportActionBar().setTitle("Verifikasi Kehadiran");
        ButterKnife.bind(this);
    }

    @OnClick(R.id.decline_btn)
    void verificationDeclined(){
        startActivity(new Intent(this, RejectAttendanceVerificationActivity.class));
    }

    @OnClick(R.id.accept_btn)
    void verificationAccepted(){
        /**
         * todo : action when verification accepted
         */
    }
}
