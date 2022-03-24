package com.example.room_wrangler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
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
        titleText.setText("Room " + room.getRoomNumber());
        TextView chosenDateTextView = findViewById(R.id.textview_bookroomMenu_date);
        chosenDateTextView.setText(chosenDate);
        RoomBooking booking = new RoomBooking(chosenDate, timeSlots, room, userId);
        setUpDisplayTimeSlots(booking.getDuration());
        Button button = findViewById(R.id.button_book_room_submit);
//        LocalTime now = LocalTime.now();
//        LocalDate today = LocalDate.now();
//        final LocalTime[] start = {now};
//        final LocalTime[] end = {now.plusHours(1)};
//        final LocalDate[] date = {today};
//

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//
//        titleText.setText("Book room " + room.getRoomNumber());
//        startTimeText.setText(start[0].format(formatter));
//        endTimeText.setText(end[0].format(formatter));
//        dateText.setText(today.toString());
//


//        startTimeText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
//                        start[0] = LocalTime.of(i, i1);
//                        startTimeText.setText(start[0].format(formatter));
//                    }
//                };
//                TimePickerDialog timePickerDialog = new TimePickerDialog(RoomInfoActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, timeSetListener, start[0].getHour(), start[0].getMinute(), false);
//                timePickerDialog.show();
//            }
//        });

//        endTimeText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
//                        end[0] = LocalTime.of(i, i1);
//                        endTimeText.setText(end[0].format(formatter));
//                    }
//                };
//                TimePickerDialog timePickerDialog = new TimePickerDialog(RoomInfoActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, timeSetListener, end[0].getHour(), end[0].getMinute(), false);
//                timePickerDialog.show();
//            }
//        });

//        dateText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                        date[0] = LocalDate.of(i, i1 + 1, i2);
//                        dateText.setText(date[0].toString());
//                    }
//                };
//                DatePickerDialog datePickerDialog = new DatePickerDialog(RoomInfoActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, date[0].getYear(), date[0].getMonthValue() - 1, date[0].getDayOfMonth());
//                datePickerDialog.show();
//            }
//        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Need to add validation checking: start time before end time, etc
                db.collection("bookings")
                        .add(booking)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("Debug", "DocumentSnapshot added with ID: " + documentReference.getId());
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Debug", "Error adding document", e);
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