package com.example.room_wrangler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountActivity extends AppCompatActivity {
    private Button logoutBtn;
    private TextView userName, userEmail, userStudentID;
    private ImageView profileImage;
    private Button gotoMainPage;

    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    //    private ArrayList<RoomBooking>
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            email = firebaseUser.getEmail();
        } else {
            Intent intent = getIntent();
            email = intent.getStringExtra("email");
        }

        logoutBtn = findViewById(R.id.logoutBtn);
        userName = findViewById(R.id.TextView_fName_account);
        userEmail = findViewById(R.id.TextView_email_account);
        userStudentID = findViewById(R.id.TextView_studentId_account);
        gotoMainPage = findViewById(R.id.button_account_goto_mainPage);
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("Room Wrangler").child("user");
        Log.d("tag", "///user reference///" + userRef.getKey());

        //Query user Info
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                    if (email.equals(ds.child("email").getValue(String.class))) {
                        userName.setText(ds.child("fName").getValue(String.class));
                        userEmail.setText(email);
                        userStudentID.setText(ds.child("studentId").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logoutBtn.setOnClickListener(view -> {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(AccountActivity.this, LoginActivity.class));
        });

        gotoMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, MainPageActivity.class));
            }
        });
    }


}