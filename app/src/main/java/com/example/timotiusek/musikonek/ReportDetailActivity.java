package com.example.timotiusek.musikonek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);
        getSupportActionBar().setTitle("Detil Laporan");
        ButterKnife.bind(this);
    }

    @OnClick(R.id.close_btn__report_detail_act)
    void back(){
        super.onBackPressed();
    }
}
