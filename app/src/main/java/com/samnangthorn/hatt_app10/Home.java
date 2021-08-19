package com.samnangthorn.hatt_app10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.timepicker.TimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class Home extends AppCompatActivity {

    private ImageView btt_report, btt_exercise, btt_schedule, btt_timer, btt_setting;
    private TextView txt_day, txt_date, txt_time, txt_timeZone, txt_workoutName;
    private Calendar realTime_data;
    private TimeZone tz;
    private DataBaseHelper myDB;
    private ArrayList<String> dateInfoList = new ArrayList<String>();
    private ArrayList<String> dateWKNameList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btt_report = findViewById(R.id.btt_report);
        btt_exercise = findViewById(R.id.btt_exercise);
        btt_schedule = findViewById(R.id.btt_schedule);
        btt_timer = findViewById(R.id.btt_timer);
        btt_setting = findViewById(R.id.btt_setting);
        txt_day = findViewById(R.id.txt_day);
        txt_date = findViewById(R.id.txt_date);
        txt_time = findViewById(R.id.txt_time);
        txt_timeZone = findViewById(R.id.txt_timeZone);
        txt_workoutName = findViewById(R.id.txt_workoutName);


        tz = TimeZone.getDefault();
        realTime_data = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(realTime_data.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        String time = simpleDateFormat.format(realTime_data.getTime());
        int n = 0;
        for(int x = 0; x < currentDate.length(); x++){
            if(currentDate.charAt(x) == 44){
                n = x;
                break;
            }
        }
        String nameofDay = currentDate.substring(0, n);
        Helper.setCurrentDayNameString(nameofDay);
        String subDate = currentDate.substring(n+1, currentDate.length());
        String year = subDate.substring(subDate.length()-2);
        // Update Helper class
        Helper.setCurrentYear(year);
        String month = "";
        for(int x = 1; x < subDate.length(); x++){
            if(subDate.charAt(x) != 32){
                month += subDate.charAt(x);
            }else{
                break;
            }
        }
        Helper.setCurrentMonth(month);

        txt_day.setText(nameofDay);
        txt_date.setText(subDate);
        txt_time.setText(time);
        txt_timeZone.setText(tz.getID());

        // read from database to array list
        myDB = new DataBaseHelper(Home.this);
        Cursor cursor = myDB.readAllDate();
        if(cursor.getCount() == 0){
            //None
        }else{
            while(cursor.moveToNext()){
                dateInfoList.add(cursor.getString(0));
                dateWKNameList.add(cursor.getString(1));
            }
        }
        // set up today workout name
        int currentDay = Integer.parseInt(Helper.getCurrentDay(currentDate));
        for(int x = 0; x < dateInfoList.size(); x++){
            if(dateInfoList.get(x).substring(2).equalsIgnoreCase(Helper.getCurrentMonthString() + currentDay)){
                txt_workoutName.setText(dateWKNameList.get(x));
            }else{
                txt_workoutName.setText("REST DAY!");
            }
        }
        // set up BMI point


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
                transition_animation("right");
            }
        });

        btt_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_timerLayout();
                transition_animation("right");
            }
        });
    }

    // methods

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

    public void open_timerLayout() {
        Intent intent = new Intent(this, Timer.class);
        startActivity(intent);
    }

    public void transition_animation(String leftOrRight){
        if(leftOrRight.equalsIgnoreCase("right")){
            overridePendingTransition(R.anim.sa_slide_in_right, R.anim.sa_slide_out_left);
        }else if(leftOrRight.equalsIgnoreCase("left")){
            overridePendingTransition(R.anim.sa_slide_in_left, R.anim.sa_slide_out_right);
        }
    }
}