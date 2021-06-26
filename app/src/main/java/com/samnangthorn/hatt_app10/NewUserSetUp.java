package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.rpc.Help;

public class NewUserSetUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_set_up);

        dataBaseHelper myDB = new dataBaseHelper(NewUserSetUp.this);
        for(int x = 0; x < Helper.eName_List.length; x++){
            myDB.addExercise(Helper.eName_List[x], "mainTarget", "subTarget", "dis");
        }

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);

    }
}