package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import java.util.ArrayList;

public class Exercise extends AppCompatActivity implements RVAdapter.onExeClickListener{

    private ImageView btt_home, btt_report, btt_schedule, btt_timer, btt_setting, btt_addExercise, btt_goLeft, btt_goRight;
    private TextView btt_MG, btt_AZ, view_MG;
    private RecyclerView recyclerView;
    private dataBaseHelper myDB;
    private ArrayList<String> exerciseName, mainTarget, subTarget, dis, sortedArray;
    private SearchView searchView;
    private SharedPreferences getData;
    private SharedPreferences.Editor editData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        btt_home = findViewById(R.id.btt_home);
        btt_report = findViewById(R.id.btt_report);
        btt_schedule = findViewById(R.id.btt_schedule);
        btt_timer = findViewById(R.id.btt_timer);
        btt_setting = findViewById(R.id.btt_setting);
        recyclerView = findViewById(R.id.recycleView);
        btt_addExercise = findViewById(R.id.btt_addExercise);
        btt_MG = findViewById(R.id.btt_MG);
        btt_AZ = findViewById(R.id.btt_AZ);
        view_MG = findViewById(R.id.MG_view);
        btt_goLeft = findViewById(R.id.btt_goLeft);
        btt_goRight = findViewById(R.id.btt_goRight);
        searchView = findViewById(R.id.search_bar);

        // initialization
        myDB = new dataBaseHelper(Exercise.this);
        getData = getApplicationContext().getSharedPreferences("local_data", MODE_PRIVATE);
        editData = getData.edit();
        exerciseName = new ArrayList<String>();
        mainTarget = new ArrayList<String>();
        subTarget = new ArrayList<String>();
        dis = new ArrayList<String>();

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

