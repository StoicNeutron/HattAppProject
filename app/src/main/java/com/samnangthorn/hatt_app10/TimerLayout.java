package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.rpc.Help;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TimerLayout extends AppCompatActivity {

    private ImageView btt_home, btt_report, btt_exercise, btt_schedule, btt_timer, btt_setting, btt_soundSwitch, btt_switch, finished_img, btt_expand;
    private TextView e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, txt_totalTimer, txt_breakTimer, btt_start, txt_wkName, finished_txt, txt_wkDesTitle, txt_totalSet, txt_totalRep, txt_wkDes;
    private LinearLayout tapTimer;
    private SharedPreferences getData, getData2;
    private SharedPreferences.Editor editData, editData2;
    private Dialog dialog;
    private DataBaseHelper myDB;
    private ArrayList<String> dateInfoList = new ArrayList<String>();
    private ArrayList<String> dateWKNameList = new ArrayList<String>();
    private ArrayList<String> wkLists = new ArrayList<String>();
    private Folder folder;
    private int trigger1, trigger2, trigger3, trigger4, trigger5, trigger6, trigger7, IntKey;

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
        btt_switch = findViewById(R.id.btt_switch);
        txt_totalSet = findViewById(R.id.txt_total_set);
        txt_totalRep = findViewById(R.id.txt_total_rep);
        finished_txt =  findViewById(R.id.finished_txt);
        finished_img = findViewById(R.id.finished_img);
        txt_breakTimer = findViewById(R.id.txt_break_timer);
        tapTimer = findViewById(R.id.tap_timer);
        btt_expand = findViewById(R.id.btt_expand);
        txt_wkDes = findViewById(R.id.txt_wkDes);
        txt_wkDesTitle = findViewById(R.id.txt_wkDesTitle);
        e0 = findViewById(R.id.e0);
        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        e3 = findViewById(R.id.e3);
        e4 = findViewById(R.id.e4);
        e5 = findViewById(R.id.e5);
        e6 = findViewById(R.id.e6);
        e7 = findViewById(R.id.e7);
        e8 = findViewById(R.id.e8);
        e9 = findViewById(R.id.e9);
        e10 = findViewById(R.id.e10);
        e11 = findViewById(R.id.e11);
        e12 = findViewById(R.id.e12);

        if(!Helper.timerCurrentState){
            Helper.currentExLists.clear();
            Helper.currentSetLists.clear();
            Helper.currentRepLists.clear();
            Helper.currentTimerRepLists.clear();
            Helper.currentSetIndexRunning = 0;
            Helper.currentExeIndexRunning = 0;
            Helper.switcher = false;
            Helper.finished = false;
            Helper.time = 0;
        }

        MediaPlayer soundSwitchAudio = MediaPlayer.create(this, R.raw.sound_on);
        MediaPlayer congratsAudio = MediaPlayer.create(this, R.raw.congrate3);
        getData = getApplicationContext().getSharedPreferences("workout_data", MODE_PRIVATE);
        editData = getData.edit();
        getData2 = getApplicationContext().getSharedPreferences("local_data", MODE_PRIVATE);
        editData2 = getData2.edit();

        // Helper Lists
        TextView[] eLists = new TextView[]{e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12};

        // read workout name from the sp workout
        trigger1 = trigger2 = trigger3 = trigger4 = trigger5 = trigger6 = trigger7 = 0;
        for(int x = 0; x < getData.getInt("WT", 0); x++){
            wkLists.add(getData.getString("W" + (x+1), "ERROR"));
        }

        // reading the database
        myDB = new DataBaseHelper(TimerLayout.this);
        Cursor cursor = myDB.readAllDate();
        if(cursor.getCount() == 0){
            //None
        }else{
            while(cursor.moveToNext()){
                dateInfoList.add(cursor.getString(0));
                dateWKNameList.add(cursor.getString(1));
            }
        }
        // set up text wk name when exist by schedule
        if( Helper.currentWkNameString == null) {
            for (int x = 0; x < dateInfoList.size(); x++) {
                if (dateInfoList.get(x).equalsIgnoreCase(Helper.currentDateString)) {
                    txt_wkName.setText(dateWKNameList.get(x));
                }
            }
        }else{
            txt_wkName.setText(Helper.currentWkNameString);
        }

        String exeString = txt_wkName.getText().toString();
        if(!exeString.equalsIgnoreCase("choose workout")) {
            Helper.currentWkNameString = exeString;
            btt_start.setVisibility(View.VISIBLE);
            // auto add set and rep of warm up to Array List
            Helper.currentSetLists.add(0);
            Helper.currentRepLists.add(0);
            Helper.currentTimerRepLists.add(0);

            for (int x = 0; x < getData.getInt("WT", 0); x++) {
                if (getData.getString("W" + (x + 1), "error").equalsIgnoreCase(exeString)) {
                    // codes here
                    for (int y = 0; y <= getData.getInt("W" + (x + 1) + "eT", 0); y++) {
                        if (y != 0) {
                            eLists[y].setText(getData.getString("W" + (x + 1) + "e" + (y - 1), "error"));
                            eLists[y].setVisibility(View.VISIBLE);
                            Helper.currentExLists.add(getData.getString("W" + (x + 1) + "e" + (y - 1), "error"));
                            Helper.currentSetLists.add(getData.getInt("W" + (x + 1) + "e" + (y - 1) + "s", 0));
                            String tempVar = getData.getString("W" + (x + 1) + "e" + (y - 1) + "T", "ERROR");
                            if (tempVar.equalsIgnoreCase("st")) {
                                Helper.currentTimerRepLists.add(getData.getInt("W" + (x + 1) + "e" + (y - 1) + "t", 0));
                                Helper.currentRepLists.add(0);
                            } else {
                                Helper.currentRepLists.add(getData.getInt("W" + (x + 1) + "e" + (y - 1) + "r", 0));
                                Helper.currentTimerRepLists.add(0);
                            }
                        }
                    }
                    txt_wkDes.setText(getData.getString("WD" + (x + 1), "ERROR"));
                    IntKey = x + 1;
                    break;
                }
            }
        }

        // reset existing process timer
        if(Helper.timerCurrentState){
            startTimer();
            // set specific to blue bg
            if(Helper.currentExeIndexRunning == 1 && Helper.currentSetIndexRunning == 0) {
                eLists[Helper.currentExeIndexRunning-1].setBackground(getResources().getDrawable(R.drawable.outline_filled_blue));
            }else{
                eLists[Helper.currentExeIndexRunning].setBackground(getResources().getDrawable(R.drawable.outline_filled_blue));
            }
            String[] temp = Helper.getInstances();
            txt_totalSet.setText(temp[0]);
            txt_totalRep.setText(temp[1]);
        }

        if(Helper.timer2CurrentState){
            txt_breakTimer.setVisibility(View.VISIBLE);
            btt_start.setText("SKIP BREAK");
            startTimer2(eLists, congratsAudio);
        }

        // Dialog
        // **********************************************************************************************************************************
        dialog = new Dialog(TimerLayout.this);
        dialog.setContentView(R.layout.pop_up_select_workout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        // Dialog Variables
        ImageView btt_cancel;
        ImageView btt_wk1, btt_wk2, btt_wk3, btt_wk4, btt_wk5, btt_wk6, btt_wk7;
        btt_cancel = dialog.findViewById(R.id.pop_btt_back);
        btt_wk1 = dialog.findViewById(R.id.wk_blue);
        btt_wk2 = dialog.findViewById(R.id.wk_red);
        btt_wk3 = dialog.findViewById(R.id.wk_yellow);
        btt_wk4 = dialog.findViewById(R.id.wk_green);
        btt_wk5 = dialog.findViewById(R.id.wk_purple);
        btt_wk6 = dialog.findViewById(R.id.wk_orange);
        btt_wk7 = dialog.findViewById(R.id.wk_none);

        // Dialog OnClick listeners
        btt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btt_wk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wkLists.size() >= 1){
                    trigger2 = trigger3 = trigger4 = trigger5 = trigger6 = trigger7 = 0;
                    trigger1++;
                    if(trigger1 >= 2){
                        dialog.dismiss();
                        txt_wkName.setText(wkLists.get(0));
                        //
                        Helper.currentWkNameString = wkLists.get(0);
                        setNewWorkoutTimer(eLists, wkLists.get(0));
                        btt_start.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        btt_wk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wkLists.size() >= 2){
                    trigger1 = trigger3 = trigger4 = trigger5 = trigger6 = trigger7 = 0;
                    trigger2++;
                    if(trigger2 >= 2){
                        dialog.dismiss();
                        txt_wkName.setText(wkLists.get(1));
                        //
                        Helper.currentWkNameString = wkLists.get(1);
                        setNewWorkoutTimer(eLists, wkLists.get(1));
                        btt_start.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        btt_wk3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wkLists.size() >= 3){
                    trigger1 = trigger2 = trigger4 = trigger5 = trigger6 = trigger7 = 0;
                    trigger3++;
                    if(trigger3 >= 2){
                        dialog.dismiss();
                        txt_wkName.setText(wkLists.get(2));
                        //
                        Helper.currentWkNameString = wkLists.get(2);
                        setNewWorkoutTimer(eLists, wkLists.get(2));
                        btt_start.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        btt_wk4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wkLists.size() >= 4){
                    trigger1 = trigger2 = trigger3 = trigger5 = trigger6 = trigger7 = 0;
                    trigger4++;
                    if(trigger4 >= 2){
                        dialog.dismiss();
                        txt_wkName.setText(wkLists.get(3));
                        //
                        Helper.currentWkNameString = wkLists.get(3);
                        setNewWorkoutTimer(eLists, wkLists.get(3));
                        btt_start.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        btt_wk5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wkLists.size() >= 5){
                    trigger1 = trigger2 = trigger3 = trigger4 = trigger6 = trigger7 = 0;
                    trigger5++;
                    if(trigger5 >= 2){
                        dialog.dismiss();
                        txt_wkName.setText(wkLists.get(4));
                        //
                        Helper.currentWkNameString = wkLists.get(4);
                        setNewWorkoutTimer(eLists, wkLists.get(4));
                        btt_start.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        btt_wk6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wkLists.size() >= 6){
                    trigger1 = trigger2 = trigger3 = trigger4 = trigger5 = trigger7 = 0;
                    trigger6++;
                    if(trigger6 >= 2){
                        dialog.dismiss();
                        txt_wkName.setText(wkLists.get(5));
                        //
                        Helper.currentWkNameString = wkLists.get(5);
                        setNewWorkoutTimer(eLists, wkLists.get(5));
                        btt_start.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        btt_wk7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wkLists.size() >= 7){
                    trigger1 = trigger2 = trigger3 = trigger4 = trigger5 = trigger6 = 0;
                    trigger7++;
                    if(trigger7 >= 2){
                        dialog.dismiss();
                        txt_wkName.setText(wkLists.get(6));
                        //
                        Helper.currentWkNameString = wkLists.get(6);
                        setNewWorkoutTimer(eLists, wkLists.get(6));
                        btt_start.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        // **********************************************************************************************************************************************
        // End Dialog

        // OnClick Listeners
        //
        btt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Helper.timerCurrentState){
                    open_homeLayout();
                    transition_animation("left");
                }else{
                    Toast.makeText(TimerLayout.this, "Finish Workout Require!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Helper.timerCurrentState){
                    open_settingLayout();
                }else{
                    Toast.makeText(TimerLayout.this, "Finish Workout Require!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btt_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Helper.timerCurrentState){
                    open_reportLayout();
                    transition_animation("left");
                }else{
                    Toast.makeText(TimerLayout.this, "Finish Workout Require!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btt_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Helper.timerCurrentState){
                    open_exerciseLayout();
                    transition_animation("left");
                }else{
                    Toast.makeText(TimerLayout.this, "Finish Workout Require!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btt_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Helper.timerCurrentState){
                    open_scheduleLayout();
                    transition_animation("left");
                }else{
                    Toast.makeText(TimerLayout.this, "Finish Workout Require!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btt_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Helper.timerCurrentState){
                    dialog.show();
                }
            }
        });

        btt_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Helper.wkDesExpand == false){
                    txt_wkDes.setVisibility(View.VISIBLE);
                    txt_wkDesTitle.setVisibility(View.VISIBLE);
                    txt_wkDes.setText(getData.getString("WD" + IntKey ,"ERROR"));
                    btt_expand.setImageResource(R.drawable.ic_collapse_black);
                    Helper.wkDesExpand = true;
                }else {
                    txt_wkDes.setVisibility(View.GONE);
                    txt_wkDesTitle.setVisibility(View.GONE);
                    btt_expand.setImageResource(R.drawable.ic_expand_more_black);
                    Helper.wkDesExpand = false;
                }
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
                    btt_start.setText("DONE");
                    eLists[0].setBackground(getResources().getDrawable(R.drawable.outline_filled_blue));
                    startTimer();
                }
                //int index = Helper.indexIndex;
                if(!Helper.finished) {
                    try {
                        if (Helper.currentExeIndexRunning != 0) {
                            // setting up new view
                            for (int x = 0; x < eLists.length; x++) {
                                eLists[x].setBackground(getResources().getDrawable(R.drawable.outline_black));
                                //Helper.indexIndex = x;
                            }
                            // set specific to blue bg
                            eLists[Helper.currentExeIndexRunning].setBackground(getResources().getDrawable(R.drawable.outline_filled_blue));
                            txt_totalSet.setText("Set: " + String.valueOf(Helper.currentSetLists.get(Helper.currentExeIndexRunning) - Helper.currentSetIndexRunning));
                            if (Helper.currentRepLists.get(Helper.currentExeIndexRunning) == 0) {
                                txt_totalRep.setText("Time: " + String.valueOf(Helper.currentTimerRepLists.get(Helper.currentExeIndexRunning)) + "s");
                            } else if (Helper.currentRepLists.get(Helper.currentExeIndexRunning) > 0) {
                                txt_totalRep.setText("Rep: " + String.valueOf(Helper.currentRepLists.get(Helper.currentExeIndexRunning)));
                            }
                        }
                            // update current exercise
                            if (Helper.currentSetIndexRunning == Helper.currentSetLists.get(Helper.currentExeIndexRunning)) {
                                Helper.currentExeIndexRunning += 1;
                                // update current set
                                Helper.currentSetIndexRunning = 0;
                                //
                            } else {
                                if (Helper.switcher) {
                                    Helper.currentSetIndexRunning += 1;
                                }
                            }

                    } catch (IndexOutOfBoundsException e) {
                        btt_start.setVisibility(View.INVISIBLE);
                        Helper.finished = true;
                        finished_txt.setVisibility(View.VISIBLE);
                        finished_img.setVisibility(View.VISIBLE);
                        congratsAudio.start();
                        finishedWorkout();
                        // setting up new view
                        for (int x = 0; x < eLists.length; x++) {
                            eLists[x].setVisibility(View.GONE);
                        }
                        //
                        Helper.timerCurrentState = false;
                        Helper.timerTask.cancel();
                        // save data for report
                        folder = new Folder(getApplicationContext());
                        folder.addCompleted(Helper.currentDateString, Helper.currentWkNameString, Helper.time,Helper.currentExLists);
                    }

                    if(!Helper.switcher){
                        Helper.switcher = true;
                        Helper.timer2 = new Timer();
                        txt_breakTimer.setVisibility(View.VISIBLE);
                        Helper.timer2CurrentState = true;
                        startTimer2(eLists, congratsAudio);
                        btt_start.setText("SKIP BREAK");
                    }else{
                        Helper.switcher = false;
                        Helper.timerTask2.cancel();
                        txt_breakTimer.setText("01:00");
                        txt_breakTimer.setVisibility(View.GONE);
                        Helper.timer2CurrentState = false;
                        Helper.time2 = getData2.getInt("restTimer", 60);
                        btt_start.setText("DONE");
                    }
                }
            }
        });

        tapTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pause when timer is running
                if(Helper.timerCurrentState){
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
                    Helper.soundOn = false;
                    btt_soundSwitch.setImageResource(R.drawable.ic_sound_off);
                }else{
                    editData.putString("sound", "ON");
                    Helper.soundOn = true;
                    btt_soundSwitch.setImageResource(R.drawable.ic_sound_on);
                }
                editData.apply();
                soundSwitchAudio.start();
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

    private void startTimer2(TextView[] eLists, MediaPlayer mediaPlayer3){
        Helper.timerTask2 = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Helper.time2--;
                        txt_breakTimer.setText(getTimer2Text(eLists, mediaPlayer3));
                    }
                });
            }
        };
        Helper.timer2.scheduleAtFixedRate(Helper.timerTask2, 0, 1000);
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

    private String getTimer2Text(TextView[] eLists, MediaPlayer mediaPlayer3){
        int rounded = (int) Math.round(Helper.time2);
        int seconds = ((rounded % 86400) % 3600 % 60);
        int minutes = ((rounded % 86400) % 3600 / 60);
        int hours = ((rounded % 86400) / 3600);

        String timeStringFormat;
        timeStringFormat = String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
        if(timeStringFormat.equalsIgnoreCase("00 : 03")){
            if(Helper.soundOn){
                MediaPlayer mediaPlayer2 = MediaPlayer.create(this, R.raw.soundeffect);
                mediaPlayer2.start();
            }
        }
        if(timeStringFormat.equalsIgnoreCase("00 : 00")){
            Helper.timerTask2.cancel();
            txt_breakTimer.setVisibility(View.GONE);
            Helper.timer2CurrentState = false;
            try {
                nextExerciseOrSet(eLists);
            }catch (IndexOutOfBoundsException e){
                btt_start.setVisibility(View.INVISIBLE);
                Helper.finished = true;
                finished_txt.setVisibility(View.VISIBLE);
                finished_img.setVisibility(View.VISIBLE);
                mediaPlayer3.start();
                // setting up new view
                for (int x = 0; x < eLists.length; x++) {
                    eLists[x].setVisibility(View.GONE);
                }
                //
                Helper.timerCurrentState = false;
                Helper.timerTask.cancel();
                // save data for report
                folder = new Folder(this);
                folder.addCompleted(Helper.currentDateString, Helper.currentWkNameString, Helper.time,Helper.currentExLists);
            }
            Helper.time2 = getData2.getInt("restTimer", 60);
        }
        return timeStringFormat;
    }

    private void nextExerciseOrSet(TextView[] eLists){
        if (Helper.currentExeIndexRunning != 0) {
            // setting up new view
            for (int x = 0; x < eLists.length; x++) {
                eLists[x].setBackground(getResources().getDrawable(R.drawable.outline_black));
            }
            // set specific to blue bg
            eLists[Helper.currentExeIndexRunning].setBackground(getResources().getDrawable(R.drawable.outline_filled_blue));
            txt_totalSet.setText(String.valueOf(Helper.currentSetLists.get(Helper.currentExeIndexRunning) - Helper.currentSetIndexRunning));
            txt_totalRep.setText(String.valueOf(Helper.currentRepLists.get(Helper.currentExeIndexRunning)));
        }
        // update current exercise
        if (Helper.currentSetIndexRunning == Helper.currentSetLists.get(Helper.currentExeIndexRunning)) {
            Helper.currentExeIndexRunning += 1;
            // update current set
            Helper.currentSetIndexRunning = 0;
            //
        } else {
            if(Helper.switcher){
                Helper.currentSetIndexRunning += 1;
                Helper.switcher = false;
            }
        }
    }

    private void setNewWorkoutTimer(TextView[] eLists, String wkName){
        Helper.currentExLists.clear();
        Helper.currentSetLists.clear();
        Helper.currentRepLists.clear();
        Helper.currentTimerRepLists.clear();
        Helper.currentSetLists.add(0);
        Helper.currentRepLists.add(0);
        Helper.currentTimerRepLists.add(0);
        for (int x = 0; x < getData.getInt("WT", 0); x++){
            if(getData.getString("W" + (x + 1), "error").equalsIgnoreCase(wkName) ){
                // codes here
                for(int y = 0; y <= getData.getInt("W" + (x + 1) + "eT", 0); y++){
                    if(y != 0){
                        eLists[y].setText(getData.getString("W" + (x + 1) + "e" + (y - 1), "error"));
                        eLists[y].setVisibility(View.VISIBLE);
                        Helper.currentExLists.add(getData.getString("W" + (x + 1) + "e" + (y - 1), "error"));
                        Helper.currentSetLists.add(getData.getInt("W" + (x + 1) + "e" + (y - 1) + "s", 0));
                        String tempVar = getData.getString("W" + (x + 1) + "e" + (y - 1) + "T", "ERROR");
                        if(tempVar.equalsIgnoreCase("st")){
                            Helper.currentTimerRepLists.add(getData.getInt("W" + (x + 1) + "e" + (y - 1) + "t", 0));
                            Helper.currentRepLists.add(0);
                        }else{
                            Helper.currentRepLists.add(getData.getInt("W" + (x + 1) + "e" + (y - 1) + "r", 0));
                            Helper.currentTimerRepLists.add(0);
                        }
                    }
                }
                break;
            }
        }
    }

    private void finishedWorkout(){
        myDB.deleteThisWorkout("210905");
        myDB.addDate("210905", txt_wkName.getText().toString(), "C", "Added Note");
    }
}