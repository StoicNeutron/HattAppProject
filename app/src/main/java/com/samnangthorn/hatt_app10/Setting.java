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
import android.widget.NumberPicker;
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

    private ImageView btt_home, btt_report, btt_exercise, btt_schedule, btt_timer, btt_facebook, btt_youtube, profilePic;
    private EditText txt_userName;
    private Button save_btt;
    private LinearLayout weight, height, edt_restTime, btt_signOut, unit, edt_weight, edt_height;
    private TextView email, txt_restTime, txt_unit, txt_the_weight, txt_the_height;
    private SharedPreferences getData, getData2;
    private SharedPreferences.Editor editData, editData2;
    private Dialog logOut_dialog, weight_dialog, height_dialog, restTime_dialog, profile_dialog;
    private DataBaseHelper myDB;
    private String newUserName, newHeight, newWeight, newTime, Unit, profileMen;

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
        email = findViewById(R.id.txt_userEmail);
        weight = findViewById(R.id.edt_weight);
        height = findViewById(R.id.edt_height);
        edt_restTime = findViewById(R.id.edt_restTime);
        txt_restTime = findViewById(R.id.txt_restTime);
        unit = findViewById(R.id.unit);
        txt_unit = findViewById(R.id.txt_unit);
        edt_weight = findViewById(R.id.edt_weight);
        edt_height = findViewById(R.id.edt_height);
        txt_userName = findViewById(R.id.txt_userName);
        profilePic = findViewById(R.id.profilePic);
        txt_the_weight = findViewById(R.id.txt_the_weight);
        txt_the_height = findViewById(R.id.txt_the_height);
        save_btt = findViewById(R.id.save_btt);

        getData = getApplicationContext().getSharedPreferences("local_data", MODE_PRIVATE);
        editData = getData.edit();
        Unit = getData.getString("unit", "ERROR");
        profileMen = getData.getString("profileMen", "true");

        // Logout Dialog
        logOut_dialog = new Dialog(Setting.this);
        logOut_dialog.setContentView(R.layout.pop_up_dialog);
        logOut_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        logOut_dialog.setCancelable(false);

        TextView btt_cancel, btt_logout;
        btt_cancel = logOut_dialog.findViewById(R.id.pop_btt_cancel);
        btt_logout = logOut_dialog.findViewById(R.id.pop_btt_logout);

        btt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut_dialog.dismiss();
            }
        });

        btt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = null;
                FirebaseFirestore firebase_database;
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

        // Weight Dialog
        weight_dialog = new Dialog(Setting.this);
        weight_dialog.setContentView(R.layout.weight_picker);
        weight_dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        weight_dialog.setCancelable(false);

        TextView btt_weightDialog_cancel, btt_weightDialog_save, lbOrKg;
        NumberPicker WeightPicker1, WeightPicker2, WeightPicker3;
        btt_weightDialog_cancel = weight_dialog.findViewById(R.id.btt_cancel2);
        btt_weightDialog_save = weight_dialog.findViewById(R.id.btt_save);
        lbOrKg = weight_dialog.findViewById(R.id.lbOrKg);
        WeightPicker1 = weight_dialog.findViewById(R.id.WeightPicker1);
        WeightPicker2 = weight_dialog.findViewById(R.id.WeightPicker2);
        WeightPicker3 = weight_dialog.findViewById(R.id.WeightPicker3);

        if(Unit.equalsIgnoreCase("NonUS")){
            lbOrKg.setText("Kg");
        }else{
            lbOrKg.setText("lb");
        }

        btt_weightDialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight_dialog.dismiss();
            }
        });

        btt_weightDialog_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int inputWeight1 = WeightPicker1.getValue()*100;
                int inputWeight2 = WeightPicker2.getValue()*10;
                int inputWeight3 = WeightPicker3.getValue();
                if(Unit.equalsIgnoreCase("US")){
                    String newWeight_US = String.valueOf(inputWeight1 + inputWeight2 + inputWeight3);
                    editData.putString("weight_US", newWeight_US);
                    double newWeight_NonUS = ((inputWeight1 + inputWeight2 + inputWeight3)/2.205);
                    editData.putString("weight_NonUS", String.format("%.0f", newWeight_NonUS));
                    txt_the_weight.setText(newWeight_US);
                }else{
                    String newWeight_NonUS = String.valueOf(inputWeight1 + inputWeight2 + inputWeight3);
                    editData.putString("weight_NonUS", newWeight_NonUS);
                    double newWeight_US = ((inputWeight1 + inputWeight2 + inputWeight3)*2.205);
                    editData.putString("weight_US", String.format("%.0f", newWeight_US));
                    txt_the_weight.setText(newWeight_NonUS);
                }

                editData.apply();
                Toast.makeText(Setting.this, "Saved", Toast.LENGTH_SHORT).show();
                weight_dialog.dismiss();
            }
        });

        if(Unit.equalsIgnoreCase("NonUS")) {
            WeightPicker1.setMaxValue(2);
            WeightPicker1.setMinValue(0);
            WeightPicker1.setValue(0);
        }else if(Unit.equalsIgnoreCase("US")){
            WeightPicker1.setMaxValue(6);
            WeightPicker1.setMinValue(0);
            WeightPicker1.setValue(1);
        }
        WeightPicker2.setMaxValue(9);
        WeightPicker3.setMaxValue(9);

        // Height Dialog
        height_dialog = new Dialog(Setting.this);
        height_dialog.setContentView(R.layout.height_picker);
        height_dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        height_dialog.setCancelable(false);

        TextView btt_heightDialog_cancel, btt_heightDialog_save, ftOrM;
        NumberPicker Height_picker1, Height_picker2, Height_picker3;
        btt_heightDialog_cancel = height_dialog.findViewById(R.id.btt_cancel3);
        btt_heightDialog_save = height_dialog.findViewById(R.id.btt_save3);
        ftOrM = height_dialog.findViewById(R.id.ftOrM);
        Height_picker1 = height_dialog.findViewById(R.id.Height_picker1);
        Height_picker2 = height_dialog.findViewById(R.id.Height_picker2);
        Height_picker3 = height_dialog.findViewById(R.id.Height_Picker3);

        if(Unit.equalsIgnoreCase("NonUS")){
            ftOrM.setText("M");
        }else{
            ftOrM.setText("ft");
        }

        btt_heightDialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                height_dialog.dismiss();
            }
        });

        btt_heightDialog_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int inputHeight1 = Height_picker1.getValue();
                int inputHeight2 = Height_picker2.getValue();
                int inputHeight3 = Height_picker3.getValue();
                if(Unit.equalsIgnoreCase("US")){
                    // save as inches
                    int inputHeight_US = (inputHeight1*12) + (inputHeight2*10 + inputHeight3);
                    editData.putString("height_US", String.valueOf(inputHeight_US));
                    double inputHeight_NonUS = inputHeight_US/39.37;
                    //String. format("%. 2f", 1.23456)
                    editData.putString("height_NonUS", String.format("%.2f", inputHeight_NonUS));
                    txt_the_height.setText(inputHeight1 + "." + ((inputHeight2*10) + inputHeight3));
                }else{
                    // save as meters
                    String inputHeight_NonUS = inputHeight1 + "."+ (inputHeight2*10) + inputHeight3;
                    editData.putString("height_NonUS", inputHeight_NonUS);
                    double inputHeight_US = Double.valueOf(inputHeight_NonUS)*39.37;
                    editData.putString("height_US", String.format("%.2f", inputHeight_US));
                    txt_the_height.setText(inputHeight_NonUS);
                }

                editData.apply();
                Toast.makeText(Setting.this, "Saved", Toast.LENGTH_SHORT).show();
                weight_dialog.dismiss();
            }
        });

        if(Unit.equalsIgnoreCase("NonUS")) {
            Height_picker1.setMaxValue(2);
            Height_picker1.setMinValue(0);
            Height_picker1.setValue(1);
        }else if(Unit.equalsIgnoreCase("US")){
            Height_picker1.setMaxValue(8);
            Height_picker1.setMinValue(1);
            Height_picker1.setValue(5);
        }
        Height_picker2.setMaxValue(9);
        Height_picker3.setMaxValue(9);

        // Rest time Dialog
        restTime_dialog = new Dialog(Setting.this);
        restTime_dialog.setContentView(R.layout.rest_time_picker);
        restTime_dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        restTime_dialog.setCancelable(false);

        TextView btt_restTimeDialog_cancel, btt_restTimeDialog_save;
        NumberPicker timePicker1, timePicker2, timePicker3;
        btt_restTimeDialog_cancel = restTime_dialog.findViewById(R.id.btt_cancel4);
        btt_restTimeDialog_save = restTime_dialog.findViewById(R.id.btt_save4);
        timePicker1 = restTime_dialog.findViewById(R.id.timePicker1);
        timePicker2 = restTime_dialog.findViewById(R.id.timePicker2);
        timePicker3 = restTime_dialog.findViewById(R.id.timePicker3);

        btt_restTimeDialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restTime_dialog.dismiss();
            }
        });

        btt_restTimeDialog_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int inputTime1 = timePicker1.getValue()*100;
                int inputTime2 = timePicker2.getValue()*10;
                int inputTime3 = timePicker3.getValue();
                String newTime = String.valueOf(inputTime1 + inputTime2 + inputTime3);
                editData.putString("rest_time", newTime);
                editData.apply();
                txt_restTime.setText(newTime);
                Toast.makeText(Setting.this, "Saved", Toast.LENGTH_SHORT).show();
                restTime_dialog.dismiss();
            }
        });

        timePicker1.setMaxValue(9);
        timePicker2.setMaxValue(9);
        timePicker3.setMaxValue(9);

        // Profile Dialog
        profile_dialog = new Dialog(Setting.this);
        profile_dialog.setContentView(R.layout.profile_picker);
        profile_dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        profile_dialog.setCancelable(false);

        TextView btt_profileDialog_cancel, btt_profileDialog_save;
        ImageView img1, img2;
        btt_profileDialog_cancel = profile_dialog.findViewById(R.id.btt_cancel4);
        btt_profileDialog_save = profile_dialog.findViewById(R.id.btt_save4);
        img1 = profile_dialog.findViewById(R.id.img1);
        img2 = profile_dialog.findViewById(R.id.img2);

        btt_profileDialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_dialog.dismiss();
            }
        });

        btt_profileDialog_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(profileMen.equalsIgnoreCase("true")){
                    editData.putString("profileMen", "true");
                    profilePic.setImageResource(R.drawable.profile_men);
                }else{
                    editData.putString("profileMen", "false");
                    profilePic.setImageResource(R.drawable.profile_women);
                }
                editData.apply();
                Toast.makeText(Setting.this, "Saved", Toast.LENGTH_SHORT).show();

            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img1.setBackground(getDrawable(R.drawable.button_home_blue));
                img2.setBackground(null);
                profileMen = "true";
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img2.setBackground(getDrawable(R.drawable.button_home_blue));
                img1.setBackground(null);
                profileMen = "false";
            }
        });

        // Set Up View
        newUserName = getData.getString("user_name", "ERROR");
        txt_userName.setText(newUserName);
        email.setText(getData.getString("email_address", "ERROR"));

        Double displayHeight;
        if(Unit.equalsIgnoreCase("US")){
            newHeight = getData.getString("height_US", "0");
            displayHeight = Double.valueOf(newHeight)/12;
            newWeight = getData.getString("weight_US", "0");
        }else{
            newHeight = getData.getString("height_NonUS", "0");
            displayHeight = Double.valueOf(newHeight)*3.281;
            newWeight = getData.getString("weight_NonUS", "0");
        }
        txt_the_height.setText(String.format("%.2f", displayHeight));
        txt_the_weight.setText(newWeight);

        newTime = getData.getString("rest_time", "60");
        txt_restTime.setText(newTime);
        //profile
        if(profileMen.equalsIgnoreCase("true")){
            editData.putString("profileMen", "true");
            profilePic.setImageResource(R.drawable.profile_men);
        }else{
            editData.putString("profileMen", "false");
            profilePic.setImageResource(R.drawable.profile_women);
        }


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
                logOut_dialog.show();
            }
        });

        edt_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Unit = getData.getString("unit", "ERROR");
                weight_dialog.show();
            }
        });

        edt_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Unit = getData.getString("unit", "ERROR");
                height_dialog.show();
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
                    //
                    txt_the_weight.setText(getData.getString("weight_US", "0"));
                    newHeight = getData.getString("height_US", "0");
                    Double displayHeight = Double.valueOf(newHeight)/12;
                    txt_the_height.setText(String.format("%.2f", displayHeight));
                }else if(Unit.equalsIgnoreCase("US")){
                    editData.putString("unit", "NonUS");
                    editData.apply();
                    Toast.makeText(Setting.this, "NonUS Unit Switched", Toast.LENGTH_SHORT).show();
                    Unit = "NonUS";
                    txt_unit.setText("KG-M");
                    //
                    txt_the_weight.setText(getData.getString("weight_NonUS", "0"));
                    newHeight = getData.getString("height_NonUS", "0");
                    Double displayHeight = Double.valueOf(newHeight)*3.281;
                    txt_the_height.setText(String.format("%.2f", displayHeight));
                }
            }
        });

        edt_restTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                restTime_dialog.show();
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                profile_dialog.show();
            }
        });

        txt_userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_btt.setVisibility(View.VISIBLE);
            }
        });

        save_btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUserName = txt_userName.getText().toString();
                editData.putString("user_name", newUserName);
                editData.apply();
                Toast.makeText(Setting.this, "Saved", Toast.LENGTH_SHORT).show();
                save_btt.setVisibility(View.GONE);
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