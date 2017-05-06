package com.example.timotiusek.musikonek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {
    @BindView(R.id.input_email_sign_in)
    EditText inputEmail;

    @BindView(R.id.input_password_sign_in)
    EditText inputPassword;

    @BindView(R.id.link_to_register)
    TextView linkToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sign_in_btn)
    public void signIn()
    {   Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
//        if(inputEmail.getText().toString().equals("admin") && inputPassword.getText().toString().equals("admin")){
//            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
//            startActivity(intent);
//        } else{
//            Toast.makeText(this, "Email/Password Salah", Toast.LENGTH_SHORT).show();
//        }
    }

    @OnClick(R.id.link_to_register)
    void goToRegisterPage(){
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
