package com.samnangthorn.hatt_app10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    Button btt_logIn, btt_signUp;
    ImageButton btt_back;
    TextInputLayout userName, email, password1, password2;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btt_logIn = (Button)findViewById(R.id.btt_logIn);
        btt_signUp = (Button)findViewById(R.id.btt_createAnAccount);
        btt_back = (ImageButton) findViewById(R.id.btt_back);
        userName = findViewById(R.id.edt_userName);
        email = findViewById(R.id.edt_email);
        password1 = findViewById(R.id.edt_createPassword);
        password2 = findViewById(R.id.edt_confirmPassword);
        mAuth = FirebaseAuth.getInstance();


        btt_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_logInLayout();
            }
        });

        btt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(email, password1);
            }
        });

        btt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_setUpLayout();
            }
        });
    }

    //Methods

    public void open_logInLayout() {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }

    public void open_setUpLayout() {
        Intent intent = new Intent(this, Setup.class);
        startActivity(intent);
    }

    public void signUp(TextInputLayout email, TextInputLayout password1) {
        String Email = email.getEditText().toString();
        String Password = password1.getEditText().toString();
    }

}