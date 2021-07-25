package com.samnangthorn.hatt_app10;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class AddWorkout extends AppCompatActivity implements RVAdapter.onExeClickListener{

    private ImageView btt_home, btt_report, btt_timer, btt_setting, btt_exercise, btt_back;
    private SearchView searchView;
    private ArrayList<String> exerciseName, selectedExercise;
    private dataBaseHelper myDB;
    private RecyclerView recyclerView;
    private LinearLayout selectedE1, selectedE2, selectedE3, selectedE4, selectedE5, selectedE6, selectedE7, selectedE8, selectedE9, selectedE10, selectedE11, selectedE12;
    private TextView Ex1, Ex2, Ex3, Ex4, Ex5, Ex6, Ex7, Ex8, Ex9, Ex10, Ex11, Ex12;
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        btt_home = findViewById(R.id.btt_home);
        btt_report = findViewById(R.id.btt_report);
        btt_timer = findViewById(R.id.btt_timer);
        btt_back = findViewById(R.id.btt_back);
        btt_setting = findViewById(R.id.btt_setting);
        btt_exercise = findViewById(R.id.btt_exercise);
        searchView = findViewById(R.id.search_bar);
        recyclerView = findViewById(R.id.recycleView);
        exerciseName = new ArrayList<String>();
        selectedExercise = new ArrayList<String>();
        myDB = new dataBaseHelper(AddWorkout.this);

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

        /*
        LinearLayout[] LayoutSelectedE = new LinearLayout[]{selectedE1, selectedE2, selectedE3, selectedE4, selectedE5, selectedE6, selectedE7, selectedE8, selectedE9, selectedE10, selectedE11, selectedE12};
        for(int i = 0; i< 12; i++){
            LayoutSelectedE[i].setVisibility(View.VISIBLE);
        }

         */


        // query the database to ArrayList
        transferToArrayList();

        // setup recycle view
        RVAdapter rvAdapter = new RVAdapter(this, exerciseName, this);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        btt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_scheduleLayout();
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
                RVAdapter rvAdapter = new RVAdapter(getApplicationContext(), tempArray, AddWorkout.this::onExeClick);
                recyclerView.setAdapter(rvAdapter);

                return false;
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

    private void open_addExerciseLayout() {
        Intent intent = new Intent(this, AddExercise.class);
        Helper.exerciseNameArray.clear();
        Helper.exerciseNameArray = exerciseName;
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
        switch (currentIndex){
            case 0:
                selectedE1.setVisibility(View.VISIBLE);
                Ex1.setText(nameList.get(position));
                recyclerView.setVisibility(View.GONE);
                currentIndex +=1;
                break;
            case 1:
                selectedE2.setVisibility(View.VISIBLE);
                Ex2.setText(nameList.get(position));
                recyclerView.setVisibility(View.GONE);
                currentIndex +=1;
                break;
            case 2:
                selectedE3.setVisibility(View.VISIBLE);
                Ex3.setText(nameList.get(position));
                recyclerView.setVisibility(View.GONE);
                currentIndex +=1;
                break;
            case 3:
                selectedE4.setVisibility(View.VISIBLE);
                Ex4.setText(nameList.get(position));
                recyclerView.setVisibility(View.GONE);
                currentIndex +=1;
                break;
            case 4:
                selectedE5.setVisibility(View.VISIBLE);
                Ex5.setText(nameList.get(position));
                recyclerView.setVisibility(View.GONE);
                currentIndex +=1;
                break;
            case 5:
                selectedE6.setVisibility(View.VISIBLE);
                Ex6.setText(nameList.get(position));
                recyclerView.setVisibility(View.GONE);
                currentIndex +=1;
                break;
            case 6:
                selectedE7.setVisibility(View.VISIBLE);
                Ex7.setText(nameList.get(position));
                recyclerView.setVisibility(View.GONE);
                currentIndex +=1;
                break;
            case 7:
                selectedE8.setVisibility(View.VISIBLE);
                Ex8.setText(nameList.get(position));
                recyclerView.setVisibility(View.GONE);
                currentIndex +=1;
                break;
            case 8:
                selectedE9.setVisibility(View.VISIBLE);
                Ex9.setText(nameList.get(position));
                recyclerView.setVisibility(View.GONE);
                currentIndex +=1;
                break;
            case 9:
                selectedE10.setVisibility(View.VISIBLE);
                Ex10.setText(nameList.get(position));
                recyclerView.setVisibility(View.GONE);
                currentIndex +=1;
                break;
            case 10:
                selectedE11.setVisibility(View.VISIBLE);
                Ex11.setText(nameList.get(position));
                recyclerView.setVisibility(View.GONE);
                currentIndex +=1;
                break;
            case 11:
                selectedE12.setVisibility(View.VISIBLE);
                Ex12.setText(nameList.get(position));
                recyclerView.setVisibility(View.GONE);
                currentIndex +=1;
                break;
        }

    }
}