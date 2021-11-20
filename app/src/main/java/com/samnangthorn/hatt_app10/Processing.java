package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/*
query all data from the database and store in the RAM.java class for faster access
 */
public class Processing extends AppCompatActivity {

    private DataBaseHelper myDB;
    private Button p1,p2,p3;
    private TimeZone timezone;
    private Calendar realTime_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);

        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);
        p3 = findViewById(R.id.p3);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // query exercises_DATA from the database to RAM
                myDB = new DataBaseHelper(Processing.this);
                // range of random int (0 - ArrayList size)
                RAM.randomIndex = (int) (Math.random() * (RAM.getList_length()));
                // store to RAM class
                transferToArrayList();

                // read from database to array list
                myDB = new DataBaseHelper(Processing.this);
                Cursor cursor = myDB.readAllDate();
                if(cursor.getCount() == 0){
                    //None
                }else{
                    while(cursor.moveToNext()){
                        RAM.write_dateInfoList(cursor.getString(1));
                        RAM.write_dateWKNameList(cursor.getString(2));
                    }
                }
                p1.setBackground(getDrawable(R.drawable.button_bg_blue));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // retrieve date and time data
                        timezone = TimeZone.getDefault();
                        Helper.timeZone = timezone;
                        realTime_data = Calendar.getInstance();
                        // raw date format
                        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(realTime_data.getTime());
                        Helper.currentDate = currentDate;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
                        String time = simpleDateFormat.format(realTime_data.getTime());
                        Helper.timeString = time;
                        // convert to the needed format
                        int n = 0;
                        for(int x = 0; x < currentDate.length(); x++){
                            if(currentDate.charAt(x) == 44){
                                n = x;
                                break;
                            }
                        }
                        String nameOfDay = currentDate.substring(0, n);
                        Helper.setCurrentDayNameString(nameOfDay);
                        String subDate = currentDate.substring(n+1, currentDate.length());
                        Helper.subDate = subDate;
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
                        p2.setBackground(getDrawable(R.drawable.button_bg_blue));

                        Helper.currentDayInteger = Integer.parseInt(Helper.getCurrentDay(Helper.currentDate));;
                        String yearString = "ERROR";
                        String monthString = "ERROR";
                        String dateString = "ERROR";
                        for(int x = 0; x < RAM.read_dateInfoList_size(); x++) {
                            yearString = Helper.getCurrentYear();
                            monthString = Helper.getCurrentMonthString();
                            dateString = String.valueOf(Helper.currentDayInteger);
                            if (dateString.length() == 1) {
                                dateString = "0" + dateString;
                            }
                        }
                        Helper.currentDateString = yearString + monthString + dateString;

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                p3.setBackground(getDrawable(R.drawable.button_bg_blue));
                                open_HomeLayout();
                                finish();

                            }
                        },600);
                    }
                },600);
            }
        },600);

    }

    // method

    private void transferToArrayList(){
        Cursor cursor = myDB.readAllAtr();
        if(cursor.getCount() == 0){
            //None
        }else{
            while(cursor.moveToNext()){
                RAM.write_exerciseName(cursor.getString(1));
                RAM.write_mainMuscle(cursor.getString(2));
                RAM.write_subMuscle(cursor.getString(3));
                RAM.write_exerciseDescription(cursor.getString(4));
            }
        }
    }

    private void open_HomeLayout() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}