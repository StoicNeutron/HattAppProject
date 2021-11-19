package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class Processing extends AppCompatActivity {

    private DataBaseHelper myDB;
    private Button p1,p2,p3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);

        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);
        p3 = findViewById(R.id.p3);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // query exercises_DATA from the database to RAM
                myDB = new DataBaseHelper(Processing.this);
                // range of random int (0 - ArrayList size)
                RAM.randomIndex = (int) (Math.random() * (RAM.getList_length()));
                transferToArrayList();
                p1.setBackground(getDrawable(R.drawable.button_bg_blue));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        p2.setBackground(getDrawable(R.drawable.button_bg_blue));

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                p3.setBackground(getDrawable(R.drawable.button_bg_blue));
                                open_HomeLayout();
                                finish();

                            }
                        },600);
                    }
                },600);
            }
        },600);

    }

    // method

    private void transferToArrayList(){
        Cursor cursor = myDB.readAllAtr();
        if(cursor.getCount() == 0){
            //None
        }else{
            while(cursor.moveToNext()){
                RAM.write_exerciseName(cursor.getString(1));
                RAM.write_mainMuscle(cursor.getString(2));
                RAM.write_subMuscle(cursor.getString(3));
                RAM.write_exerciseDescription(cursor.getString(4));
            }
        }
    }

    private void open_HomeLayout() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}