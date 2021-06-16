package com.samnangthorn.hatt_app10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class LogIn extends AppCompatActivity {

    Button btt_logIn;
    TextView btt_signUp, btt_forgotPassword, notification_txt;
    ImageButton btt_back;
    TextInputLayout email, password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        btt_back = findViewById(R.id.btt_back);
        btt_logIn = findViewById(R.id.btt_logIn);
        btt_signUp = findViewById(R.id.btt_signUp);
        btt_forgotPassword = findViewById(R.id.btt_forgotPassword);
        notification_txt = findViewById(R.id.notification_text);
        email = findViewById(R.id.edt_email);
        password = findViewById(R.id.edt_Password);
        mAuth = FirebaseAuth.getInstance();

        btt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_setUpLayout();
            }
        });

        btt_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn(email, password);
            }
        });

        btt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_signUpLayout();
            }
        });

        btt_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String E_mail = email.getEditText().getText().toString();
                forgot_password(E_mail, email, password, btt_logIn);

            }
        });
    }

    //Methods

    public void open_setUpLayout() {
        Intent intent = new Intent(this, Setup.class);
        startActivity(intent);
    }

    public void open_signUpLayout() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void logIn(TextInputLayout email, TextInputLayout password) {
            String Email = email.getEditText().getText().toString();
            String Password = password.getEditText().getText().toString();

            if (!Email.isEmpty() && !Password.isEmpty()) {
                if (Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    email.setError(null);
                    // Google Authentication LogIn return User
                    mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LogIn.this, "LogIn Successful!", Toast.LENGTH_SHORT).show();
                                open_homeLayout();
                                finish();
                            }else{
                                if(task.getException().getMessage().equalsIgnoreCase("The password is invalid or the user does not have a password.")){
                                    password.setError("*Incorrect Password");
                                }else{
                                    password.setError(null);
                                    Toast.makeText(LogIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

                } else {
                    email.setError("*Incorrect Email Format!");
                }
            } else {
                email.setError(null);
                Toast.makeText(LogIn.this, "Empty field Found!", Toast.LENGTH_SHORT).show();
            }
    }

    public void open_homeLayout() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void forgot_password(String email, TextInputLayout email_box, TextInputLayout password_box, Button btt_logIn){
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        email_box.setError(null);
                        notification_txt.setVisibility(View.VISIBLE);
                        btt_forgotPassword.setVisibility((View.GONE));
                        password_box.setVisibility(View.GONE);
                        btt_logIn.setVisibility(View.GONE);
                    }else{
                        Toast.makeText(LogIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else if(email.isEmpty()){
            email_box.setError("Enter your Email to reset your password");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_box.setError("Incorrect email Format!");
        }
    }
}