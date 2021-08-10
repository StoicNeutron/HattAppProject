package com.samnangthorn.hatt_app10;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Calendar;

public class Schedule extends AppCompatActivity{

    private ImageView btt_home, btt_report, btt_exercise, btt_schedule, btt_timer, btt_setting, btt_preMonth, btt_nextMonth;
    private TextView txt_month, btt_createWorkout, btt_editWorkout, btt_removeWorkout;
    private TextView wk1, wk2, wk3, wk4, wk5, wk6, wk7;
    private LinearLayout c1, c2, c3, c4, c5, c6, c7;
    private TextView d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, d13, d14, d15, d16, d17, d18, d19, d20, d21, d22, d23, d24, d25, d26, d27, d28, d29, d30, d31, d32;
    private TextView[] daysList = {d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, d13, d14, d15, d16, d17, d18, d19, d20, d21, d22, d23, d24, d25, d26, d27, d28, d29, d30, d31, d32};
    private @IdRes int[] dayIDList = {R.id.d1, R.id.d2, R.id.d3, R.id.d4, R.id.d5, R.id.d6, R.id.d7, R.id.d8, R.id.d9, R.id.d10,
            R.id.d11, R.id.d12, R.id.d13, R.id.d14, R.id.d15, R.id.d16, R.id.d17, R.id.d18, R.id.d19, R.id.d20,
            R.id.d21, R.id.d22, R.id.d23, R.id.d24, R.id.d25, R.id.d26, R.id.d27, R.id.d28, R.id.d29, R.id.d30, R.id.d31, R.id.d32};
    private SharedPreferences getData;
    private SharedPreferences.Editor editData;
    private Calendar realTime_data;
    private String currentMonth;
    private String initialSetMonth;
    private int currentYear;
    private int currentDay;
    private int totalWorkoutNum;
    private Dialog dialog;
    private String currentDayNum;

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
        wk2 = findViewById(R.id.wk2);
        wk3 = findViewById(R.id.wk3);
        wk4 = findViewById(R.id.wk4);
        wk5 = findViewById(R.id.wk5);
        wk6 = findViewById(R.id.wk6);
        wk7 = findViewById(R.id.wk7);

        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);
        c4 = findViewById(R.id.c4);
        c5 = findViewById(R.id.c5);
        c6 = findViewById(R.id.c6);
        c7 = findViewById(R.id.c7);
        LinearLayout[] cList = new LinearLayout[]{c1, c2, c3, c4, c5, c6, c7};
        TextView[] wkList = new TextView[]{wk1, wk2, wk3, wk4, wk5, wk6, wk7};

        btt_preMonth = findViewById(R.id.btt_previous_month);
        btt_nextMonth = findViewById(R.id.btt_next_month);
        getData = getApplicationContext().getSharedPreferences("workout_data", MODE_PRIVATE);
        editData = getData.edit();

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

        // setup view color workout
        totalWorkoutNum = getData.getInt("WT", 0);

        for(int x = 0; x < totalWorkoutNum; x++){
            cList[x].setVisibility(View.VISIBLE);
            wkList[x].setText(getData.getString("W" + (x+1), "ERROR"));
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

        // Days on Click Listeners
        //
        //
        daysList[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "01";
                open_chooseWKLayout();
            }
        });
        daysList[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "02";
                open_chooseWKLayout();
            }
        });
        daysList[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "03";
                open_chooseWKLayout();
            }
        });
        daysList[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "04";
                open_chooseWKLayout();
            }
        });
        daysList[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "05";
                open_chooseWKLayout();
            }
        });
        daysList[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "06";
                open_chooseWKLayout();
            }
        });
        daysList[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "07";
                open_chooseWKLayout();
            }
        });
        daysList[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "08";
                open_chooseWKLayout();
            }
        });
        daysList[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "09";
                open_chooseWKLayout();
            }
        });
        daysList[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "10";
                open_chooseWKLayout();
            }
        });
        daysList[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "11";
                open_chooseWKLayout();
            }
        });
        daysList[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "12";
                open_chooseWKLayout();
            }
        });
        daysList[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "13";
                open_chooseWKLayout();
            }
        });
        daysList[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "14";
                open_chooseWKLayout();
            }
        });
        daysList[14].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "15";
                open_chooseWKLayout();
            }
        });
        daysList[15].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "16";
                open_chooseWKLayout();
            }
        });
        daysList[16].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "17";
                open_chooseWKLayout();
            }
        });
        daysList[17].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "18";
                open_chooseWKLayout();
            }
        });
        daysList[18].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "19";
                open_chooseWKLayout();
            }
        });
        daysList[19].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "20";
                open_chooseWKLayout();
            }
        });
        daysList[20].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "21";
                open_chooseWKLayout();
            }
        });
        daysList[21].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "22";
                open_chooseWKLayout();
            }
        });
        daysList[22].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "23";
                open_chooseWKLayout();
            }
        });
        daysList[23].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "24";
                open_chooseWKLayout();
            }
        });
        daysList[24].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "25";
                open_chooseWKLayout();
            }
        });
        daysList[25].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "26";
                open_chooseWKLayout();
            }
        });
        daysList[26].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "27";
                open_chooseWKLayout();
            }
        });
        daysList[27].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDayNum = "28";
                open_chooseWKLayout();
            }
        });
        ////
        if(true){
            daysList[28].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentDayNum = "29";
                    open_chooseWKLayout();
                }
            });
        }
        if(true){
            daysList[29].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentDayNum = "30";
                    open_chooseWKLayout();
                }
            });
        }
        if(true){
            daysList[30].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentDayNum = "31";
                    open_chooseWKLayout();
                }
            });
        }
        if(true){
            daysList[31].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentDayNum = "32";
                    open_chooseWKLayout();
                }
            });
        }
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

    private void open_chooseWKLayout() {
        Intent intent = new Intent(this, PopUp_chooseWK.class);
        intent.putExtra("dayString", currentDayNum);
        intent.putExtra("monthString", currentMonth);
        startActivity(intent);
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