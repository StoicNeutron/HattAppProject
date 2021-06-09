package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Setup extends AppCompatActivity {

    Button signUp_btt, btt_logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        signUp_btt= (Button)findViewById(R.id.btt_signUp);
        btt_logIn= (Button)findViewById(R.id.btt_logIn);

        signUp_btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_signUpLayout();
            }
        });

        btt_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_logInLayout();
            }
        });

    }

    //Methods

    public void open_signUpLayout() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void open_logInLayout() {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }
}