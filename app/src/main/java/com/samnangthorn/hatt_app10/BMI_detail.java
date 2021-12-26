package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAd;

public class BMI_detail extends AppCompatActivity {

    private ImageView btt_home, btt_exercise, btt_schedule, btt_timer, btt_setting;
    private Dialog dialog;
    private ImageView btt_BMI_info, orange, green, blue, red;
    private TextView txt_bmi_value, txt_weight, txt_height, txt_bmiStatus;
    private SharedPreferences getData;
    private SharedPreferences.Editor editData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_m_i_detail);

        btt_home = findViewById(R.id.btt_home);
        btt_exercise = findViewById(R.id.btt_exercise);
        btt_schedule = findViewById(R.id.btt_schedule);
        btt_timer = findViewById(R.id.btt_timer);
        btt_setting = findViewById(R.id.btt_setting);
        btt_BMI_info = findViewById(R.id.btt_BMI_info);
        txt_height = findViewById(R.id.txt_height);
        txt_weight = findViewById(R.id.txt_weight);
        txt_bmiStatus = findViewById(R.id.txt_bmiStatus);
        orange = findViewById(R.id.orange);
        green = findViewById(R.id.green);
        blue = findViewById(R.id.blue);
        red = findViewById(R.id.red);
        txt_bmi_value = findViewById(R.id.txt_bmi_value);

        dialog = new Dialog(BMI_detail.this);
        dialog.setContentView(R.layout.popup_bmi_info);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        // initialization
        getData = getApplicationContext().getSharedPreferences("local_data", MODE_PRIVATE);
        editData = getData.edit();

        if(getData.getString("unit", "error").toString().equalsIgnoreCase("US")){
            txt_weight.setText(getData.getString("weight_US", "error")+ " lb");
            txt_height.setText(getData.getString("height_US", "error")+ " inches");
        }else{
            txt_weight.setText(getData.getString("weight_NonUS", "error")+ " lb");
            txt_height.setText(getData.getString("height_NonUS", "error") + " inches");
        }

        txt_bmi_value.setText(Helper.tempBMI_value);
        double bmiVal = Double.valueOf(Helper.tempBMI_value);
        if(bmiVal < 18){
            txt_bmiStatus.setText("More Weight Recommended");
            txt_bmiStatus.setTextColor(getColor(R.color.orange));
            orange.setVisibility(View.VISIBLE);
        }else if(bmiVal < 26){
            txt_bmiStatus.setText("Healthy");
            txt_bmiStatus.setTextColor(getColor(R.color.green));
            green.setVisibility(View.VISIBLE);
        }else if(bmiVal < 32){
            txt_bmiStatus.setText("Check Result Below");
            txt_bmiStatus.setTextColor(getColor(R.color.blue));
            blue.setVisibility(View.VISIBLE);
        }else{
            txt_bmiStatus.setText("Less Weight Recommended");
            txt_bmiStatus.setTextColor(getColor(R.color.red_dark));
            red.setVisibility(View.VISIBLE);
        }

        AdLoader adLoader2 = new AdLoader.Builder(BMI_detail.this, "ca-app-pub-9354138576649544/9652899280")
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

        btt_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_exerciseLayout();
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

        btt_BMI_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
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

    private void transition_animation(String leftOrRight){
        if(leftOrRight.equalsIgnoreCase("right")){
            overridePendingTransition(R.anim.sa_slide_in_right, R.anim.sa_slide_out_left);
        }else if(leftOrRight.equalsIgnoreCase("left")){
            overridePendingTransition(R.anim.sa_slide_in_left, R.anim.sa_slide_out_right);
        }
    }
}