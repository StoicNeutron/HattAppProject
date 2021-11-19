package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Back Sign In
                if(mAuth.getCurrentUser() != null){
                    Intent intent = new Intent(MainActivity.this, Returning.class);
                    startActivity(intent);
                    finish();
                //New User or First Sign In
                }else{
                    Intent intent = new Intent(MainActivity.this, Setup.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2000);
    }
}