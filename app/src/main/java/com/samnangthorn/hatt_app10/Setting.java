package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Setting extends AppCompatActivity {

    private ImageView btt_home, btt_report, btt_exercise, btt_schedule, btt_timer, btt_facebook, btt_youtube;
    private EditText userName;
    private LinearLayout weight, height, edt_restTime, btt_signOut, unit;
    private TextView email, txt_restTime, txt_unit, txt_wUnit, txt_hUnit;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebase_database;
    private RadioButton kg, lb;
    private SharedPreferences getData, getData2;
    private SharedPreferences.Editor editData, editData2;
    private Dialog dialog;
    private DataBaseHelper myDB;
    private String newUserName, newHeight, newWeight, Unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btt_home = findViewById(R.id.btt_home);
        btt_report = findViewById(R.id.btt_report);
        btt_exercise = findViewById(R.id.btt_exercise);
        btt_schedule = findViewById(R.id.btt_schedule);
        btt_timer = findViewById(R.id.btt_timer);
        btt_facebook = findViewById(R.id.btt_facebook);
        btt_youtube = findViewById(R.id.btt_youtube);
        btt_signOut = findViewById(R.id.btt_signOut);
        userName = findViewById(R.id.txt_userName);
        email = findViewById(R.id.txt_userEmail);
        weight = findViewById(R.id.edt_weight);
        height = findViewById(R.id.edt_height);
        //txt_wUnit = findViewById(R.id.txt_wUnit);
        //txt_hUnit = findViewById(R.id.txt_hUnit);
        edt_restTime = findViewById(R.id.edt_restTime);
        txt_restTime = findViewById(R.id.txt_restTime);
        unit = findViewById(R.id.unit);
        txt_unit = findViewById(R.id.txt_unit);

        // Dialog
        dialog = new Dialog(Setting.this);
        dialog.setContentView(R.layout.pop_up_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        TextView btt_cancel, btt_logout;
        btt_cancel = dialog.findViewById(R.id.pop_btt_cancel);
        btt_logout = dialog.findViewById(R.id.pop_btt_logout);

        btt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getInstance().signOut();
                getData2 = getApplicationContext().getSharedPreferences("workout_data", MODE_PRIVATE);
                editData2 = getData2.edit();
                editData2.putInt("WT", 0);
                editData2.apply();
                myDB = new DataBaseHelper(Setting.this);
                myDB.clearAllExercise();
                myDB.clearAllWorkout();
                open_setUpLayout();
                finish();
            }
        });

        getData = getApplicationContext().getSharedPreferences("local_data", MODE_PRIVATE);
        editData = getData.edit();

        // Set Up View
        newUserName = getData.getString("user_name", "ERROR");
        userName.setText(newUserName);
        email.setText(getData.getString("email_address", "ERROR"));
        newWeight = getData.getString("weight", "ERROR");
        //weight.setText(newWeight);
        newHeight = getData.getString("height", "ERROR");
        //height.setText(newHeight);
        txt_restTime.setText(String.valueOf(getData.getInt("restTimer", 60)));

        Unit = getData.getString("unit", "ERROR");
        if(Unit.equalsIgnoreCase("NonUS")) {
            txt_unit.setText("KG-M");
        }else if(Unit.equalsIgnoreCase("US")){
            txt_unit.setText("LB-FT");
        }

        AdLoader adLoader = new AdLoader.Builder(Setting.this, "ca-app-pub-9354138576649544/5121073006")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(null).build();
                        TemplateView template = findViewById(R.id.my_template);
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);

                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());

        // On Click Listeners
        //
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
                dialog.show();
            }
        });

        unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Unit.equalsIgnoreCase("NonUS")) {
                    editData.putString("unit", "US");
                    editData.apply();
                    Toast.makeText(Setting.this, "US Unit Switched", Toast.LENGTH_SHORT).show();
                    Unit = "US";
                    txt_unit.setText("LB-FT");
                }else if(Unit.equalsIgnoreCase("US")){
                    editData.putString("unit", "NonUS");
                    editData.apply();
                    Toast.makeText(Setting.this, "NonUS Unit Switched", Toast.LENGTH_SHORT).show();
                    Unit = "NonUS";
                    txt_unit.setText("KG-M");
                }
            }
        });

        /*btt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean saveCondition = false;

                if (!(userName.getText().toString().equals(getData.getString("user_name", "ERROR")))) {
                    newUserName = userName.getText().toString();
                    saveCondition = true;
                }
                if (!(height.getText().toString().equals(getData.getString("Height", "ERROR")))) {
                    newHeight = height.getText().toString();
                    saveCondition = true;
                }
                if (!(weight.getText().toString().equals(getData.getString("Weight", "ERROR")))) {
                    newWeight = weight.getText().toString();
                    saveCondition = true;
                }

                if (!(kg.isChecked() && getData.getString("unit", "ERROR").equalsIgnoreCase("NonUS"))) {
                    saveCondition = true;
                }
                if (!(lb.isChecked() && getData.getString("unit", "ERROR").equalsIgnoreCase("US"))) {
                    saveCondition = true;
                }
                if (saveCondition) {
                    String unit;
                    if (kg.isChecked()) {
                        newUnit = "NonUS";
                    } else {
                        newUnit = "US";
                    }
                    // update local data
                    editData.putString("user_name", newUserName);
                    editData.putString("unit", newUnit);
                    editData.putString("weight", newWeight);
                    editData.putString("height", newHeight);
                    editData.putInt("restTimer", Integer.parseInt(edt_restTime.getText().toString()));
                    editData.apply();
                    Toast.makeText(Setting.this, "Saved!", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        /*kg.setOnClickListener(new View.OnClickListener() {
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
        });*/
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
        Intent intent = new Intent(this, TimerLayout.class);
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