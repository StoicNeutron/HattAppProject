package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.NativeAd;

public class Report extends AppCompatActivity {

    ImageView btt_home, btt_report, btt_exercise, btt_schedule, btt_timer, btt_setting;
    private View bar_mon, bar_tue, bar_wed, bar_thu, bar_fri, bar_sat, bar_sun;
    private TextView txt_bmi_value, bmiResult, txt_PWeekDate, co1, co2, co3, co4, co5, co6, co7;
    private LinearLayout btt_updateWeightHeight, btt_cloudUpload;
    private ImageView btt_BMI_info;
    private AdView mAdView;
    private Dialog dialog, dialog2;
    private String dateRangeString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        btt_home = findViewById(R.id.btt_home);
        btt_report = findViewById(R.id.btt_report);
        btt_exercise = findViewById(R.id.btt_exercise);
        btt_schedule = findViewById(R.id.btt_schedule);
        btt_timer = findViewById(R.id.btt_timer);
        btt_setting = findViewById(R.id.btt_setting);
        txt_bmi_value = findViewById(R.id.txt_bmi_value);
        btt_updateWeightHeight = findViewById(R.id.btt_updateWeightHeight);
        btt_BMI_info = findViewById(R.id.btt_BMI_info);
        txt_bmi_value.setText(Helper.tempBMI_value);
        bmiResult = findViewById(R.id.bmiResult);
        bar_mon = findViewById(R.id.bar_mon);
        bar_tue = findViewById(R.id.bar_tue);
        bar_wed = findViewById(R.id.bar_wed);
        bar_thu = findViewById(R.id.bar_thu);
        bar_fri = findViewById(R.id.bar_fri);
        bar_sat = findViewById(R.id.bar_sat);
        bar_sun = findViewById(R.id.bar_sun);
        txt_PWeekDate = findViewById(R.id.txt_PWeekDate);
        co1 = findViewById(R.id.co1);
        co2 = findViewById(R.id.co2);
        co3 = findViewById(R.id.co3);
        co4 = findViewById(R.id.co4);
        co5 = findViewById(R.id.co5);
        co6 = findViewById(R.id.co6);
        co7 = findViewById(R.id.co7);
        TextView[] coList = new TextView[]{co1, co2, co3, co4, co5, co6, co7};
        String[] dateStringList = new String[7];

        int starInt = 7;

        for(int x = 0; x < coList.length; x++){
            dateStringList[x] = getPWeekRange(coList, x, starInt, dateRangeString);
            dateRangeString = getPWeekRangeString(coList, x, starInt, dateRangeString);
            System.out.println("kcg: " + dateStringList[x]);
            starInt--;
        }
        dateRangeString = "( " + dateRangeString + " )";
        txt_PWeekDate.setText(dateRangeString);


        // Status of previous week completion
        for(int x = 0; x< RAM.get_dateInfoList_arrayList().size(); x++){
            if(RAM.get_dateInfoList_arrayList().get(x).equalsIgnoreCase(Helper.currentDateString)){
                if(RAM.get_statusList_arrayList().get(x).equalsIgnoreCase("C")){

                    // Later
                }else{

                    // Later
                }
            }
        }


        // Dialog1
        //
        dialog = new Dialog(Report.this);
        dialog.setContentView(R.layout.popup_bmi_info);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        // Dialog2
        //
        dialog2 = new Dialog(Report.this);
        dialog2.setContentView(R.layout.popup_cloudfuncinfo);
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.setCancelable(true);

        //Ads loading func
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        //MobileAds.initialize(this);
        AdLoader adLoader = new AdLoader.Builder(Report.this, "ca-app-pub-9354138576649544/6820371324")
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

        AdLoader adLoader2 = new AdLoader.Builder(Report.this, "ca-app-pub-9354138576649544/1664263478")
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

