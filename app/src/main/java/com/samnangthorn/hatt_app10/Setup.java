package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class Setup extends AppCompatActivity {

    private Button signUp_btt, btt_logIn, b1, b2, b3, b4, b5, b6, b7, b8;
    private ImageView logo;
    private Animation logo_motion, motion2, motion3, motion4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        signUp_btt = findViewById(R.id.btt_signUp);
        btt_logIn = findViewById(R.id.btt_logIn);
        logo = findViewById(R.id.logoView);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);

        logo_motion = AnimationUtils.loadAnimation(this, R.anim.animation);
        motion2 = AnimationUtils.loadAnimation(this, R.anim.motion2);
        motion3 = AnimationUtils.loadAnimation(this, R.anim.motion3);
        motion4 = AnimationUtils.loadAnimation(this, R.anim.motion4);
        logo.startAnimation(logo_motion);
        b1.startAnimation(motion2);
        b2.startAnimation(motion2);
        b3.startAnimation(motion3);
        b4.startAnimation(motion3);
        b5.startAnimation(motion3);
        b6.startAnimation(motion4);
        b7.startAnimation(motion4);
        b8.startAnimation(motion4);

        signUp_btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_signUpLayout();
            }
        });

        btt_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_logInLayout();
            }
        });

    }

    //Methods

    private void open_signUpLayout() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    private void open_logInLayout() {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }
}