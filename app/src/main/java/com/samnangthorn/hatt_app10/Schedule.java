package com.samnangthorn.hatt_app10;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Calendar;

public class Schedule extends AppCompatActivity{

    private ImageView btt_home, btt_report, btt_exercise, btt_schedule, btt_timer, btt_setting, btt_preMonth, btt_nextMonth;
    private TextView txt_month, btt_createWorkout, btt_editWorkout, btt_removeWorkout;
    private TextView wk1;
    private TextView d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, d13, d14, d15, d16, d17, d18, d19, d20, d21, d22, d23, d24, d25, d26, d27, d28, d29, d30, d31, d32;
    private TextView[] daysList = {d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, d13, d14, d15, d16, d17, d18, d19, d20, d21, d22, d23, d24, d25, d26, d27, d28, d29, d30, d31, d32};
    private @IdRes int[] dayIDList = {R.id.d1, R.id.d2, R.id.d3, R.id.d4, R.id.d5, R.id.d6, R.id.d7, R.id.d8, R.id.d9, R.id.d10,
            R.id.d11, R.id.d12, R.id.d13, R.id.d14, R.id.d15, R.id.d16, R.id.d17, R.id.d18, R.id.d19, R.id.d20,
            R.id.d21, R.id.d22, R.id.d23, R.id.d24, R.id.d25, R.id.d26, R.id.d27, R.id.d28, R.id.d29, R.id.d30, R.id.d31, R.id.d32};
    private Calendar realTime_data;
    private String currentMonth;
    private String initialSetMonth;
    private int currentYear;
    private int currentDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        btt_home = findViewById(R.id.btt_home);
        btt_report = findViewById(R.id.btt_report);
        btt_exercise = findViewById(R.id.btt_exercise);
        btt_schedule = findViewById(R.id.btt_schedule);
        btt_timer = findViewById(R.id.btt_timer);
        btt_setting = findViewById(R.id.btt_setting);
        txt_month = findViewById(R.id.txt_monthYear);
        btt_createWorkout = findViewById(R.id.btt_createWorkout);
        btt_editWorkout = findViewById(R.id.btt_editWorkout);
        btt_removeWorkout = findViewById(R.id.btt_removeWorkout);
        wk1 = findViewById(R.id.wk1);
        btt_preMonth = findViewById(R.id.btt_previous_month);
        btt_nextMonth = findViewById(R.id.btt_next_month);

        // setting calendar
        currentMonth = Helper.getCurrentMonthName();
        initialSetMonth = currentMonth;
        txt_month.setText(currentMonth);

        currentYear = Integer.parseInt(Helper.getCurrentYear());
        if(Helper.leapOrNot(currentYear)){
            findViewOfThese(Helper.getLeapYearTotalDayOfMonth(Helper.getCurrentMonth()));
        }else{
            findViewOfThese(Helper.getTotalDayOfMonth(Helper.getCurrentMonth()));
        }


        realTime_data = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(realTime_data.getTime());
        currentDay = Integer.parseInt(Helper.getCurrentDay(currentDate));
        daysList[currentDay-1].setTextSize(19f);


        // on click listener
        //
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

        btt_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_timerLayout();
                transition_animation("right");
            }
        });

        btt_preMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setting calendar
                txt_month.setText(Helper.getPreMonth(currentMonth));
                // update current month
                currentMonth = Helper.getPreMonth(currentMonth);
                int currentYear = 0;
                try{
                    currentYear = Integer.parseInt(Helper.getCurrentYear());
                }catch(Exception e){
                    //
                }

                if(Helper.leapOrNot(currentYear)){
                    findViewOfThese(Helper.getLeapYearTotalDayOfPreMonth(currentMonth));
                }else{
                    findViewOfThese(Helper.getTotalDayOfPreMonth(currentMonth));
                }
                //
                if(currentMonth.equalsIgnoreCase(initialSetMonth)){
                    daysList[currentDay-1].setTextSize(19f);
                }else{
                    daysList[currentDay-1].setTextSize(14f);
                }
            }
        });

        btt_nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setting calendar
                txt_month.setText(Helper.getNextMonth(currentMonth));
                // update current month
                currentMonth = Helper.getNextMonth(currentMonth);
                int currentYear = 0;
                try{
                    currentYear = Integer.parseInt(Helper.getCurrentYear());
                }catch(Exception e){
                    //
                }

                if(Helper.leapOrNot(currentYear)){
                    findViewOfThese(Helper.getLeapYearTotalDayOfNextMonth(currentMonth));
                }else{
                    findViewOfThese(Helper.getTotalDayOfNextMonth(currentMonth));
                }
                //
                if(currentMonth.equalsIgnoreCase(initialSetMonth)){
                    daysList[currentDay-1].setTextSize(19f);
                }else{
                    daysList[currentDay-1].setTextSize(14f);
                }
            }
        });

        btt_createWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_addWorkoutLayout();
            }
        });

        btt_editWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_editWorkoutLayout();
            }
        });

        btt_removeWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_removeWorkoutLayout();
            }
        });


    }

    // methods

    private void findViewOfThese(int totalDaysInMonth){
        for(int x = 0; x < 32; x++){
            daysList[x] = findViewById(dayIDList[x]);
            if(x < totalDaysInMonth){
                if(x<9){
                    daysList[x].setText("0" + String.valueOf(x + 1));
                }else{
                    daysList[x].setText(String.valueOf(x + 1));
                }
            }else{
                daysList[x].setText("__");
            }
        }
    }

    private void open_homeLayout() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    private void open_settingLayout() {
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }

    private void open_reportLayout() {
        Intent intent = new Intent(this, Report.class);
        startActivity(intent);
    }

    private void open_exerciseLayout() {
        Intent intent = new Intent(this, Exercise.class);
        startActivity(intent);
    }

    private void open_timerLayout() {
        Intent intent = new Intent(this, Timer.class);
        startActivity(intent);
    }

    private void open_addWorkoutLayout() {
        Intent intent = new Intent(this, AddWorkout.class);
        startActivity(intent);
    }

    private void open_editWorkoutLayout() {
        Intent intent = new Intent(this, EditWorkout.class);
        startActivity(intent);
    }

    private void open_removeWorkoutLayout() {
        Intent intent = new Intent(this, RemoveWorkout.class);
        startActivity(intent);
    }

    private void transition_animation(String leftOrRight){
        if(leftOrRight.equalsIgnoreCase("right")){
            overridePendingTransition(R.anim.sa_slide_in_right, R.anim.sa_slide_out_left);
        }else if(leftOrRight.equalsIgnoreCase("left")){
            overridePendingTransition(R.anim.sa_slide_in_left, R.anim.sa_slide_out_right);
        }
    }
}