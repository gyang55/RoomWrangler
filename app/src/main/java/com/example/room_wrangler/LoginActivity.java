package com.example.room_wrangler;


import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser firebaseUser;

    private EditText userEmail, userPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Button btn_login = findViewById(R.id.button_singIn_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Room Wrangler");

        userEmail = findViewById(R.id.editText_email_register);
        userPassword = findViewById(R.id.editText_password_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = userEmail.getText().toString();
                String strPassword = userPassword.getText().toString();

                if (strEmail.equals("") || strPassword.equals("")) {
                    Toast.makeText(getBaseContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
                            intent.putExtra("email", strEmail);
                            startActivity(intent);
                            finish(); // destroy current activity
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        Button btn_register = findViewById(R.id.button_singUp_login);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


}