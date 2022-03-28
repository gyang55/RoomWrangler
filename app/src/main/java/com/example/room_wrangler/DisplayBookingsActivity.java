package com.example.room_wrangler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class DisplayBookingsActivity extends AppCompatActivity {
    private Room room;
    private String chosenDate;
    private ArrayList<String> timeSlots;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_bookings);
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        room = (Room) intent.getExtras().get("Room");
        chosenDate = intent.getExtras().getString("date");
        timeSlots = intent.getExtras().getStringArrayList("timeslots");
        showBookingMenu();
    }

    private void showBookingMenu() {
        setContentView(R.layout.book_room_menu);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String userId = firebaseUser.getUid();
        TextView titleText = findViewById(R.id.textView_book_room_title);
        String roomNumber = "Room ".concat(" ").concat(room.getRoomNumber());
        titleText.setText(roomNumber);
        TextView chosenDateTextView = findViewById(R.id.textview_bookroomMenu_date);
        chosenDateTextView.setText(chosenDate);
        RoomBooking booking = new RoomBooking(chosenDate, timeSlots, room, userId);
        setUpDisplayTimeSlots(booking.getDuration());
        Button button = findViewById(R.id.button_book_room_submit);
        button.setOnClickListener(view -> {
            for (String slot : booking.getDuration()
            ) {
                db.collection("bookings").document(booking.getDate()
                        .concat(" ").concat(booking.getRoomNumber())).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot result = task.getResult();
                        if (result.exists()) {
                            db.collection("bookings").document(result.getId()).
                                    update("duration", FieldValue.arrayUnion(slot))
                                    .addOnSuccessListener(
                                            documentReference -> finish())
                                    .addOnFailureListener(e -> Log.w("Debug", "Error adding document", e));
                        } else {
                            db.collection("bookings")
                                    .document(booking.getDate().concat(" ").concat(booking.getRoomNumber()).concat(" ").concat(booking.getOwner()))
                                    .set(booking)
                                    .addOnSuccessListener(documentReference -> finish())
                                    .addOnFailureListener(e -> Log.w("Debug", "Error adding document", e));
                        }
                    }
                });
            }
        });

    }

    private void setUpDisplayTimeSlots(ArrayList<String> timeSlots) {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_bookroommenu);
        DisplayChosenTimeSlots displayChosenTimeSlots = new DisplayChosenTimeSlots(timeSlots);
        recyclerView.setAdapter(displayChosenTimeSlots);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}