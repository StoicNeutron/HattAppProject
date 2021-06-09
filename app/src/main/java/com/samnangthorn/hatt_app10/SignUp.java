package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUp extends AppCompatActivity {

    Button btt_logIn, btt_back, btt_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btt_back = (Button)findViewById(R.id.btt_back);
        btt_logIn = (Button)findViewById(R.id.btt_logIn);
        btt_signUp = (Button)findViewById(R.id.btt_signUp);

        btt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_setUpLayout();
            }
        });

        btt_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_logInLayout();
            }
        });

        btt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    //Methods

    public void open_setUpLayout() {
        Intent intent = new Intent(this, Setup.class);
        startActivity(intent);
    }

    public void open_logInLayout() {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }

    public void signUp() {
        //later
    }
}