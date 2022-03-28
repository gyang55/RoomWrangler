package com.example.room_wrangler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class AccountActivity extends AppCompatActivity {
    private Button logoutBtn;
    private TextView userName, userEmail, userStudentID;
    private ImageView profileImage;
    private Button gotoMainPage;

    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    //    private ArrayList<RoomBooking>
    private String email, owner;

//    private ArrayList<RoomBooking> bookingArrayList;




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


        //logout button
        logoutBtn.setOnClickListener(view -> {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(AccountActivity.this, LoginActivity.class));
        });

        //go to main button
        gotoMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, MainPageActivity.class));
            }
        });


        //upcoming reservation bookings
        owner = firebaseUser.getUid();
        db = FirebaseFirestore.getInstance();

        CollectionReference bookings = db.collection("bookings");
        Query query = bookings.whereEqualTo("owner", owner);


        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<RoomBooking> bookingArrayList = new ArrayList<>();
                for (QueryDocumentSnapshot documents: task.getResult()) {
                    RoomBooking roomBooking= documents.toObject(RoomBooking.class);
                    bookingArrayList.add(roomBooking);
                    Log.d("TAG", documents.getId() + " => " + documents.getData());
                }
                showUpcomingReservation(bookingArrayList);
            }

        });


    }


    private void showUpcomingReservation(ArrayList<RoomBooking> bookingArrayList) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_account);
        RoomBooking[] bookingList = new RoomBooking[bookingArrayList.size()];
        for(int i = 0; i < bookingArrayList.size(); i++) {
            bookingList[i] = bookingArrayList.get(i);
        }
        ReservationAdapter reservationAdapter = new ReservationAdapter(bookingList);
        recyclerView.setAdapter(reservationAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

}