        btt_addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_addExerciseLayout();
            }
        });

        btt_MG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // auto disable sorted AZ function
                editData.putString("AZ", "false");
                editData.apply();
                btt_AZ.setTextColor(getResources().getColor(R.color.black));

                // check sp if MG function is off then switch on
                if(getData.getString("MG", "ERROR").equalsIgnoreCase("false")){

                    view_MG.setVisibility(View.VISIBLE);
                    view_MG.setText(Helper.muscleGroup_List[getData.getInt("MG_Index", 0)]);
                    btt_goLeft.setVisibility(View.VISIBLE);
                    btt_goRight.setVisibility(View.VISIBLE);
                    btt_MG.setTextColor(getResources().getColor(R.color.blue));
                    editData.putString("MG", "true");

                    // filter corresponding exercises to the following Muscle Group
                    ArrayList<String> tempArray = new ArrayList<String>();
                    for(int x = 0; x < exerciseName.size(); x++){
                        if(mainTarget.get(x).toLowerCase().contains(Helper.muscleGroup_List[getData.getInt("MG_Index", 0)])){
                            tempArray.add(exerciseName.get(x));
                        }
                    }
                    // update recycle view
                    RVAdapter rvAdapter = new RVAdapter(getApplicationContext(), tempArray, Exercise.this::onExeClick);
                    recyclerView.setAdapter(rvAdapter);

                    // check sp if MG function is on then switch off
                }else{
                    view_MG.setVisibility(View.GONE);
                    btt_goLeft.setVisibility(View.GONE);
                    btt_goRight.setVisibility(View.GONE);
                    btt_MG.setTextColor(getResources().getColor(R.color.black));
                    editData.putString("MG", "false");

                    // update recycle view
                    RVAdapter rvAdapter = new RVAdapter(getApplicationContext(), exerciseName, Exercise.this::onExeClick);
                    recyclerView.setAdapter(rvAdapter);
                }
                // update sp key value
                editData.apply();
            }
        });

        btt_AZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check sp if MG function is on then switch off
                if(getData.getString("MG", "ERROR").equalsIgnoreCase("true")){
                    view_MG.setVisibility(View.GONE);
                    btt_goLeft.setVisibility(View.GONE);
                    btt_goRight.setVisibility(View.GONE);
                    btt_MG.setTextColor(getResources().getColor(R.color.black));
                    editData.putString("MG", "false");
                }

                // check sp if AZ function is off then switch on
                if(getData.getString("AZ", "ERROR").equalsIgnoreCase("false")){
                    String[] eNameList = new String[exerciseName.size()];
                    for(int x = 0; x < exerciseName.size(); x++){
                        eNameList[x] = exerciseName.get(x);
                    }
                    // Bubble sort
                    for(int x = 0; x < eNameList.length; x++){
                        for(int y = 0; y < eNameList.length - 1; y++){
                            if(eNameList[y].compareTo(eNameList[y+1]) > 0){
                                //swap
                                String temp = eNameList[y];
                                eNameList[y] = eNameList[y+1];
                                eNameList[y+1] = temp;
                            }
                        }
                    }
                    sortedArray = new ArrayList<String>();
                    for(int x = 0; x < eNameList.length; x++){
                        sortedArray.add(eNameList[x]);
                    }
                    // update recycle view
                    RVAdapter rvAdapter = new RVAdapter(getApplicationContext(), sortedArray, Exercise.this::onExeClick);
                    recyclerView.setAdapter(rvAdapter);

                    // update sp key value
                    editData.putString("AZ", "true");
                    editData.apply();
                    btt_AZ.setTextColor(getResources().getColor(R.color.blue));

                    // check sp if AZ function is on then switch off
                }else if(getData.getString("AZ", "ERROR").equalsIgnoreCase("true")){
                    // update recycle view
                    RVAdapter rvAdapter = new RVAdapter(getApplicationContext(), exerciseName, Exercise.this::onExeClick);
                    recyclerView.setAdapter(rvAdapter);

                    // update sp key value
                    editData.putString("AZ", "false");
                    editData.apply();
                    btt_AZ.setTextColor(getResources().getColor(R.color.black));
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
                // auto close MG view when search
                view_MG.setVisibility(View.GONE);
                btt_goLeft.setVisibility(View.GONE);
                btt_goRight.setVisibility(View.GONE);
                btt_MG.setTextColor(getResources().getColor(R.color.black));
                editData.putString("MG", "false");
                editData.apply();

                // auto close AZ sort
                editData.putString("AZ", "false");
                editData.apply();
                btt_AZ.setTextColor(getResources().getColor(R.color.black));

                // query part
                ArrayList<String> tempArray = new ArrayList<String>();
                for(int x = 0; x < exerciseName.size(); x++){
                    if(exerciseName.get(x).toLowerCase().contains(newText.toLowerCase())){
                        tempArray.add(exerciseName.get(x));
                    }
                }
                // update recycle view
                RVAdapter rvAdapter = new RVAdapter(getApplicationContext(), tempArray, Exercise.this::onExeClick);
                recyclerView.setAdapter(rvAdapter);

                return false;
            }
        });

        btt_goRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Access MG value from sp to the corresponding index
                int index = getData.getInt("MG_Index", 0);
                index += 1;
                if (index >= (Helper.muscleGroup_List.length - 1)){
                    index = 0;
                }
                view_MG.setText(Helper.muscleGroup_List[index]);

                // filter corresponding exercises to the following Muscle Group
                ArrayList<String> tempArray = new ArrayList<String>();
                tempArray.clear();
                for(int x = 0; x < exerciseName.size(); x++){
                    if(mainTarget.get(x).toLowerCase().equalsIgnoreCase(Helper.muscleGroup_List[index])){
                        tempArray.add(exerciseName.get(x));
                    }
                }
                // update recycle view
                RVAdapter rvAdapter = new RVAdapter(getApplicationContext(), tempArray, Exercise.this::onExeClick);
                recyclerView.setAdapter(rvAdapter);

                // update sp key value
                editData.putInt("MG_Index", index);
                editData.apply();
            }
        });

        btt_goLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Access MG value from sp to the corresponding index
                int index = getData.getInt("MG_Index", 0);
                index -= 1;
                if (index <= 0){
                    index = Helper.muscleGroup_List.length-1;
                }
                view_MG.setText(Helper.muscleGroup_List[index]);

                // filter corresponding exercises to the following Muscle Group
                ArrayList<String> tempArray = new ArrayList<String>();
                for(int x = 0; x < exerciseName.size(); x++){
                    if(mainTarget.get(x).toLowerCase().equalsIgnoreCase(Helper.muscleGroup_List[index])){
                        tempArray.add(exerciseName.get(x));
                    }
                }
                // update recycle view
                RVAdapter rvAdapter = new RVAdapter(getApplicationContext(), tempArray, Exercise.this::onExeClick);
                recyclerView.setAdapter(rvAdapter);

                // update sp key value
                editData.putInt("MG_Index", index);
                editData.apply();
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
                mainTarget.add(cursor.getString(2));
                subTarget.add(cursor.getString(3));
                dis.add(cursor.getString(4));
            }
        }
    }

    @Override
    public void onExeClick(int position, ArrayList<String> nameList) {

        Intent intent = new Intent(this, DetailExercise.class);
        intent.putExtra("eName", nameList.get(position));
        intent.putExtra("mTarget", mainTarget.get(exerciseName.indexOf(nameList.get(position))));
        intent.putExtra("sTarget", subTarget.get(exerciseName.indexOf(nameList.get(position))));
        intent.putExtra("des", dis.get(exerciseName.indexOf(nameList.get(position))));

        startActivity(intent);
    }
}