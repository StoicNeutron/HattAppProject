package com.samnangthorn.hatt_app10;

import androidx.annotation.BinderThread;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    Button btt_logIn, btt_signUp;
    ImageButton btt_back;
    TextInputLayout userName, email, password1, password2;
    String UID;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebase_database;
    private SharedPreferences getData;
    private SharedPreferences.Editor editData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btt_logIn = findViewById(R.id.btt_logIn);
        btt_signUp = findViewById(R.id.btt_createAnAccount);
        btt_back = findViewById(R.id.btt_back);
        userName = findViewById(R.id.edt_userName);
        email = findViewById(R.id.edt_email);
        password1 = findViewById(R.id.edt_createPassword);
        password2 = findViewById(R.id.edt_confirmPassword);
        mAuth = FirebaseAuth.getInstance();
        firebase_database = FirebaseFirestore.getInstance();

        btt_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_logInLayout();
            }
        });

        btt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(email, password1, userName, password2);
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

    public void signUp(TextInputLayout email, TextInputLayout password1, TextInputLayout userName, TextInputLayout password2) {
        String Email = email.getEditText().getText().toString();
        String Password = password1.getEditText().getText().toString();
        String confirmPassword = password2.getEditText().getText().toString();
        String Username = userName.getEditText().getText().toString();

        if (!Email.isEmpty() && !Password.isEmpty() && !confirmPassword.isEmpty() && !Username.isEmpty()) {
            if (Patterns.EMAIL_ADDRESS.matcher(Email).matches() && Username.length() < 15 && confirmPassword.equals(Password)) {
                email.setError(null);
                password2.setError(null);
                if (Password.length() >= 12) {
                    password1.setError(null);
                    boolean conditionTrue = false;
                    char currentChar;
                    for (int x = 0; x < Password.length(); x++) {
                        currentChar = Password.charAt(x);
                        if (currentChar > 47 && currentChar < 58) {
                            conditionTrue = true;
                            break;
                        }
                    }
                    if (conditionTrue) {
                        password1.setError(null);
                        // Google Authentication Sign Up new User
                        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(SignUp.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                                    UID = mAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = firebase_database.collection("User").document(UID);
                                    Map<String, Object> User_data = new HashMap<>();
                                    User_data.put("user_name", Username);
                                    User_data.put("email_address", Email);
                                    User_data.put("unit", "NonUS");
                                    documentReference.set(User_data);
                                    editData.putString("user_name", Username);
                                    editData.putString("email_address", Email);
                                    editData.putString("unit", "NonUS");
                                    editData.apply();
                                    open_homeLayout();
                                    finish();
                                }else if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    email.setError("Account associated to this Email is already exist!");
                                }else{
                                    Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        password1.setError("*Must include at least 1 number and 12 characters long");
                    }
                }else{
                    password1.setError("*Must include at least 1 number and 12 characters long");
                }
            } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                email.setError("Incorrect email Format!");
            } else {
                password2.setError("Re-enter your new password");
            }
        } else {
            Toast.makeText(SignUp.this, "Empty field Found!", Toast.LENGTH_SHORT).show();
        }
    }

    public void open_homeLayout() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

}