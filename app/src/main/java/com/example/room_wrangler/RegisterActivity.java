package com.example.room_wrangler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabase;

    private EditText emailRegister, passwordRegister;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Room Wrangler");

        emailRegister = findViewById(R.id.editText_email_register);
        passwordRegister = findViewById(R.id.editText_password_register);
        buttonRegister = findViewById(R.id.button_login);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = emailRegister.getText().toString();
                String strPassword = passwordRegister.getText().toString();

                //firebase auth

                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            assert firebaseUser != null;
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPassword);
                            account.setIdToken(firebaseUser.getUid());


                            mDatabase.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(RegisterActivity.this, "Register Succeed!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Register Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}