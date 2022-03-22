package com.example.room_wrangler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.firestore.FirebaseFirestore;


public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore db;
    private DatabaseReference mDatabase;

    private EditText userEmail, userPassword, userName, userStudentId;
    private String Uid;
    private Button buttonRegister;

    private static final String TAG = "RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Room Wrangler");
        db = FirebaseFirestore.getInstance();

        //user info to store database
        userEmail = findViewById(R.id.editText_email_register);
        userPassword = findViewById(R.id.editText_password_register);
        userName = findViewById(R.id.editText_userName_register);
        userStudentId = findViewById(R.id.editText_studnetId_register);
        buttonRegister = findViewById(R.id.button_singIn_login);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strEmail = userEmail.getText().toString().trim();
                String strPassword = userPassword.getText().toString().trim();
                String strName = userName.getText().toString();
                String strStudentId = userStudentId.getText().toString();

                isBlank(strEmail, strPassword, strName, strStudentId);


                //firebase auth
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //Update UI
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount(strEmail, strPassword, strName, strStudentId);
                            assert firebaseUser != null;
                            account.setEmail(firebaseUser.getEmail());
                            account.setfName(strName);
                            account.setStudentId(strStudentId);
                            account.setPassword(strPassword);
                            account.setIdToken(firebaseUser.getUid());
                            mDatabase.child("user").child(firebaseUser.getUid()).setValue(account);


                            //success message
                            Toast.makeText(RegisterActivity.this, "Register Succeed!", Toast.LENGTH_SHORT).show();

                            //go to login page after register succeed
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(RegisterActivity.this, "Register Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void isBlank(String strEmail, String strPassword, String strName, String strStudentId) {
        if (TextUtils.isEmpty(strEmail)) {
            userEmail.setError("Email is required.");
            return;
        }

        if (TextUtils.isEmpty(strPassword)) {
            userPassword.setError("Password is required.");
            return;
        }

        if (strPassword.length() < 6) {
            userPassword.setError("Password Must be >= 6 Characters.");
            return;
        }

        if (TextUtils.isEmpty(strName)) {
            userName.setError("Name is required.");
            return;
        }

        if (TextUtils.isEmpty(strStudentId)) {
            userStudentId.setError("Student ID is required.");
            return;
        }

    }

}