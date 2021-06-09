package com.samnangthorn.hatt_app10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        try{
            bottomNavigationView.setSelectedItemId(R.id.nav_report);
        }catch(Exception e){
            System.out.println(e);
        }

        try {


            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item_menu) {
                    switch (item_menu.getItemId()) {
                        case R.id.nav_report:
                            startActivity(new Intent(getApplicationContext(), Report.class));
                            overridePendingTransition(0, 0);
                            return true;
                        case R.id.nav_timmer:
                            startActivity(new Intent(getApplicationContext(), Timer.class));
                            overridePendingTransition(0, 0);
                            return true;
                        case R.id.nav_exercise:
                            startActivity(new Intent(getApplicationContext(), Exercise.class));
                            overridePendingTransition(0, 0);
                            return true;
                        case R.id.nav_schedule:
                            startActivity(new Intent(getApplicationContext(), Schedule.class));
                            overridePendingTransition(0, 0);
                            return true;
                        case R.id.nav_home:
                            return true;
                    }
                    return false;
                }
            });

        }catch(Exception e){
            System.out.println("Second error: " + e);
        }
}}