package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PopUp_chooseWK extends AppCompatActivity {

    private SharedPreferences getData;
    private SharedPreferences.Editor editData;
    private ImageView btt_home, btt_report, btt_timer, btt_setting, btt_exercise;
    TextView txt_day, txt_month, btt_back, btt_done;
    private LinearLayout w1, w2, w3, w4, w5, w6, w7;
    private TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_choose_w_k);

        txt_day = findViewById(R.id.pop_txt_day);
        txt_month = findViewById(R.id.pop_txt_month);
        btt_back = findViewById(R.id.pop_btt_back);
        btt_done = findViewById(R.id.pop_btt_done);
        getData = getApplicationContext().getSharedPreferences("workout_data", MODE_PRIVATE);
        editData = getData.edit();

        btt_home = findViewById(R.id.btt_home);
        btt_report = findViewById(R.id.btt_report);
        btt_timer = findViewById(R.id.btt_timer);
        btt_setting = findViewById(R.id.btt_setting);
        btt_exercise = findViewById(R.id.btt_exercise);

        w1 = findViewById(R.id.wk1);
        w2 = findViewById(R.id.wk2);
        w3 = findViewById(R.id.wk3);
        w4 = findViewById(R.id.wk4);
        w5 = findViewById(R.id.wk5);
        w6 = findViewById(R.id.wk6);
        w7 = findViewById(R.id.wk7);

        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);
        txt6 = findViewById(R.id.txt6);
        txt7 = findViewById(R.id.txt7);

        // helper lists
        LinearLayout[] wList = new LinearLayout[]{w1, w2, w3, w4, w5, w6, w7};
        TextView[] txtList = new TextView[]{txt1, txt2, txt3, txt4, txt5, txt6, txt7};
        // set up view
        int totalWorkout = getData.getInt("WT", 0);
        for(int x = 0; x < totalWorkout; x++){
            wList[x].setVisibility(View.VISIBLE);
            txtList[x].setText(getData.getString("W" + (x+1), "ERROR"));
        }
        String dayString = getIntent().getStringExtra("dayString");
        txt_day.setText(dayString);
        String monthString = getIntent().getStringExtra("monthString");
        txt_month.setText(monthString);

        // on Click Listeners
        //
        btt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_homeLayout();
                transition_animation("right");
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

        btt_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_timerLayout();
                transition_animation("right");
            }
        });

        btt_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_exerciseLayout();
                transition_animation("left");
            }
        });
        btt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_scheduleLayout();
            }
        });

        btt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        wList[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectAction(0, wList);
            }
        });
        wList[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectAction(1, wList);
            }
        });
        wList[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectAction(2, wList);
            }
        });
        wList[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectAction(3, wList);
            }
        });
        wList[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectAction(4, wList);
            }
        });
        wList[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectAction(5, wList);
            }
        });
        wList[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectAction(6, wList);
            }
        });

    }

    // helper methods

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

    private void open_scheduleLayout() {
        Intent intent = new Intent(this, Schedule.class);
        startActivity(intent);
    }

    private void open_timerLayout() {
        Intent intent = new Intent(this, Timer.class);
        startActivity(intent);
    }

    private void open_exerciseLayout() {
        Intent intent = new Intent(this, Exercise.class);
        startActivity(intent);
    }

    private void transition_animation(String leftOrRight){
        if(leftOrRight.equalsIgnoreCase("right")){
            overridePendingTransition(R.anim.sa_slide_in_right, R.anim.sa_slide_out_left);
        }else if(leftOrRight.equalsIgnoreCase("left")){
            overridePendingTransition(R.anim.sa_slide_in_left, R.anim.sa_slide_out_right);
        }
    }

    private void onSelectAction(int index, LinearLayout[] wList){
        for(int x = 0; x < 7; x++){
            wList[x].setBackground(getDrawable(R.drawable.outline_black));
        }
        switch (index){
            case 0:
                wList[0].setBackground(getDrawable(R.drawable.outline_blue));
                break;
            case 1:
                wList[1].setBackground(getDrawable(R.drawable.outline_blue));
                break;
            case 2:
                wList[2].setBackground(getDrawable(R.drawable.outline_blue));
                break;
            case 3:
                wList[3].setBackground(getDrawable(R.drawable.outline_blue));
                break;
            case 4:
                wList[4].setBackground(getDrawable(R.drawable.outline_blue));
                break;
            case 5:
                wList[5].setBackground(getDrawable(R.drawable.outline_blue));
                break;
            case 6:
                wList[6].setBackground(getDrawable(R.drawable.outline_blue));
                break;

        }
    }
}