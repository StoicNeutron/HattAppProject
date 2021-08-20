package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AddExercise extends AppCompatActivity {

    private ImageView btt_home, btt_report, btt_exercise, btt_schedule, btt_timer, btt_setting, btt_back, btt_addNewExercise;
    private TextInputLayout dt_mainTarget, dt_subTarget, edt_exerciseName, edt_description;
    private AutoCompleteTextView autoTxt_1, autoTxt_2;
    private String[] muscleGroup_list = {"Bicep", "Tricep", "Shoulder", "Chest", "Trap.", "Abs", "Forearm", "Quadricep", "Calf", "Hamstring", "Lower Back", "Lat." };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        btt_home = findViewById(R.id.btt_home);
        btt_report = findViewById(R.id.btt_report);
        btt_exercise = findViewById(R.id.btt_exercise);
        btt_schedule = findViewById(R.id.btt_schedule);
        btt_timer = findViewById(R.id.btt_timer);
        btt_setting = findViewById(R.id.btt_setting);
        dt_mainTarget = findViewById(R.id.edt_mainTarget);
        dt_subTarget = findViewById(R.id.edt_subTarget);
        autoTxt_1 = findViewById(R.id.autoTxt_1);
        autoTxt_2 = findViewById(R.id.autoTxt_2);
        btt_back = findViewById(R.id.btt_back);
        btt_addNewExercise = findViewById(R.id.btt_addNewExercise);
        edt_exerciseName = findViewById(R.id.edt_exerciseName);
        edt_description = findViewById(R.id.edt_description);

        ArrayList<String> arrayList = new ArrayList<String>();
        for (int x = 0; x< muscleGroup_list.length; x++){
            arrayList.add(muscleGroup_list[x]);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrayList);
        autoTxt_1.setAdapter(arrayAdapter);
        autoTxt_2.setAdapter(arrayAdapter);

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

        btt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_exerciseLayout();
            }
        });

        btt_addNewExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper myDB = new DataBaseHelper(AddExercise.this);
                String exerciseName = edt_exerciseName.getEditText().getText().toString();
                String mainTarget = dt_mainTarget.getEditText().getText().toString();
                String subTarget = dt_subTarget.getEditText().getText().toString();
                String dis = edt_description.getEditText().getText().toString();

                // reset error
                edt_exerciseName.setError(null);
                dt_mainTarget.setError(null);
                dt_subTarget.setError(null);
                edt_description.setError(null);

                //validation new exercise input
                boolean addCondition = true;
                for(int x = 0 ; x < Helper.exerciseNameArray.size(); x++){
                    if(exerciseName.equalsIgnoreCase(Helper.exerciseNameArray.get(x))){
                        addCondition = false;
                        edt_exerciseName.setError("Exercise already existed");
                        break;
                    }
                }
                if(mainTarget.isEmpty()){
                    addCondition = false;
                    dt_mainTarget.setError("Require*");
                }
                if(subTarget.isEmpty()){
                    addCondition = false;
                    dt_subTarget.setError("Require*");
                }
                if(dis.isEmpty()){
                    addCondition = false;
                    edt_description.setError("Require*");
                }
                // add new exercise into the database
                if(addCondition){
                    myDB.addExercise(exerciseName, mainTarget, subTarget, dis);
                    Toast.makeText(AddExercise.this, "Added", Toast.LENGTH_SHORT).show();
                    open_exerciseLayout();
                    finish();
                }
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
        Intent intent = new Intent(this, TimerLayout.class);
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