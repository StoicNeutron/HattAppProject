package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Setting extends AppCompatActivity {

    ImageView btt_home, btt_report, btt_exercise, btt_schedule, btt_timer, btt_setting, btt_facebook, btt_youtube;
    Button btt_signOut;
    EditText userName;
    TextView email;
    RadioButton kg, lb;
    FirebaseAuth mAuth;
    SharedPreferences getData;
    SharedPreferences.Editor editData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btt_home = findViewById(R.id.btt_home);
        btt_report = findViewById(R.id.btt_report);
        btt_exercise = findViewById(R.id.btt_exercise);
        btt_schedule = findViewById(R.id.btt_schedule);
        btt_timer = findViewById(R.id.btt_timer);
        btt_setting = findViewById(R.id.btt_setting);
        btt_facebook = findViewById(R.id.btt_facebook);
        btt_youtube = findViewById(R.id.btt_youtube);
        btt_signOut = findViewById(R.id.btt_signOut);
        userName = findViewById(R.id.txt_userName);
        email = findViewById(R.id.txt_userEmail);
        kg = findViewById(R.id.kg_unit);
        lb = findViewById(R.id.lb_unit);

        getData = getApplicationContext().getSharedPreferences("local_data", MODE_PRIVATE);
        editData = getData.edit();

        userName.setText(getData.getString("user_name", "ERROR"));
        email.setText(getData.getString("email_address", "ERROR"));
        if(getData.getString("unit", "ERROR").equalsIgnoreCase("NonUS")){
            kg.setChecked(true);
        }else{
            lb.setChecked(true);
        }

        btt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_homeLayout();
            }
        });

        btt_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_reportLayout();
            }
        });

        btt_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_exerciseLayout();
            }
        });

        btt_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_scheduleLayout();
            }
        });

        btt_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_timerLayout();
            }
        });

        btt_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLinkFacebookPage();
            }
        });

        btt_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLinkYoutubeChannel();
            }
        });

        btt_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getInstance().signOut();
                open_setUpLayout();
                finish();
            }
        });
    }

    // methods

    public void open_homeLayout() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void open_reportLayout() {
        Intent intent = new Intent(this, Report.class);
        startActivity(intent);
    }

    public void open_exerciseLayout() {
        Intent intent = new Intent(this, Exercise.class);
        startActivity(intent);
    }

    public void open_scheduleLayout() {
        Intent intent = new Intent(this, Schedule.class);
        startActivity(intent);
    }

    public void open_timerLayout() {
        Intent intent = new Intent(this, Timer.class);
        startActivity(intent);
    }

    public void openLinkFacebookPage(){
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/HattAppOfficial"));
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(Setting.this, "Link Error!", Toast.LENGTH_SHORT).show();
        }
    }

    public void openLinkYoutubeChannel(){
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/c/SamnangThorn"));
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(Setting.this, "Link Error!", Toast.LENGTH_SHORT).show();
        }
    }

    public void open_setUpLayout() {
        Intent intent = new Intent(this, Setup.class);
        startActivity(intent);
    }
}