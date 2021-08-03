package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RemoveWorkout extends AppCompatActivity {

    private ImageView btt_home, btt_report, btt_timer, btt_setting, btt_exercise;
    private LinearLayout w1, w2, w3, w4, w5, w6, w7;
    private TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7;
    private TextView btt_back, btt_save;
    private ImageView d1, d2, d3, d4, d5, d6, d7;
    private SharedPreferences getData;
    private SharedPreferences.Editor editData;
    private Dialog dialog;
    private int removeIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_workout);

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

        d1 = findViewById(R.id.d1);
        d2 = findViewById(R.id.d2);
        d3 = findViewById(R.id.d3);
        d4 = findViewById(R.id.d4);
        d5 = findViewById(R.id.d5);
        d6 = findViewById(R.id.d6);
        d7 = findViewById(R.id.d7);

        btt_back = findViewById(R.id.btt_back);
        btt_save = findViewById(R.id.btt_save);
        btt_home = findViewById(R.id.btt_home);
        btt_report = findViewById(R.id.btt_report);
        btt_timer = findViewById(R.id.btt_timer);
        btt_setting = findViewById(R.id.btt_setting);
        btt_exercise = findViewById(R.id.btt_exercise);
        getData = getApplicationContext().getSharedPreferences("workout_data", MODE_PRIVATE);
        editData = getData.edit();

        // helper lists
        LinearLayout[] wList = new LinearLayout[]{w1, w2, w3, w4, w5, w6, w7};
        TextView[] txtList = new TextView[]{txt1, txt2, txt3, txt4, txt5, txt6, txt7};

        // set up view
        int totalWorkout = getData.getInt("WT", 0);
        for(int x = 0; x < totalWorkout; x++){
            wList[x].setVisibility(View.VISIBLE);
            txtList[x].setText(getData.getString("W" + (x+1), "ERROR"));
        }

        dialog = new Dialog(RemoveWorkout.this);
        dialog.setContentView(R.layout.pop_up_dialog_delete_workout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        TextView btt_cancel, btt_delete;
        btt_cancel = dialog.findViewById(R.id.pop_btt_cancel);
        btt_delete = dialog.findViewById(R.id.pop_btt_delete);

        btt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wList[removeIndex].setVisibility(View.GONE);
                if(removeIndex < totalWorkout){
                    editData.putInt("WT", totalWorkout -1);
                    for(int x = removeIndex; x < totalWorkout ; x++){
                        editData.putInt("W" + (x+1) + "eT", getData.getInt("W" + (x + 2) + "eT", 0));
                        editData.putString("W" + (x+1), getData.getString("W" + (x + 2), "ERROR Workout Name"));
                        for(int y = 0; y < getData.getInt("W" + (x + 2) + "eT", 0); y++){
                            editData.putString("W" + (x+1) + "e" + y, getData.getString("W" + (x + 2) + "e" + y, "ERROR eName"));
                            editData.putString("W" + (x+1) + "e" + y + "T", getData.getString("W" + (x + 2) + "e" + y + "T", "ERROR type"));
                            editData.putInt("W" + (x+1) + "e" + y + "s", getData.getInt("W" + (x + 2) + "e" + y + "s", 0));
                            if(getData.getString("W" + (x + 2) + "e" + y + "T", "ERROR").equalsIgnoreCase("sr")){
                                editData.putInt("W" + (x+1) + "e" + y + "t", getData.getInt("W" + (x + 2) + "e" + y + "r", 0));
                            }else{
                                editData.putInt("W" + (x+1) + "e" + y + "t", getData.getInt("W" + (x + 2) + "e" + y + "t", 0));
                            }
                        }
                    }
                }
                editData.apply();
                dialog.dismiss();
            }
        });

        // on click listener
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

        btt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeIndex = 1;
                dialog.show();
            }
        });

        d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeIndex = 2;
                dialog.show();
            }
        });

        d4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeIndex = 3;
                dialog.show();
            }
        });

        d5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeIndex = 4;
                dialog.show();
            }
        });

        d6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeIndex = 5;
                dialog.show();
            }
        });

        d7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeIndex = 6;
                dialog.show();
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
}