package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class NewUserSetUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_set_up);

        // set up built in exercises
        DataBaseHelper myDB = new DataBaseHelper(NewUserSetUp.this);
        for(int x = 0; x < Helper.eName_List.length; x++){
            myDB.addExercise(Helper.eName_List[x], Helper.mTarget_List[x], Helper.sTarget_List[x], Helper.des_List[x]);
        }

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);

    }
}