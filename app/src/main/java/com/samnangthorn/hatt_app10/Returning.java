package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class Returning extends AppCompatActivity {

    private ConstraintLayout TouchLayout = null;
    private Button b1, b2, b3, b4, b5, b6, b7, b8;
    private ImageView logo;
    private Animation logo_motion, motion2, motion3, motion4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_returning);

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

        TouchLayout = findViewById(R.id.touchLayout);

        TouchLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                logo_motion = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
                motion2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.motion2);
                motion3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.motion3);
                motion4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.motion4);
                logo.startAnimation(logo_motion);
                b1.startAnimation(motion2);
                b2.startAnimation(motion2);
                b3.startAnimation(motion3);
                b4.startAnimation(motion3);
                b5.startAnimation(motion3);
                b6.startAnimation(motion4);
                b7.startAnimation(motion4);
                b8.startAnimation(motion4);
                open_ProcessingLayout();
                finish();
                return false;
            }
        });
    }

    // method

    private void open_ProcessingLayout() {
        Intent intent = new Intent(this, Processing.class);
        startActivity(intent);
    }
}