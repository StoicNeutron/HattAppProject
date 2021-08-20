package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class TimerLayout extends AppCompatActivity {

    private ImageView btt_home, btt_report, btt_exercise, btt_schedule, btt_timer, btt_setting, btt_soundSwitch;
    private TextView txt_totalTimer, btt_start, txt_wkName;
    private SharedPreferences getData;
    private SharedPreferences.Editor editData;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        btt_home = findViewById(R.id.btt_home);
        btt_report = findViewById(R.id.btt_report);
        btt_exercise = findViewById(R.id.btt_exercise);
        btt_schedule = findViewById(R.id.btt_schedule);
        btt_timer = findViewById(R.id.btt_timer);
        btt_setting = findViewById(R.id.btt_setting);
        btt_soundSwitch = findViewById(R.id.btt_soundSwitch);
        txt_totalTimer = findViewById(R.id.txt_totalTimer);
        btt_start = findViewById(R.id.btt_start);
        txt_wkName = findViewById(R.id.txt_wkName);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sound_on);
        getData = getApplicationContext().getSharedPreferences("local_data", MODE_PRIVATE);
        editData = getData.edit();

        if(Helper.timerCurrentState){
            startTimer();
        }

        dialog = new Dialog(TimerLayout.this);
        dialog.setContentView(R.layout.pop_up_select_workout);



        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        TextView btt_cancel, btt_select;
        btt_cancel = dialog.findViewById(R.id.pop_btt_back);
        btt_select = dialog.findViewById(R.id.pop_btt_select);

        btt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btt_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });


        btt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_homeLayout();
                transition_animation("left");
            }
        });

        btt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_settingLayout();
            }
        });

        btt_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_reportLayout();
                transition_animation("left");
            }
        });

        btt_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_exerciseLayout();
                transition_animation("left");
            }
        });

        btt_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_scheduleLayout();
                transition_animation("left");
            }
        });

        txt_wkName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        btt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Helper.timerCurrentState == false){
                    Helper.timer = new Timer();
                    Helper.timerCurrentState = true;
                    btt_start.setText("STOP");
                    startTimer();
                }else{
                    Helper.timerCurrentState = false;
                    btt_start.setText("START");
                    Helper.timerTask.cancel();
                }
            }
        });

        btt_soundSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getData.getString("sound", "ERROR").equalsIgnoreCase("ERROR") || getData.getString("sound", "ERROR").equalsIgnoreCase("ON")){
                    editData.putString("sound", "OFF");
                    btt_soundSwitch.setImageResource(R.drawable.ic_sound_off);
                }else{
                    editData.putString("sound", "ON");
                    btt_soundSwitch.setImageResource(R.drawable.ic_sound_on);
                }
                editData.apply();
                mediaPlayer.start();
            }
        });


    }

    // methods

    public void open_homeLayout() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void open_settingLayout() {
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }

    public void open_reportLayout() {
        Intent intent = new Intent(this, Report.class);
        startActivity(intent);
    }

    public void open_exerciseLayout() {
        Intent intent = new Intent(this, Exercise.class);
        startActivity(intent);
    }

    public void open_scheduleLayout() {
        Intent intent = new Intent(this, Schedule.class);
        startActivity(intent);
    }

    public void transition_animation(String leftOrRight){
        if(leftOrRight.equalsIgnoreCase("right")){
            overridePendingTransition(R.anim.sa_slide_in_right, R.anim.sa_slide_out_left);
        }else if(leftOrRight.equalsIgnoreCase("left")){
            overridePendingTransition(R.anim.sa_slide_in_left, R.anim.sa_slide_out_right);
        }
    }

    private void startTimer(){
        Helper.timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Helper.time++;
                        txt_totalTimer.setText(getTimerText());
                    }
                });
            }
        };
        Helper.timer.scheduleAtFixedRate(Helper.timerTask, 0, 1000);
    }

    private String getTimerText(){
        int rounded = (int) Math.round(Helper.time);
        int seconds = ((rounded % 86400) % 3600 % 60);
        int minutes = ((rounded % 86400) % 3600 / 60);
        int hours = ((rounded % 86400) / 3600);

        String timeStringFormat;
        if(minutes < 60){
            timeStringFormat = String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
        }else{
            timeStringFormat = String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
        }
        return timeStringFormat;
    }
}