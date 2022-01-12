package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAd;

public class SetGoal extends AppCompatActivity {

    private ImageView btt_home, btt_report, btt_exercise, btt_schedule, btt_timer, btt_setting, logoReplaceBackBtt;
    private EditText edtWeight, edtDeadliftPr, edtBenchPressPr, edtCardioRunPr, edtSquatPr;
    private LinearLayout btt_saveGoal;
    private SharedPreferences getData;
    private SharedPreferences.Editor editData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goal);

        btt_home = findViewById(R.id.btt_home);
        btt_report = findViewById(R.id.btt_report);
        btt_exercise = findViewById(R.id.btt_exercise);
        btt_schedule = findViewById(R.id.btt_schedule);
        btt_timer = findViewById(R.id.btt_timer);
        btt_setting = findViewById(R.id.btt_setting);
        btt_saveGoal = findViewById(R.id.btt_saveGoal);
        edtWeight = findViewById(R.id.edtWeight);
        edtDeadliftPr = findViewById(R.id.edtDeadliftPr);
        edtBenchPressPr = findViewById(R.id.edtBenchPressPr);
        edtCardioRunPr = findViewById(R.id.edtCardioRunPr);
        edtSquatPr = findViewById(R.id.edtSquatPr);
        logoReplaceBackBtt = findViewById(R.id.logoReplaceBackBtt);

        getData = getApplicationContext().getSharedPreferences("local_data", MODE_PRIVATE);
        editData = getData.edit();

        AdLoader adLoader2 = new AdLoader.Builder(SetGoal.this, "ca-app-pub-9354138576649544/5969804562")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd2) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(null).build();
                        TemplateView template = findViewById(R.id.my_template2);
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd2);

                    }
                })
                .build();

        adLoader2.loadAd(new AdRequest.Builder().build());



        // set on click listeners
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

        btt_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_exerciseLayout();
                transition_animation("right");
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

        btt_saveGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editData.putString("WeightGoal", edtWeight.getText().toString());
                editData.putString("DeadLiftPR", edtWeight.getText().toString());
                editData.putString("BenchPressPR", edtWeight.getText().toString());
                editData.putString("aMileRunPR", edtWeight.getText().toString());
                editData.putString("SquatPR", edtWeight.getText().toString());
                editData.apply();
                Toast.makeText(SetGoal.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });

        logoReplaceBackBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetGoal.this, Report.class);
                startActivity(intent);
            }
        });
    }

    // methods

    public void open_homeLayout() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void open_settingLayout() {
        Intent intent = new Intent(this, Setting.class);
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
        Intent intent = new Intent(this, TimerLayout.class);
        startActivity(intent);
    }

    public void transition_animation(String leftOrRight){
        if(leftOrRight.equalsIgnoreCase("right")){
            overridePendingTransition(R.anim.sa_slide_in_right, R.anim.sa_slide_out_left);
        }else if(leftOrRight.equalsIgnoreCase("left")){
            overridePendingTransition(R.anim.sa_slide_in_left, R.anim.sa_slide_out_right);
        }
    }

}