package com.samnangthorn.hatt_app10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity implements RVAdapter.onExeClickListener{

    private ImageView btt_setting;
    private TextView txt_day, txt_date, txt_time, txt_timeZone, txt_workoutName, txt_exerciseName, txt_BMI_point,exercise_detailBtt, greetingTxt, btt_goWK, BMI_btt, userName, txt_wk_DES;
    private LinearLayout btt_GooglePlay, btt_cloudSave, btt_website, btt_report, btt_exercise, btt_schedule, btt_timer;
    private SharedPreferences getData;
    private SharedPreferences.Editor editData;
    private RewardedAd mRewardedAd;
    private final String TAG = "MainActivity";
    private String UID;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebase_database;
    private Dialog cloudSave_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btt_report = findViewById(R.id.btt_report);
        btt_exercise = findViewById(R.id.btt_exercise);
        btt_schedule = findViewById(R.id.btt_schedule);
        btt_timer = findViewById(R.id.btt_timer);
        btt_setting = findViewById(R.id.btt_setting);
        txt_day = findViewById(R.id.txt_day);
        txt_date = findViewById(R.id.txt_date);
        txt_time = findViewById(R.id.txt_time);
        txt_timeZone = findViewById(R.id.txt_timeZone);
        txt_workoutName = findViewById(R.id.txt_workoutName);
        txt_BMI_point =  findViewById(R.id.txt_BMI_point);
        btt_goWK = findViewById(R.id.btt_goWK);
        txt_exerciseName = findViewById(R.id.txt_exerciseName);
        exercise_detailBtt = findViewById(R.id.exercise_detailBtt);
        BMI_btt = findViewById(R.id.BMI_btt);
        btt_GooglePlay = findViewById(R.id.btt_GooglePlay);
        btt_cloudSave = findViewById(R.id.btt_cloudSave);
        userName = findViewById(R.id.userName);
        btt_website = findViewById(R.id.btt_website);
        greetingTxt = findViewById(R.id.greetingTxt);
        txt_wk_DES = findViewById(R.id.txt_wk_DES);



        // setting up date, time and timezone views
        txt_day.setText(Helper.getCurrentDayNameString());
        txt_date.setText(Helper.subDate);
        txt_time.setText(Helper.timeString);
        txt_timeZone.setText(Helper.timeZone.getID());
        // setting up welcome message
        if(Helper.timeString.contains("PM")){
            greetingTxt.setText("Good Evening,");
        }
        // setting up discover new exercise view
        txt_exerciseName.setText(RAM.read_exerciseNameAt(RAM.randomIndex));
        // set up today workout name
        int currentDay = Integer.parseInt(Helper.getCurrentDay(Helper.currentDate));
        String currentDayString = String.valueOf(currentDay);
        if(currentDay < 10){
            currentDayString = "0" + currentDayString;
        }
        for(int x = 0; x < RAM.get_dateInfoList_arrayList().size(); x++){
            System.out.println("peaks :" + Helper.getCurrentMonthString() + currentDay);
            if(RAM.get_dateInfoList_arrayList().get(x).substring(2).equalsIgnoreCase(Helper.getCurrentMonthString() + currentDayString)){
                txt_workoutName.setText(RAM.get_dateWKNameList_arrayList().get(x));
                if(RAM.get_statusList_arrayList().get(x).equalsIgnoreCase("IC")){
                    txt_wk_DES.setText("Status: Not yet Complete. Let finish this workout and get closer to your Goals.");
                }else{
                    txt_wk_DES.setText("Congratulation! You have finished your Today's Workout. That's another step closer to your Goals.");
                    btt_goWK.setVisibility(View.GONE);
                }
            }else{
                if(txt_workoutName.getText().toString().equalsIgnoreCase("REST DAY")){
                    txt_workoutName.setText("REST DAY");
                }
            }
        }

        // set up BMI point
        getData = getApplicationContext().getSharedPreferences("local_data", MODE_PRIVATE);
        editData = getData.edit();
        userName.setText(getData.getString("user_name", "User"));
        double BMI_number;
        // case unit is lb and inches
        if(getData.getString("unit", "ERROR").equalsIgnoreCase("US")){
            BMI_number = Double.valueOf(getData.getString("weight_US", "0")) / Math.pow(Double.valueOf(getData.getString("height_US", "0")), 2) * 703;
        // case unit is kg and meters
        }else{
            BMI_number = Double.valueOf(getData.getString("weight_NonUS", "0")) / Math.pow(Double.valueOf(getData.getString("height_NonUS", "0")), 2);
        }

        txt_BMI_point.setText(String.valueOf(String.format("%.1f", BMI_number)));
        Helper.tempBMI_value = String.format("%.1f", BMI_number);

        //Ads Loader
        /*AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-9354138576649544/6539393625")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        // Show the ad.
                        //ColorDrawable background = null;
                        NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(null).build();
                        TemplateView template = findViewById(R.id.my_template);
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {

                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();*/
        // End of Ads loader
        MobileAds.initialize(this);
        AdLoader adLoader = new AdLoader.Builder(Home.this, "ca-app-pub-9354138576649544/6539393625")
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

        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");
                    }
                });

        cloudSave_dialog = new Dialog(Home.this);
        cloudSave_dialog.setContentView(R.layout.cloud_save_opt);
        cloudSave_dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cloudSave_dialog.setCancelable(false);

        TextView bttCancel, bttProceed;
        bttCancel = cloudSave_dialog.findViewById(R.id.btt_cancel);
        bttProceed = cloudSave_dialog.findViewById(R.id.btt_proceed);

        bttCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cloudSave_dialog.dismiss();
            }
        });

        bttProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mRewardedAd != null) {
                    Activity activityContext = Home.this;
                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            Log.d(TAG, "The user earned the reward.");
                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                        }
                    });
                } else {
                    Log.d(TAG, "The rewarded ad wasn't ready yet.");
                }

                // Remote data
                mAuth = FirebaseAuth.getInstance();
                firebase_database = FirebaseFirestore.getInstance();
                UID = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = firebase_database.collection("User").document(UID);
                // localData_sharePreference
                Map<String, Object> User_data = new HashMap<>();
                User_data.put("user_name", getData.getString("user_name", "error"));
                User_data.put("weight_US", getData.getString("weight_US", "error"));
                User_data.put("weight_NonUS", getData.getString("weight_NonUS", "error"));
                User_data.put("height_US", getData.getString("height_US", "error"));
                User_data.put("height_NonUS", getData.getString("height_NonUS", "error"));
                User_data.put("profileMen", getData.getString("profileMen", "true"));
                User_data.put("unit", getData.getString("unit", "US"));
                //
                documentReference.update(User_data);
                // saving to cloud
                Toast.makeText(Home.this, "Save to Cloud Successful", Toast.LENGTH_SHORT).show();
                cloudSave_dialog.dismiss();
            }
        });

        // OnClick Listeners
        //
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

        BMI_btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this , BMI_detail.class);
                startActivity(intent);
                transition_animation("left");
            }
        });

        btt_goWK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_workoutName.getText().toString().equalsIgnoreCase("REST DAY")){
                    open_scheduleLayout();
                }else{
                    open_timerLayout();
                }
                transition_animation("right");
            }
        });

        txt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_scheduleLayout();
                transition_animation("right");
            }
        });

        txt_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_scheduleLayout();
                transition_animation("right");
            }
        });

        exercise_detailBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, DetailExercise.class);
                intent.putExtra("eName", RAM.read_exerciseNameAt(RAM.randomIndex));
                intent.putExtra("mTarget", RAM.read_mainMuscleAt(RAM.randomIndex));
                intent.putExtra("sTarget", RAM.read_subMuscleAt(RAM.randomIndex));
                intent.putExtra("des", RAM.read_exerciseDescriptionAt(RAM.randomIndex));
                startActivity(intent);
                transition_animation("left");
            }
        });

        btt_GooglePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGooglePlay();
            }
        });

        btt_cloudSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cloudSave_dialog.show();
            }
        });

        btt_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGooglePlay();
            }
        });

    }

    // methods

    public void open_settingLayout() {
        Intent intent = new Intent(this, Setting.class);
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

    @Override
    public void onExeClick(int position, ArrayList<String> nameList) {

        Intent intent = new Intent(this, DetailExercise.class);
        intent.putExtra("eName", nameList.get(position));
        intent.putExtra("mTarget", RAM.read_mainMuscleAt(RAM.read_exerciseName().indexOf(nameList.get(position))));
        intent.putExtra("sTarget", RAM.read_subMuscleAt(RAM.read_exerciseName().indexOf(nameList.get(position))));
        intent.putExtra("des", RAM.read_exerciseDescriptionAt(RAM.read_exerciseName().indexOf(nameList.get(position))));
        startActivity(intent);
    }

    private void openGooglePlay(){
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.samnangthorn.hatt_app10"));
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(Home.this, "Link Error!", Toast.LENGTH_SHORT).show();
        }
    }

    private void openWebsite(){
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://samnangthorn.website/hattapp"));
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(Home.this, "Link Error!", Toast.LENGTH_SHORT).show();
        }
    }
}