        btt_updateWeightHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_settingLayout();
            }
        });

        btt_BMI_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        bmiResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_bmiResult();
            }
        });

        /*btt_cloudUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.show();
            }
        });*/
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

    public void open_bmiResult() {
        Intent intent = new Intent(this, BMI_detail.class);
        startActivity(intent);
    }

    public void transition_animation(String leftOrRight){
        if(leftOrRight.equalsIgnoreCase("right")){
            overridePendingTransition(R.anim.sa_slide_in_right, R.anim.sa_slide_out_left);
        }else if(leftOrRight.equalsIgnoreCase("left")){
            overridePendingTransition(R.anim.sa_slide_in_left, R.anim.sa_slide_out_right);
        }
    }

    public String getPWeekRange(TextView[] coList, int index, int starInt, String dateRangeString){

        int currentDayNum = Helper.currentDayInteger;
        String resultDateString = "";

        if((currentDayNum-starInt)<=0){
            if(Helper.getTotalDayOfPreMonth(Helper.getPreMonth(Helper.currentMonth))-(starInt-currentDayNum) == 21 || Helper.getTotalDayOfPreMonth(Helper.getPreMonth(Helper.currentMonth))-(starInt-currentDayNum) == 31){
                coList[index].setText(String.valueOf(Helper.getTotalDayOfPreMonth(Helper.getPreMonth(Helper.currentMonth))-(starInt-currentDayNum))+"st");
            }else if(Helper.getTotalDayOfPreMonth(Helper.getPreMonth(Helper.currentMonth))-(starInt-currentDayNum) == 22){
                coList[index].setText(String.valueOf(Helper.getTotalDayOfPreMonth(Helper.getPreMonth(Helper.currentMonth))-(starInt-currentDayNum))+"nd");
            }else if(Helper.getTotalDayOfPreMonth(Helper.getPreMonth(Helper.currentMonth))-(starInt-currentDayNum) == 23){
                coList[index].setText(String.valueOf(Helper.getTotalDayOfPreMonth(Helper.getPreMonth(Helper.currentMonth))-(starInt-currentDayNum))+"rd");
            }else{
                coList[index].setText(String.valueOf(Helper.getTotalDayOfPreMonth(Helper.getPreMonth(Helper.currentMonth))-(starInt-currentDayNum))+"th");
            }

            if(Helper.getTotalDayOfPreMonth(Helper.getPreMonth(Helper.currentMonth))-(starInt-currentDayNum) < 10){
                resultDateString = Helper.getCurrentYear() + String.valueOf(Helper.getThisMonthIndex(Helper.getPreMonth(Helper.getCurrentMonthName()))) + "0" + String.valueOf(Helper.getTotalDayOfPreMonth(Helper.getPreMonth(Helper.currentMonth))-(starInt-currentDayNum));
            }else{
                resultDateString = Helper.getCurrentYear() + String.valueOf(Helper.getThisMonthIndex(Helper.getPreMonth(Helper.getCurrentMonthName()))) + String.valueOf(Helper.getTotalDayOfPreMonth(Helper.getPreMonth(Helper.currentMonth))-(starInt-currentDayNum));
            }

            if(index == 0){
                dateRangeString = Helper.getPreMonth(Helper.getCurrentMonthName() + " " + String.valueOf(Helper.getTotalDayOfPreMonth(Helper.getPreMonth(Helper.currentMonth))-(starInt-currentDayNum)));
            }
        }else{
            if(currentDayNum-starInt == 1 || currentDayNum-starInt == 21 || currentDayNum-starInt == 31){
                coList[index].setText(String.valueOf(currentDayNum-starInt)+"st");
            }else if(currentDayNum-1 == 2 || currentDayNum-starInt == 22){
                coList[index].setText(String.valueOf(currentDayNum-starInt)+"nd");
            }else if(currentDayNum-1 == 3 || currentDayNum-starInt == 23){
                coList[index].setText(String.valueOf(currentDayNum-starInt)+"rd");
            }else{
                coList[index].setText(String.valueOf(currentDayNum-starInt)+"th");
            }

            if(currentDayNum-starInt < 10){
                resultDateString = Helper.getCurrentYear() + String.valueOf(Helper.getThisMonthIndex(Helper.getCurrentMonthName())) + "0" + String.valueOf(currentDayNum-starInt);
            }else{
                resultDateString = Helper.getCurrentYear() + String.valueOf(Helper.getThisMonthIndex(Helper.getCurrentMonthName())) + String.valueOf(currentDayNum-starInt);
            }

            if(index == 0){
                dateRangeString = Helper.getCurrentMonthName() + " " + String.valueOf(currentDayNum-starInt);
            }else if(index == 6){
                dateRangeString = dateRangeString + " - " + Helper.getCurrentMonthName() + " " + String.valueOf(currentDayNum-starInt);
            }
        }

        return resultDateString;

    }

    public String getPWeekRangeString(TextView[] coList, int index, int starInt, String dateRangeString){

        int currentDayNum = Helper.currentDayInteger;
        String resultDateString = "";

        if((currentDayNum-starInt)<=0){

            if(index == 0){
                dateRangeString = Helper.getPreMonth(Helper.getCurrentMonthName() + " " + String.valueOf(Helper.getTotalDayOfPreMonth(Helper.getPreMonth(Helper.currentMonth))-(starInt-currentDayNum)));
            }
        }else{

            if(index == 0){
                dateRangeString = Helper.getCurrentMonthName() + " " + String.valueOf(currentDayNum-starInt);
            }else if(index == 6){
                dateRangeString = dateRangeString + " - " + Helper.getCurrentMonthName() + " " + String.valueOf(currentDayNum-starInt);
            }
        }

        return dateRangeString;

    }
}