package com.samnangthorn.hatt_app10;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Setting extends AppCompatActivity {

    private ImageView btt_home, btt_report, btt_exercise, btt_schedule, btt_timer, btt_setting, btt_facebook, btt_youtube;
    private Button btt_signOut, btt_save;
    private EditText userName, weight, height;
    private TextView email, txt_wUnit, txt_hUnit;
    private FirebaseAuth mAuth;
    private RadioButton kg, lb;
    private SharedPreferences getData;
    private SharedPreferences.Editor editData;
    private FirebaseFirestore firebase_database;

    private String newUserName, newHeight, newWeight, newUnit;

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
        weight = findViewById(R.id.edt_weight);
        height = findViewById(R.id.edt_height);
        btt_save = findViewById(R.id.btt_save);
        txt_wUnit = findViewById(R.id.txt_wUnit);
        txt_hUnit = findViewById(R.id.txt_hUnit);

        getData = getApplicationContext().getSharedPreferences("local_data", MODE_PRIVATE);
        editData = getData.edit();

        newUserName = getData.getString("user_name", "ERROR");
        userName.setText(newUserName);
        email.setText(getData.getString("email_address", "ERROR"));
        newWeight = getData.getString("weight", "ERROR");
        weight.setText(newWeight);
        newHeight = getData.getString("height", "ERROR");
        height.setText(newHeight);

        newUnit = getData.getString("unit", "ERROR");
        if(newUnit.equalsIgnoreCase("NonUS")){
            kg.setChecked(true);
            txt_wUnit.setText(" KG");
            txt_hUnit.setText(" M");
        }else{
            lb.setChecked(true);
            txt_wUnit.setText(" LB");
            txt_hUnit.setText(" F");
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

        btt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean saveCondition = false;

                if(!(userName.getText().toString().equals(getData.getString("user_name", "ERROR")))) {
                    newUserName = userName.getText().toString();
                    saveCondition = true;
                }
                if(!(height.getText().toString().equals(getData.getString("Height", "ERROR")))) {
                    newHeight = height.getText().toString();
                    saveCondition = true;
                }
                if(!(weight.getText().toString().equals(getData.getString("Weight", "ERROR")))) {
                    newWeight = weight.getText().toString();
                    saveCondition = true;
                }

                if(!(kg.isChecked() && getData.getString("unit", "ERROR").equalsIgnoreCase("NonUS"))){
                    saveCondition = true;
                }
                if(!(lb.isChecked() && getData.getString("unit", "ERROR").equalsIgnoreCase("US"))){
                    saveCondition = true;
                }

                if(saveCondition){
                    String unit;
                    if(kg.isChecked()){
                        newUnit = "NonUS";
                        unit = "NonUS";
                    }else{
                        newUnit = "US";
                        unit = "US";
                    }
                    firebase_database = FirebaseFirestore.getInstance();
                    DocumentReference documentReference = firebase_database.collection("User").document(getData.getString("UID", "ERROR"));
                    Map<String, Object> User_data = new HashMap<>();
                    User_data.put("user_name", userName.getText().toString());
                    User_data.put("height", height.getText().toString());
                    User_data.put("weight", weight.getText().toString());
                    User_data.put("unit", unit);
                    documentReference.update(User_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                editData.putString("user_name", newUserName);
                                editData.putString("unit", newUnit);
                                editData.putString("weight", newWeight);
                                editData.putString("height", newHeight);
                                editData.apply();
                                Toast.makeText(Setting.this, "Saved!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Setting.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_wUnit.setText(" KG");
                txt_hUnit.setText(" M");
            }
        });

        lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_wUnit.setText(" LB");
                txt_hUnit.setText(" F");
            }
        });
    }

    // methods

    private void open_homeLayout() {
        Intent intent = new Intent(this, Home.class);
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

    private void open_scheduleLayout() {
        Intent intent = new Intent(this, Schedule.class);
        startActivity(intent);
    }

    private void open_timerLayout() {
        Intent intent = new Intent(this, Timer.class);
        startActivity(intent);
    }

    private void openLinkFacebookPage(){
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/HattAppOfficial"));
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(Setting.this, "Link Error!", Toast.LENGTH_SHORT).show();
        }
    }

    private void openLinkYoutubeChannel(){
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/c/SamnangThorn"));
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(Setting.this, "Link Error!", Toast.LENGTH_SHORT).show();
        }
    }

    private void open_setUpLayout() {
        Intent intent = new Intent(this, Setup.class);
        startActivity(intent);
    }
}