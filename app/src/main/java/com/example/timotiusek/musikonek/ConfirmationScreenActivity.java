package com.example.timotiusek.musikonek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmationScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_screen);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.ok_btn__confirmation_screen_act)
    void goToLoginPage(){
        startActivity(new Intent(this, SignInActivity.class));
    }
}
