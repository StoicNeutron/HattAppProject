package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LogIn extends AppCompatActivity {

    Button btt_logIn, btt_signUp, btt_forgotPassword;
    ImageButton btt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        btt_back = (ImageButton)findViewById(R.id.btt_back);
        btt_logIn = (Button)findViewById(R.id.btt_logIn);
        btt_signUp = (Button)findViewById(R.id.btt_signUp);
        btt_forgotPassword = (Button)findViewById(R.id.btt_forgotPassword);

        btt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_setUpLayout();
            }
        });

        btt_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });

        btt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btt_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //later
            }
        });
    }

    //Methods

    public void open_setUpLayout() {
        Intent intent = new Intent(this, Setup.class);
        startActivity(intent);
    }

    public void open_signUpLayout() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void logIn() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void forgotPassword() {
        //later
    }
}