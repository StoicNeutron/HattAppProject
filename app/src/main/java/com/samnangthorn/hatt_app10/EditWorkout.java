package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditWorkout extends AppCompatActivity implements RVAdapter.onExeClickListener{

    private ImageView btt_home, btt_report, btt_timer, btt_setting, btt_exercise, btt_back;
    private SearchView searchView;
    private ArrayList<String> exerciseName;
    private DataBaseHelper myDB;
    private RecyclerView recyclerView;
    private LinearLayout selectedE1, selectedE2, selectedE3, selectedE4, selectedE5, selectedE6, selectedE7, selectedE8, selectedE9, selectedE10, selectedE11, selectedE12;
    private TextView workoutName, btt_save, Ex1, Ex2, Ex3, Ex4, Ex5, Ex6, Ex7, Ex8, Ex9, Ex10, Ex11, Ex12, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12;
    private ImageView DE1, DE2, DE3, DE4, DE5, DE6, DE7, DE8, DE9, DE10, DE11, DE12, btt_right, btt_left;
    private EditText workoutDes;
    private EditText es1, es2, es3, es4, es5, es6, es7, es8, es9, es10, es11, es12, er1, er2, er3, er4, er5, er6, er7,  er8, er9, er10, er11, er12;
    private String[] eNameListedIn = new String[12];
    private SharedPreferences getData;
    private SharedPreferences.Editor editData;
    private int currentExerciseIndex = 0;
    private int currentWorkoutIndex = 0;
    private String[] W_num_keySet = new String[]{"W1", "W2", "W3", "W4", "W5", "W6", "W7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        // find views by ID

        btt_home = findViewById(R.id.btt_home);
        btt_report = findViewById(R.id.btt_report);
        btt_timer = findViewById(R.id.btt_timer);
        btt_back = findViewById(R.id.btt_back);
        btt_setting = findViewById(R.id.btt_setting);
        btt_exercise = findViewById(R.id.btt_exercise);
        searchView = findViewById(R.id.search_bar);
        recyclerView = findViewById(R.id.recycleView);
        btt_save = findViewById(R.id.btt_add);
        workoutName = findViewById(R.id.txt_workoutName);
        workoutDes = findViewById(R.id.edt_description);
        btt_left = findViewById(R.id.btt_left);
        btt_right = findViewById(R.id.btt_right);
        exerciseName = new ArrayList<String>();
        myDB = new DataBaseHelper(EditWorkout.this);
        getData = getApplicationContext().getSharedPreferences("workout_data", MODE_PRIVATE);
        editData = getData.edit();

        selectedE1 = findViewById(R.id.selectedEx1);
        selectedE2 = findViewById(R.id.selectedEx2);
        selectedE3 = findViewById(R.id.selectedEx3);
        selectedE4 = findViewById(R.id.selectedEx4);
        selectedE5 = findViewById(R.id.selectedEx5);
        selectedE6 = findViewById(R.id.selectedEx6);
        selectedE7 = findViewById(R.id.selectedEx7);
        selectedE8 = findViewById(R.id.selectedEx8);
        selectedE9 = findViewById(R.id.selectedEx9);
        selectedE10 = findViewById(R.id.selectedEx10);
        selectedE11 = findViewById(R.id.selectedEx11);
        selectedE12 = findViewById(R.id.selectedEx12);

        Ex1 = findViewById(R.id.Ex1);
        Ex2 = findViewById(R.id.Ex2);
        Ex3 = findViewById(R.id.Ex3);
        Ex4 = findViewById(R.id.Ex4);
        Ex5 = findViewById(R.id.Ex5);
        Ex6 = findViewById(R.id.Ex6);
        Ex7 = findViewById(R.id.Ex7);
        Ex8 = findViewById(R.id.Ex8);
        Ex9 = findViewById(R.id.Ex9);
        Ex10 = findViewById(R.id.Ex10);
        Ex11 = findViewById(R.id.Ex11);
        Ex12 = findViewById(R.id.Ex12);

        DE1 = findViewById(R.id.DE1);
        DE2 = findViewById(R.id.DE2);
        DE3 = findViewById(R.id.DE3);
        DE4 = findViewById(R.id.DE4);
        DE5 = findViewById(R.id.DE5);
        DE6 = findViewById(R.id.DE6);
        DE7 = findViewById(R.id.DE7);
        DE8 = findViewById(R.id.DE8);
        DE9 = findViewById(R.id.DE9);
        DE10 = findViewById(R.id.DE10);
        DE11 = findViewById(R.id.DE11);
        DE12 = findViewById(R.id.DE12);

        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);
        t5 = findViewById(R.id.t5);
        t6 = findViewById(R.id.t6);
        t7 = findViewById(R.id.t7);
        t8 = findViewById(R.id.t8);
        t9 = findViewById(R.id.t9);
        t10 = findViewById(R.id.t10);
        t11 = findViewById(R.id.t11);
        t12 = findViewById(R.id.t12);

        s1 = findViewById(R.id.s1);
        s2 = findViewById(R.id.s2);
        s3 = findViewById(R.id.s3);
        s4 = findViewById(R.id.s4);
        s5 = findViewById(R.id.s5);
        s6 = findViewById(R.id.s6);
        s7 = findViewById(R.id.s7);
        s8 = findViewById(R.id.s8);
        s9 = findViewById(R.id.s9);
        s10 = findViewById(R.id.s10);
        s11 = findViewById(R.id.s11);
        s12 = findViewById(R.id.s12);

        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);
        c4 = findViewById(R.id.c4);
        c5 = findViewById(R.id.c5);
        c6 = findViewById(R.id.c6);
        c7 = findViewById(R.id.c7);
        c8 = findViewById(R.id.c8);
        c9 = findViewById(R.id.c9);
        c10 = findViewById(R.id.c10);
        c11 = findViewById(R.id.c11);
        c12 = findViewById(R.id.c12);

        es1 = findViewById(R.id.e1s);
        es2 = findViewById(R.id.e2s);
        es3 = findViewById(R.id.e3s);
        es4 = findViewById(R.id.e4s);
        es5 = findViewById(R.id.e5s);
        es6 = findViewById(R.id.e6s);
        es7 = findViewById(R.id.e7s);
        es8 = findViewById(R.id.e8s);
        es9 = findViewById(R.id.e9s);
        es10 = findViewById(R.id.e10s);
        es11 = findViewById(R.id.e11s);
        es12 = findViewById(R.id.e12s);

        er1 = findViewById(R.id.e1r);
        er2 = findViewById(R.id.e2r);
        er3 = findViewById(R.id.e3r);
        er4 = findViewById(R.id.e4r);
        er5 = findViewById(R.id.e5r);
        er6 = findViewById(R.id.e6r);
        er7 = findViewById(R.id.e7r);
        er8 = findViewById(R.id.e8r);
        er9 = findViewById(R.id.e9r);
        er10 = findViewById(R.id.e10r);
        er11 = findViewById(R.id.e11r);
        er12 = findViewById(R.id.e12r);

        // END find views by ID

        // Helper lists
        LinearLayout[] LayoutSelectedE = new LinearLayout[]{selectedE1, selectedE2, selectedE3, selectedE4, selectedE5, selectedE6, selectedE7, selectedE8, selectedE9, selectedE10, selectedE11, selectedE12};
        TextView[] eNameList = new TextView[]{Ex1, Ex2, Ex3, Ex4, Ex5, Ex6, Ex7, Ex8, Ex9, Ex10, Ex11, Ex12};
        TextView[] cList = new TextView[]{c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12};
        EditText[] sList = new EditText[]{es1, es2, es3, es4, es5, es6, es7, es8, es9, es10, es11, es12};
        EditText[] rList = new EditText[]{er1, er2, er3, er4, er5, er6, er7,  er8, er9, er10, er11, er12};
        TextView[] tList = new TextView[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12};
        TextView[] sTList = new TextView[]{s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12};

        // query the database to ArrayList
        transferToArrayList();

        // setup recycle view
        RVAdapter rvAdapter = new RVAdapter(this, exerciseName, this);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // set up existing workout view
        int sp_workoutTotal = getData.getInt("WT", 0);
        String wKName = getData.getString(W_num_keySet[currentWorkoutIndex], "ERROR");
        // *Workout Name
        workoutName.setText(wKName);
        // *Exercises Name
        for(int x = 0; x < getData.getInt(W_num_keySet[currentWorkoutIndex]+"eT", 0); x++){
            LayoutSelectedE[x].setVisibility(View.VISIBLE);
            String retrieveKey = W_num_keySet[currentWorkoutIndex] + "e" + String.valueOf(x);
            String inputString = getData.getString(retrieveKey, "ERROR");
            eNameList[x].setText(inputString);
            sList[x].setText(String.valueOf(getData.getInt(retrieveKey + "s", 0)));
            if(getData.getString(retrieveKey + "T", "ERROR").equalsIgnoreCase("sr")){
                rList[x].setText(String.valueOf(getData.getInt(retrieveKey + "r", 0)));
            }else{
                rList[x].setText(String.valueOf(getData.getInt(retrieveKey + "t", 0)));
                tList[x].setTextColor(getColor(R.color.white));
                tList[x].setBackground(getDrawable(R.drawable.outline_filled_black));
                cList[x].setText("SECOND:   ");
                sTList[x].setTextColor(getColor(R.color.black));
                sTList[x].setBackground(getDrawable(R.drawable.outline_filled_none));
            }
            eNameListedIn[currentExerciseIndex] = inputString;
            currentExerciseIndex +=1;
        }
        String keyDes = "WD" + String.valueOf(currentWorkoutIndex+1);
        // *Description
        workoutDes.setText(getData.getString(keyDes, "ERROR"));


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

        btt_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set up existing workout view

                if(sp_workoutTotal>1){
                    if(currentWorkoutIndex < sp_workoutTotal - 1){
                        currentWorkoutIndex += 1;
                    }else if(currentWorkoutIndex >= sp_workoutTotal - 1){
                        currentWorkoutIndex = 0;
                    }

                    String wKName = getData.getString(W_num_keySet[currentWorkoutIndex], "ERROR");
                    workoutName.setText(wKName);

                    if(currentExerciseIndex <= getData.getInt(W_num_keySet[currentWorkoutIndex]+"eT", 0)){

                        currentExerciseIndex = 0;
                        for(int x = 0; x < getData.getInt(W_num_keySet[currentWorkoutIndex]+"eT", 0); x++){
                            LayoutSelectedE[x].setVisibility(View.VISIBLE);
                            String retrieveKey = W_num_keySet[currentWorkoutIndex] + "e" + String.valueOf(x);
                            String inputString = getData.getString(retrieveKey, "ERROR");
                            eNameList[x].setText(inputString);
                            sList[x].setText(String.valueOf(getData.getInt(retrieveKey + "s", 0)));
                            if(getData.getString(retrieveKey + "T", "ERROR").equalsIgnoreCase("sr")){
                                rList[x].setText(String.valueOf(getData.getInt(retrieveKey + "r", 0)));
                                tList[x].setTextColor(getColor(R.color.black));
                                tList[x].setBackground(getDrawable(R.drawable.outline_filled_none));
                                cList[x].setText("REP:   ");
                                sTList[x].setTextColor(getColor(R.color.white));
                                sTList[x].setBackground(getDrawable(R.drawable.outline_filled_black));
                            }else{
                                rList[x].setText(String.valueOf(getData.getInt(retrieveKey + "t", 0)));
                                tList[x].setTextColor(getColor(R.color.white));
                                tList[x].setBackground(getDrawable(R.drawable.outline_filled_black));
                                cList[x].setText("SECOND:   ");
                                sTList[x].setTextColor(getColor(R.color.black));
                                sTList[x].setBackground(getDrawable(R.drawable.outline_filled_none));
                            }
                            eNameListedIn[currentExerciseIndex] = inputString;
                            currentExerciseIndex +=1;
                        }
                        String keyDes = "WD" + String.valueOf(currentWorkoutIndex+1);
                        // *Description
                        workoutDes.setText(getData.getString(keyDes, "ERROR"));
                    }else if(currentExerciseIndex > getData.getInt(W_num_keySet[currentWorkoutIndex]+"eT", 0)){

                        int tempNum = currentExerciseIndex;
                        currentExerciseIndex = 0;
                        for(int x = 0; x < getData.getInt(W_num_keySet[currentWorkoutIndex]+"eT", 0); x++){
                            LayoutSelectedE[x].setVisibility(View.VISIBLE);
                            String retrieveKey = W_num_keySet[currentWorkoutIndex] + "e" + String.valueOf(x);
                            String inputString = getData.getString(retrieveKey, "ERROR");
                            eNameList[x].setText(inputString);
                            sList[x].setText(String.valueOf(getData.getInt(retrieveKey + "s", 0)));
                            if(getData.getString(retrieveKey + "T", "ERROR").equalsIgnoreCase("sr")){
                                rList[x].setText(String.valueOf(getData.getInt(retrieveKey + "r", 0)));
                                tList[x].setTextColor(getColor(R.color.black));
                                tList[x].setBackground(getDrawable(R.drawable.outline_filled_none));
                                cList[x].setText("REP:   ");
                                sTList[x].setTextColor(getColor(R.color.white));
                                sTList[x].setBackground(getDrawable(R.drawable.outline_filled_black));
                            }else{
                                rList[x].setText(String.valueOf(getData.getInt(retrieveKey + "t", 0)));
                                tList[x].setTextColor(getColor(R.color.white));
                                tList[x].setBackground(getDrawable(R.drawable.outline_filled_black));
                                cList[x].setText("SECOND:   ");
                                sTList[x].setTextColor(getColor(R.color.black));
                                sTList[x].setBackground(getDrawable(R.drawable.outline_filled_none));
                            }
                            eNameListedIn[currentExerciseIndex] = inputString;
                            currentExerciseIndex +=1;
                        }
                        String keyDes = "WD" + String.valueOf(currentWorkoutIndex+1);
                        // *Description
                        workoutDes.setText(getData.getString(keyDes, "ERROR"));
                        for(int y = getData.getInt(W_num_keySet[currentWorkoutIndex]+"eT", 0); y < tempNum+1; y++){
                            LayoutSelectedE[y].setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

        btt_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    // set up existing workout view

                    if(sp_workoutTotal >1){
                        if(currentWorkoutIndex > 0){
                            currentWorkoutIndex -= 1;
                        }else if(currentWorkoutIndex <= 0){
                            currentWorkoutIndex = sp_workoutTotal - 1;
                        }

                        String wKName = getData.getString(W_num_keySet[currentWorkoutIndex], "ERROR");
                        workoutName.setText(wKName);

                        if(currentExerciseIndex <= getData.getInt(W_num_keySet[currentWorkoutIndex]+"eT", 0)){

                            currentExerciseIndex = 0;
                            for(int x = 0; x < getData.getInt(W_num_keySet[currentWorkoutIndex]+"eT", 0); x++){
                                LayoutSelectedE[x].setVisibility(View.VISIBLE);
                                String retrieveKey = W_num_keySet[currentWorkoutIndex] + "e" + String.valueOf(x);
                                String inputString = getData.getString(retrieveKey, "ERROR");
                                eNameList[x].setText(inputString);
                                sList[x].setText(String.valueOf(getData.getInt(retrieveKey + "s", 0)));
                                if(getData.getString(retrieveKey + "T", "ERROR").equalsIgnoreCase("sr")){
                                    rList[x].setText(String.valueOf(getData.getInt(retrieveKey + "r", 0)));
                                    tList[x].setTextColor(getColor(R.color.black));
                                    tList[x].setBackground(getDrawable(R.drawable.outline_filled_none));
                                    cList[x].setText("REP:   ");
                                    sTList[x].setTextColor(getColor(R.color.white));
                                    sTList[x].setBackground(getDrawable(R.drawable.outline_filled_black));
                                }else{
                                    rList[x].setText(String.valueOf(getData.getInt(retrieveKey + "t", 0)));
                                    tList[x].setTextColor(getColor(R.color.white));
                                    tList[x].setBackground(getDrawable(R.drawable.outline_filled_black));
                                    cList[x].setText("SECOND:   ");
                                    sTList[x].setTextColor(getColor(R.color.black));
                                    sTList[x].setBackground(getDrawable(R.drawable.outline_filled_none));
                                }
                                eNameListedIn[currentExerciseIndex] = inputString;
                                currentExerciseIndex +=1;
                            }
                            String keyDes = "WD" + String.valueOf(currentWorkoutIndex+1);
                            // *Description
                            workoutDes.setText(getData.getString(keyDes, "ERROR"));
                        }else if(currentExerciseIndex > getData.getInt(W_num_keySet[currentWorkoutIndex]+"eT", 0)){

                            int tempNum = currentExerciseIndex;
                            currentExerciseIndex = 0;
                            for(int x = 0; x < getData.getInt(W_num_keySet[currentWorkoutIndex]+"eT", 0); x++){
                                LayoutSelectedE[x].setVisibility(View.VISIBLE);
                                String retrieveKey = W_num_keySet[currentWorkoutIndex] + "e" + String.valueOf(x);
                                String inputString = getData.getString(retrieveKey, "ERROR");
                                eNameList[x].setText(inputString);
                                sList[x].setText(String.valueOf(getData.getInt(retrieveKey + "s", 0)));
                                if(getData.getString(retrieveKey + "T", "ERROR").equalsIgnoreCase("sr")){
                                    rList[x].setText(String.valueOf(getData.getInt(retrieveKey + "r", 0)));
                                    tList[x].setTextColor(getColor(R.color.black));
                                    tList[x].setBackground(getDrawable(R.drawable.outline_filled_none));
                                    cList[x].setText("REP:   ");
                                    sTList[x].setTextColor(getColor(R.color.white));
                                    sTList[x].setBackground(getDrawable(R.drawable.outline_filled_black));
                                }else{
                                    rList[x].setText(String.valueOf(getData.getInt(retrieveKey + "t", 0)));
                                    tList[x].setTextColor(getColor(R.color.white));
                                    tList[x].setBackground(getDrawable(R.drawable.outline_filled_black));
                                    cList[x].setText("SECOND:   ");
                                    sTList[x].setTextColor(getColor(R.color.black));
                                    sTList[x].setBackground(getDrawable(R.drawable.outline_filled_none));
                                }
                                eNameListedIn[currentExerciseIndex] = inputString;
                                currentExerciseIndex +=1;
                            }
                            String keyDes = "WD" + String.valueOf(currentWorkoutIndex+1);
                            // *Description
                            workoutDes.setText(getData.getString(keyDes, "ERROR"));
                            for(int y = getData.getInt(W_num_keySet[currentWorkoutIndex]+"eT", 0); y < tempNum+1; y++){
                                LayoutSelectedE[y].setVisibility(View.GONE);
                            }
                        }
                    }
                }

            }
        });

        btt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String workout_des = workoutDes.getText().toString();

                    // validation at least 1 exercise in a workout
                    if(currentExerciseIndex > 0){
                        String newKey_W_num = "W" + String.valueOf(currentWorkoutIndex+1);
                        String newKey_WD_num = "WD" + String.valueOf(currentWorkoutIndex+1);

                        // workout description
                        if(workout_des.isEmpty()){
                            editData.putString(newKey_WD_num, "NONE");
                        }else{
                            editData.putString(newKey_WD_num, workout_des);
                        }
                        // workout exercises
                        editData.putInt(newKey_W_num + "eT", currentExerciseIndex);
                        // generate key
                        String newKey_W_num_e_num;
                        //ArrayList<String> keyArray = new ArrayList<String>();
                        for(int x = 0; x < currentExerciseIndex; x++){
                            newKey_W_num_e_num = newKey_W_num + "e" + String.valueOf(x);
                            //keyArray.add(newKey_W_num_e_num);
                            editData.putString(newKey_W_num_e_num, eNameListedIn[x]);
                            editData.putInt(newKey_W_num_e_num + "s", Integer.valueOf(sList[x].getText().toString()));
                            if(cList[x].getText().toString().contains("REP")){
                                editData.putInt(newKey_W_num_e_num + "r", Integer.parseInt(rList[x].getText().toString()));
                                editData.putString(newKey_W_num_e_num + "T", "sr");
                            }else{
                                editData.putInt(newKey_W_num_e_num + "t", Integer.parseInt(rList[x].getText().toString()));
                                editData.putString(newKey_W_num_e_num + "T", "st");
                            }
                            editData.apply();
                        }
                        String keyDes = "WD" + String.valueOf(currentWorkoutIndex+1);
                        // *Description
                        editData.putString(keyDes, workoutDes.getText().toString());
                        Toast.makeText(EditWorkout.this, "Save Successful", Toast.LENGTH_SHORT).show();
                        open_scheduleLayout();
                    }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerView.setVisibility(View.VISIBLE);
                // query part
                ArrayList<String> tempArray = new ArrayList<String>();
                for(int x = 0; x < exerciseName.size(); x++){
                    if(exerciseName.get(x).toLowerCase().contains(newText.toLowerCase())){
                        tempArray.add(exerciseName.get(x));
                    }
                }
                // update recycle view
                RVAdapter rvAdapter = new RVAdapter(getApplicationContext(), tempArray, EditWorkout.this::onExeClick);
                recyclerView.setAdapter(rvAdapter);

                return false;
            }
        });

        DE1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExerciseIndex -= 1;
                LayoutSelectedE[currentExerciseIndex].setVisibility(View.GONE);
                reSortingIndex(0);
            }
        });

        DE2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExerciseIndex -= 1;
                LayoutSelectedE[currentExerciseIndex].setVisibility(View.GONE);
                reSortingIndex(1);
            }
        });

        DE3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExerciseIndex -= 1;
                LayoutSelectedE[currentExerciseIndex].setVisibility(View.GONE);
                reSortingIndex(2);
            }
        });

        DE4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExerciseIndex -= 1;
                LayoutSelectedE[currentExerciseIndex].setVisibility(View.GONE);
                reSortingIndex(3);
            }
        });

        DE5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExerciseIndex -= 1;
                LayoutSelectedE[currentExerciseIndex].setVisibility(View.GONE);
                reSortingIndex(4);
            }
        });

        DE6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExerciseIndex -= 1;
                LayoutSelectedE[currentExerciseIndex].setVisibility(View.GONE);
                reSortingIndex(5);
            }
        });

        DE7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExerciseIndex -= 1;
                LayoutSelectedE[currentExerciseIndex].setVisibility(View.GONE);
                reSortingIndex(6);
            }
        });

        DE8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExerciseIndex -= 1;
                LayoutSelectedE[currentExerciseIndex].setVisibility(View.GONE);
                reSortingIndex(7);
            }
        });

        DE9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExerciseIndex -= 1;
                LayoutSelectedE[currentExerciseIndex].setVisibility(View.GONE);
                reSortingIndex(8);
            }
        });

        DE10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExerciseIndex -= 1;
                LayoutSelectedE[currentExerciseIndex].setVisibility(View.GONE);
                reSortingIndex(9);
            }
        });

        DE11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExerciseIndex -= 1;
                LayoutSelectedE[currentExerciseIndex].setVisibility(View.GONE);
                reSortingIndex(10);
            }
        });

        DE12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExerciseIndex -= 1;
                LayoutSelectedE[currentExerciseIndex].setVisibility(View.GONE);
                reSortingIndex(11);
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.setTextColor(getColor(R.color.white));
                t1.setBackground(getDrawable(R.drawable.outline_filled_black));
                c1.setText("SECOND:   ");
                s1.setTextColor(getColor(R.color.black));
                s1.setBackground(getDrawable(R.drawable.outline_filled_none));
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t2.setTextColor(getColor(R.color.white));
                t2.setBackground(getDrawable(R.drawable.outline_filled_black));
                c2.setText("SECOND:   ");
                s2.setTextColor(getColor(R.color.black));
                s2.setBackground(getDrawable(R.drawable.outline_filled_none));
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t3.setTextColor(getColor(R.color.white));
                t3.setBackground(getDrawable(R.drawable.outline_filled_black));
                c3.setText("SECOND:   ");
                s3.setTextColor(getColor(R.color.black));
                s3.setBackground(getDrawable(R.drawable.outline_filled_none));
            }
        });

        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t4.setTextColor(getColor(R.color.white));
                t4.setBackground(getDrawable(R.drawable.outline_filled_black));
                c4.setText("SECOND:   ");
                s4.setTextColor(getColor(R.color.black));
                s4.setBackground(getDrawable(R.drawable.outline_filled_none));
            }
        });

        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t5.setTextColor(getColor(R.color.white));
                t5.setBackground(getDrawable(R.drawable.outline_filled_black));
                c5.setText("SECOND:   ");
                s5.setTextColor(getColor(R.color.black));
                s5.setBackground(getDrawable(R.drawable.outline_filled_none));
            }
        });

        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t6.setTextColor(getColor(R.color.white));
                t6.setBackground(getDrawable(R.drawable.outline_filled_black));
                c6.setText("SECOND:   ");
                s6.setTextColor(getColor(R.color.black));
                s6.setBackground(getDrawable(R.drawable.outline_filled_none));
            }
        });

        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t7.setTextColor(getColor(R.color.white));
                t7.setBackground(getDrawable(R.drawable.outline_filled_black));
                c7.setText("SECOND:   ");
                s7.setTextColor(getColor(R.color.black));
                s7.setBackground(getDrawable(R.drawable.outline_filled_none));
            }
        });

        t8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t8.setTextColor(getColor(R.color.white));
                t8.setBackground(getDrawable(R.drawable.outline_filled_black));
                c8.setText("SECOND:   ");
                s8.setTextColor(getColor(R.color.black));
                s8.setBackground(getDrawable(R.drawable.outline_filled_none));
            }
        });

        t9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t9.setTextColor(getColor(R.color.white));
                t9.setBackground(getDrawable(R.drawable.outline_filled_black));
                c9.setText("SECOND:   ");
                s9.setTextColor(getColor(R.color.black));
                s9.setBackground(getDrawable(R.drawable.outline_filled_none));
            }
        });

        t10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t10.setTextColor(getColor(R.color.white));
                t10.setBackground(getDrawable(R.drawable.outline_filled_black));
                c10.setText("SECOND:   ");
                s10.setTextColor(getColor(R.color.black));
                s10.setBackground(getDrawable(R.drawable.outline_filled_none));
            }
        });

        t11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t11.setTextColor(getColor(R.color.white));
                t11.setBackground(getDrawable(R.drawable.outline_filled_black));
                c11.setText("SECOND:   ");
                s11.setTextColor(getColor(R.color.black));
                s11.setBackground(getDrawable(R.drawable.outline_filled_none));
            }
        });

        t12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t12.setTextColor(getColor(R.color.white));
                t12.setBackground(getDrawable(R.drawable.outline_filled_black));
                c12.setText("SECOND:   ");
                s12.setTextColor(getColor(R.color.black));
                s12.setBackground(getDrawable(R.drawable.outline_filled_none));
            }
        });

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.setTextColor(getColor(R.color.black));
                t1.setBackground(getDrawable(R.drawable.outline_filled_none));
                c1.setText("REP:   ");
                s1.setTextColor(getColor(R.color.white));
                s1.setBackground(getDrawable(R.drawable.outline_filled_black));
            }
        });

        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t2.setTextColor(getColor(R.color.black));
                t2.setBackground(getDrawable(R.drawable.outline_filled_none));
                c2.setText("REP:   ");
                s2.setTextColor(getColor(R.color.white));
                s2.setBackground(getDrawable(R.drawable.outline_filled_black));
            }
        });

        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t3.setTextColor(getColor(R.color.black));
                t3.setBackground(getDrawable(R.drawable.outline_filled_none));
                c3.setText("REP:   ");
                s3.setTextColor(getColor(R.color.white));
                s3.setBackground(getDrawable(R.drawable.outline_filled_black));
            }
        });

        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t4.setTextColor(getColor(R.color.black));
                t4.setBackground(getDrawable(R.drawable.outline_filled_none));
                c4.setText("REP:   ");
                s4.setTextColor(getColor(R.color.white));
                s4.setBackground(getDrawable(R.drawable.outline_filled_black));
            }
        });

        s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t5.setTextColor(getColor(R.color.black));
                t5.setBackground(getDrawable(R.drawable.outline_filled_none));
                c5.setText("REP:   ");
                s5.setTextColor(getColor(R.color.white));
                s5.setBackground(getDrawable(R.drawable.outline_filled_black));
            }
        });

        s6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t6.setTextColor(getColor(R.color.black));
                t6.setBackground(getDrawable(R.drawable.outline_filled_none));
                c6.setText("REP:   ");
                s6.setTextColor(getColor(R.color.white));
                s6.setBackground(getDrawable(R.drawable.outline_filled_black));
            }
        });

        s7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t7.setTextColor(getColor(R.color.black));
                t7.setBackground(getDrawable(R.drawable.outline_filled_none));
                c7.setText("REP:   ");
                s7.setTextColor(getColor(R.color.white));
                s7.setBackground(getDrawable(R.drawable.outline_filled_black));
            }
        });

        s8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t8.setTextColor(getColor(R.color.black));
                t8.setBackground(getDrawable(R.drawable.outline_filled_none));
                c8.setText("REP:   ");
                s8.setTextColor(getColor(R.color.white));
                s8.setBackground(getDrawable(R.drawable.outline_filled_black));
            }
        });

        s9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t9.setTextColor(getColor(R.color.black));
                t9.setBackground(getDrawable(R.drawable.outline_filled_none));
                c9.setText("REP:   ");
                s9.setTextColor(getColor(R.color.white));
                s9.setBackground(getDrawable(R.drawable.outline_filled_black));
            }
        });

        s10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t10.setTextColor(getColor(R.color.black));
                t10.setBackground(getDrawable(R.drawable.outline_filled_none));
                c10.setText("REP:   ");
                s10.setTextColor(getColor(R.color.white));
                s10.setBackground(getDrawable(R.drawable.outline_filled_black));
            }
        });

        s11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t11.setTextColor(getColor(R.color.black));
                t11.setBackground(getDrawable(R.drawable.outline_filled_none));
                c11.setText("REP:   ");
                s11.setTextColor(getColor(R.color.white));
                s11.setBackground(getDrawable(R.drawable.outline_filled_black));
            }
        });

        s12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t12.setTextColor(getColor(R.color.black));
                t12.setBackground(getDrawable(R.drawable.outline_filled_none));
                c12.setText("REP:   ");
                s12.setTextColor(getColor(R.color.white));
                s12.setBackground(getDrawable(R.drawable.outline_filled_black));
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

    private void transferToArrayList(){
        Cursor cursor = myDB.readAllAtr();
        if(cursor.getCount() == 0){
            //None
        }else{
            while(cursor.moveToNext()){
                exerciseName.add(cursor.getString(1));
            }
        }
    }

    @Override
    public void onExeClick(int position, ArrayList<String> nameList) {
        String inputString;
        switch (currentExerciseIndex){
            case 0:
                selectedE1.setVisibility(View.VISIBLE);
                inputString = (currentExerciseIndex +1) + ". "+ nameList.get(position);
                Ex1.setText(inputString);
                eNameListedIn[currentExerciseIndex] = inputString;
                recyclerView.setVisibility(View.GONE);
                currentExerciseIndex +=1;
                break;
            case 1:
                selectedE2.setVisibility(View.VISIBLE);
                inputString = (currentExerciseIndex +1) + ". "+ nameList.get(position);
                Ex2.setText(inputString);
                eNameListedIn[currentExerciseIndex] = inputString;
                recyclerView.setVisibility(View.GONE);
                currentExerciseIndex +=1;
                break;
            case 2:
                selectedE3.setVisibility(View.VISIBLE);
                inputString = (currentExerciseIndex +1) + ". "+ nameList.get(position);
                Ex3.setText(inputString);
                eNameListedIn[currentExerciseIndex] = inputString;
                recyclerView.setVisibility(View.GONE);
                currentExerciseIndex +=1;
                break;
            case 3:
                selectedE4.setVisibility(View.VISIBLE);
                inputString = (currentExerciseIndex +1) + ". "+ nameList.get(position);
                Ex4.setText(inputString);
                eNameListedIn[currentExerciseIndex] = inputString;
                recyclerView.setVisibility(View.GONE);
                currentExerciseIndex +=1;
                break;
            case 4:
                selectedE5.setVisibility(View.VISIBLE);
                inputString = (currentExerciseIndex +1) + ". "+ nameList.get(position);
                Ex5.setText(inputString);
                eNameListedIn[currentExerciseIndex] = inputString;
                recyclerView.setVisibility(View.GONE);
                currentExerciseIndex +=1;
                break;
            case 5:
                selectedE6.setVisibility(View.VISIBLE);
                inputString = (currentExerciseIndex +1) + ". "+ nameList.get(position);
                Ex6.setText(inputString);
                eNameListedIn[currentExerciseIndex] = inputString;
                recyclerView.setVisibility(View.GONE);
                currentExerciseIndex +=1;
                break;
            case 6:
                selectedE7.setVisibility(View.VISIBLE);
                inputString = (currentExerciseIndex +1) + ". "+ nameList.get(position);
                Ex7.setText(inputString);
                eNameListedIn[currentExerciseIndex] = inputString;
                recyclerView.setVisibility(View.GONE);
                currentExerciseIndex +=1;
                break;
            case 7:
                selectedE8.setVisibility(View.VISIBLE);
                inputString = (currentExerciseIndex +1) + ". "+ nameList.get(position);
                Ex8.setText(inputString);
                eNameListedIn[currentExerciseIndex] = inputString;
                recyclerView.setVisibility(View.GONE);
                currentExerciseIndex +=1;
                break;
            case 8:
                selectedE9.setVisibility(View.VISIBLE);
                inputString = (currentExerciseIndex +1) + ". "+ nameList.get(position);
                Ex9.setText(inputString);
                eNameListedIn[currentExerciseIndex] = inputString;
                recyclerView.setVisibility(View.GONE);
                currentExerciseIndex +=1;
                break;
            case 9:
                selectedE10.setVisibility(View.VISIBLE);
                inputString = (currentExerciseIndex +1) + ". "+ nameList.get(position);
                Ex10.setText(inputString);
                eNameListedIn[currentExerciseIndex] = inputString;
                recyclerView.setVisibility(View.GONE);
                currentExerciseIndex +=1;
                break;
            case 10:
                selectedE11.setVisibility(View.VISIBLE);
                inputString = (currentExerciseIndex +1) + ". "+ nameList.get(position);
                Ex11.setText(inputString);
                eNameListedIn[currentExerciseIndex] = inputString;
                recyclerView.setVisibility(View.GONE);
                currentExerciseIndex +=1;
                break;
            case 11:
                selectedE12.setVisibility(View.VISIBLE);
                inputString = (currentExerciseIndex +1) + ". "+ nameList.get(position);
                Ex12.setText(inputString);
                eNameListedIn[currentExerciseIndex] = inputString;
                recyclerView.setVisibility(View.GONE);
                currentExerciseIndex +=1;
                break;
        }

    }

    private void reSortingIndex(int startingIndex){
        TextView[] ExList = new TextView[]{Ex1, Ex2, Ex3, Ex4, Ex5, Ex6, Ex7, Ex8, Ex9, Ex10, Ex11, Ex12};
        String tempName;
        int newIndex = 0;
        String indexString;
        for (int x = startingIndex; x < currentExerciseIndex; x++){
            tempName = eNameListedIn[x+1];

            for(int i = 0; i< tempName.length(); i++){
                if(tempName.charAt(i) == 32){
                    tempName = tempName.substring(i+1, tempName.length());
                    break;
                }
            }
            eNameListedIn[x] = eNameListedIn[x+1];
            indexString = String.valueOf(x+1);
            ExList[x].setText(indexString + ". " + tempName);
            newIndex +=1;
        }
    }
}